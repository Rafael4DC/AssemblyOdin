package pt.isel.odin.heimdallMiddleware

import com.mongodb.client.MongoClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.slf4j.LoggerFactory


@Service
@EnableScheduling
class HeimdallUpdateScheduler @Autowired constructor(private val logHandler: LogHandler,
                                                     private val logRepository: MongoRepo) {


    private val logger = LoggerFactory.getLogger(LogHandler::class.java)

    @Scheduled(cron = "0 0 0 * * ?")
    fun dailyLogonTimeCalculation() {
        logHandler.calculateUserLogonTime()
    }

    @Scheduled(cron = "*/20 * * * * *")
    fun periodicLogonTimeCalculation() {
        logger.info("Periodic log ran")
        logHandler.calculateUserLogonTime()
    }
}
