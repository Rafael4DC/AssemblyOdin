package pt.isel.odin.service

import org.springframework.stereotype.Component
import pt.isel.odin.http.controllers.voc.models.UpdateVocInputModel
import pt.isel.odin.model.CreditLog
import pt.isel.odin.model.Section
import pt.isel.odin.model.Voc
import pt.isel.odin.model.user.User
import pt.isel.odin.repository.CreditLogRepository
import pt.isel.odin.repository.SectionRepository
import pt.isel.odin.repository.UserRepository
import java.time.LocalDateTime

@Component
class ServiceUtils(
    private val userRepository: UserRepository,
    private val sectionRepository: SectionRepository,
    private val creditLogRepository: CreditLogRepository
) {
    fun getUser(userId: Long? = null, email: String): User? {
        val user = if (userId == null || userId == 0L) {
            userRepository.findByEmail(email)
        } else {
            userRepository.findById(userId)
        }
        return if (user.isEmpty) {
            null
        } else {
            user.get()
        }
    }

    fun getSection(sectionId: Long): Section? {
        val section = sectionRepository.findById(sectionId)
        return if (section.isEmpty) {
            null
        } else {
            section.get()
        }
    }

    fun vocPointsToUser(user: User, updateVocInputModel: UpdateVocInputModel, voc: Voc) {
        val clog =  creditLogRepository.save(CreditLog(
            null,
            "Points relative to VOC class",
            1,
            LocalDateTime.now(),
            user
        ))
        if(voc.approved != updateVocInputModel.approved) {
            userRepository.save(user.copy(credits = user.credits + 1))
        }
    }

    fun changePointsToUser(clog: CreditLog, credits:Int) {
        userRepository.save(clog.user.copy(credits = clog.user.credits + clog.value))
        creditLogRepository.save(clog)
    }

}
