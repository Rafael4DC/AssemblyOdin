package pt.isel.odin.heimdallMiddleware

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
@EnableScheduling
class HeimdallUpdateScheduler @Autowired constructor(private val logHandler: LogHandler) {

    @Scheduled(cron = "0 0 0 * * ?")
    fun dailyLogonTimeCalculation() {
        logHandler.calculateUserLogonTime()
    }

    // @Scheduled(cron = "*/30 * * * * *")
    // fun periodicLogonTimeCalculation() {
    //    logHandler.calculateUserLogonTime()
    // }
}
