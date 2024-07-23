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
import kotlin.math.ceil

@Service
class LogHandler(
    private val logRepository: MongoRepo,
    private val userRepository: UserRepository,
    private val creditLogRepository: CreditLogRepository
) {

    private val logger = LoggerFactory.getLogger(LogHandler::class.java)

    fun calculateUserLogonTime() {
        val logs: List<BaseLog> = logRepository.findAllUnprocessedLogs().map { BaseLog(it) }

        logger.info("Fetching logs, ${logs.size} entries found.")

        val logins = logs.filter { it.type == LogType.Login }.distinctBy { it.identifier }

        val users: List<User> = logins
            .map { userRepository.findByEmail(it.identifier) }
            .filter { it.isPresent }
            .map { it.get() }

        val groupedLogs: Map<String, List<BaseLog>> = logs.groupBy { it.identifier }

        val userLogMap: Map<User, List<BaseLog>> = users.associateWith { user ->
            groupedLogs[user.username.filterNot { it.isWhitespace() }].orEmpty() + groupedLogs[user.email].orEmpty()
        }

        logger.info("Fetching logs, ${userLogMap.size} done.")

        var unprocessedLogs: List<BaseLog> = emptyList()
        val processedLogs = userLogMap.flatMap { (user, logs) ->
            val (pairedLogs, unpairedLogs) = pairLogs(logs)
            unprocessedLogs = unpairedLogs
            val verifiedPairedLogs = verifyPairs(pairedLogs).map { pair ->
                val points = calculatePoints(pair.logon.timestamp, pair.logout.timestamp)
                ProcessedLog(user, points, pair.logon.timestamp to pair.logout.timestamp, pair.logon.machineName)
            }
            verifiedPairedLogs
        }

        processedLogs.forEach { pLog ->
            creditLogRepository.save(
                CreditLog(
                    null,
                    "Points added for gaming",
                    pLog.pointValue,
                    pLog.timestamp.second.toJavaLocalDateTime(),
                    pLog.user
                )
            )
        }
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

    fun storeUnprocessedLog(log: BaseLog) {
        // logRepository.saveUnprocessedLog(log)
    }

    fun storeProcessedLog(log: ProcessedLog) {
        // logRepository.saveProcessedLog(log)
    }
}
