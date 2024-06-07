package pt.isel.odin.utils.heimdallSupport

import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
@EnableScheduling
class LogService(private val mongoTemplate: MongoTemplate) {

    private val logger = LoggerFactory.getLogger(LogService::class.java)

    data class LogEntry(
        val username: String,
        val type: String,
        val timestamp: String
    )

    @Scheduled(cron = "0 0 0 * * ?")
    fun dailyLogonTimeCalculation() {
        calculateUserLogonTime()
    }

    @Scheduled(cron = "0 */3 * * * *")
    fun periodicLogonTimeCalculation() {
        calculateUserLogonTime()
    }

    private fun calculateUserLogonTime() {
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val logs = mongoTemplate.findAll(LogEntry::class.java, "processed")
        val userSessions = mutableMapOf<String, MutableList<LogEntry>>()

        logger.info("Fetching logs, ${logs.size} entries found.")

        logs.filter { it.type == "Login" || it.type == "Logout" }.forEach { log ->
            userSessions.computeIfAbsent(log.username) { mutableListOf() }
            userSessions[log.username]?.add(log)
        }

        userSessions.forEach { (user, logEntries) ->
            var totalDuration = Duration.ZERO
            val sortedEntries = logEntries.sortedBy { LocalDateTime.parse(it.timestamp, formatter) }

            var i = 0
            while (i < sortedEntries.size - 1) {
                val loginEntry = sortedEntries[i]
                val logoutEntry = sortedEntries[i + 1]

                if (loginEntry.type == "Login" && logoutEntry.type == "Logout") {
                    val loginTime = LocalDateTime.parse(loginEntry.timestamp, formatter)
                    val logoutTime = LocalDateTime.parse(logoutEntry.timestamp, formatter)
                    totalDuration = totalDuration.plus(Duration.between(loginTime, logoutTime))
                    i += 2
                } else {
                    logger.warn("Unpaired log entry detected for user: $user, type: ${loginEntry.type}")
                    i += 1
                }
            }

            val points = calculatePoints(totalDuration)
            logger.info("User: $user, Total Duration: ${totalDuration.toMinutes()} minutes, Points: $points")
        }
    }

    private fun calculatePoints(duration: Duration): Int {
        // Placeholder for your point calculation logic
        return duration.toMinutes().toInt()/30
    }
}
