package pt.isel.odin.service

import org.springframework.stereotype.Component
import pt.isel.odin.model.Section
import pt.isel.odin.model.user.User
import pt.isel.odin.repository.SectionRepository
import pt.isel.odin.repository.UserRepository

@Component
class ServiceUtils(
    private val userRepository: UserRepository,
    private val sectionRepository: SectionRepository
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
}