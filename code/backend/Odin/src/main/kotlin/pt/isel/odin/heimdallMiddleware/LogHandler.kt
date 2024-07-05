package pt.isel.odin.heimdallMiddleware

import kotlinx.datetime.toKotlinLocalDateTime
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pt.isel.odin.heimdallMiddleware.models.BaseLog
import pt.isel.odin.heimdallMiddleware.models.ProcessedLog
import pt.isel.odin.repository.UserRepository
import java.time.Duration
import java.time.format.DateTimeFormatter

@Service
class LogHandler(private val logRepository: MongoRepo,
                 private val userRepository: UserRepository,) {

    private val logger = LoggerFactory.getLogger(LogHandler::class.java)

    fun calculateUserLogonTime() {
        val logs = logRepository.findAllUnprocessedLogs().map {
            BaseLog(
                it.identifier,
                it.type,
                java.time.LocalDateTime.parse(it.timestamp, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSX")),
                it.machineName
            )
        }

        logger.info("Fetching logs, ${logs.size} entries found.")

        // Group logs by user/email pairs
        val userSessions = logs.groupBy {
            if (it.type == "Logout") it.identifier else userRepository.findByEmail(it.identifier)
        }

        userSessions.forEach { (user, logEntries) ->
            val sortedEntries = logEntries.sortedBy { it.timestamp }
            val pairedSessions = mutableListOf<Pair<BaseLog, BaseLog>>()
            val unpairedLogs = mutableListOf<BaseLog>()

            var previousLog: BaseLog? = null
            sortedEntries.forEach { currentLog ->
                if (previousLog != null) {
                    if (previousLog!!.type == "Login" && currentLog.type == "Logout") {
                        pairedSessions.add(previousLog!! to currentLog)
                        previousLog = null
                    } else {
                        unpairedLogs.add(previousLog!!)
                        previousLog = currentLog
                    }
                } else {
                    previousLog = currentLog
                }
            }

            if (previousLog != null) {
                unpairedLogs.add(previousLog!!)
            }

            val totalDuration = pairedSessions.map {
                val loginTime = it.first.timestamp
                val logoutTime = it.second.timestamp
                Duration.between(loginTime, logoutTime)
            }.fold(Duration.ZERO) { acc, duration -> acc + duration }

            val timestamps = pairedSessions.flatMap { listOf(it.first.timestamp, it.second.timestamp) }.map {
                it.toKotlinLocalDateTime()
            }

            unpairedLogs.forEach { log ->
                if (log.type == "Login") {
                    logger.warn("Unpaired login entry detected for user: $user at ${log.timestamp}")
                } else if (log.type == "Logout") {
                    logger.warn("Unpaired logout entry detected for user: $user at ${log.timestamp}")
                }
            }

            val points = calculatePoints(totalDuration)
            logger.info("User: $user, Total Duration: ${totalDuration.toMinutes()} minutes, Points: $points")

            //val email = logRepository.getEmailByUser(user) // Assuming you have this method in the repository
            //val processedLog = ProcessedLog(user, email, "Login/Logout", timestamps, "MachineName")
            //logRepository.saveProcessedLog(processedLog)
        }
    }

    fun calculatePoints(duration: Duration): Int {
        // Placeholder for your point calculation logic
        return (duration.toMinutes() / 30).toInt()
    }

    fun storeUnprocessedLog(log: BaseLog) {
        logRepository.saveUnprocessedLog(log)
    }

    fun storeProcessedLog(log: ProcessedLog) {
        logRepository.saveProcessedLog(log)
    }
}
