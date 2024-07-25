package pt.isel.odin.heimdallMiddleware

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDateTime
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import pt.isel.odin.heimdallMiddleware.models.BaseLog
import pt.isel.odin.heimdallMiddleware.models.LogPair
import pt.isel.odin.heimdallMiddleware.models.LogType
import pt.isel.odin.heimdallMiddleware.models.ProcessedLog
import pt.isel.odin.model.CreditLog
import pt.isel.odin.model.user.User
import pt.isel.odin.repository.CreditLogRepository
import pt.isel.odin.repository.UserRepository
import pt.isel.odin.service.ServiceUtils
import kotlin.math.ceil

@Service
class LogHandler(
    private val logRepository: MongoRepo,
    private val userRepository: UserRepository,
    private val serviceUtils: ServiceUtils
) {

    private val logger = LoggerFactory.getLogger(LogHandler::class.java)

    fun calculateUserLogonTime() {
        val logs: List<BaseLog> = logRepository.findAllUnprocessedLogs().map { BaseLog(it) }

        //logger.info("Fetching logs, ${logs.size} entries found.")

        //get the identifier for all the logs
        val logins = logs.filter { it.type == LogType.Login }.distinctBy { it.identifier }

        //get all users from their identifiers
        val users: List<User> = logins
            .map { userRepository.findByEmail(it.identifier) }
            .filter { it.isPresent }
            .map { it.get() }


        //group logs by identifier
        val groupedLogs: Map<String, List<BaseLog>> = logs.groupBy { it.identifier }


        //associate each user with their logs
        val userLogMap: Map<User, List<BaseLog>> = users.associateWith { user ->
            val logs = groupedLogs[user.username.filterNot { it.isWhitespace() }].orEmpty() + groupedLogs[user.email].orEmpty()
            logs.forEach { logRepository.deleteUnprocessed(it) }
            logs
        }

       //logger.info("Fetching logs, ${userLogMap.size} done.")

        val unprocessedLogs: MutableList<BaseLog> = mutableListOf()

        //for each user, pair logs and calculate points
        val processedLogs = userLogMap.flatMap { (user, logs) ->
            val (pairedLogs, unpairedLogs) = pairLogs(logs)
            unprocessedLogs += unpairedLogs
            val verifiedPairedLogs = verifyPairs(pairedLogs).map { pair ->
                val points = calculatePoints(pair.logon.timestamp, pair.logout.timestamp)
                ProcessedLog(user, points, pair.logon.timestamp to pair.logout.timestamp, pair.logon.machineName)
            }
            verifiedPairedLogs
        }

        processedLogs.forEach { pLog ->
            val creditLog = CreditLog(
                null,
                "Points removed due to gaming",
                -pLog.pointValue,
                pLog.timestamp.second.toJavaLocalDateTime(),
                pLog.user
            )
            serviceUtils.changePointsToUser(creditLog,-pLog.pointValue)
            logRepository.saveProcessedLog(pLog)
        }

        val binLogs = logs.filterNot { it in unprocessedLogs }

        logger.info("Fetching logs, ${unprocessedLogs.size} up found.")
        logger.info("Fetching logs, ${binLogs.size} bin found.")
        //binLogs
    }

    fun pairLogs(logs: List<BaseLog>): Pair<List<LogPair>, List<BaseLog>> {
        val unpairedLogs = mutableListOf<BaseLog>()
        val pairedLogs = mutableListOf<LogPair>()
        val logonStack = mutableListOf<BaseLog>()

        for (log in logs.sortedBy { it.timestamp }) {
            when (log.type) {
                LogType.Login -> logonStack.add(log)
                LogType.Lock -> logonStack.add(log)
                LogType.Logout -> {
                    if (logonStack.isNotEmpty()) {
                        val logon = logonStack.removeAt(logonStack.size - 1)
                        pairedLogs.add(LogPair(logon, log))
                    } else {
                        unpairedLogs.add(log)
                    }
                }
            }
        }
        unpairedLogs.addAll(logonStack)
        return pairedLogs to unpairedLogs
    }

    // Mock verification function
    fun verifyPairs(pairs: List<LogPair>): List<LogPair> {
        // Let's assume that some verification logic might fail
        // For simplicity, let's assume all pairs are valid
        // If invalid, we can remove pairs from pairedLogs and add their logs to unpairedLogs
        return pairs
    }

    fun calculatePoints(start: LocalDateTime, end: LocalDateTime): Int {
        val startInstant = start.toInstant(TimeZone.UTC)
        val endInstant = end.toInstant(TimeZone.UTC)
        val duration = endInstant.minus(startInstant).inWholeMinutes
        return ceil(duration / 30.0).toInt()
    }
}
