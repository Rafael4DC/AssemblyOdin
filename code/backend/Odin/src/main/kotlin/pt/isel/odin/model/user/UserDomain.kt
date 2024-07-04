package pt.isel.odin.model.user

import org.springframework.stereotype.Component
import pt.isel.odin.model.user.User.Companion.MAX_EMAIL_SIZE
import pt.isel.odin.model.user.User.Companion.MAX_NAME_SIZE
import pt.isel.odin.model.user.User.Companion.MIN_EMAIL_SIZE
import pt.isel.odin.model.user.User.Companion.MIN_NAME_SIZE

/**
 * Represents the user domain.
 */
@Component
class UserDomain {

    companion object {
        private val usernameRegex = "^[\\S ]{$MIN_NAME_SIZE,$MAX_NAME_SIZE}$".toRegex()
        private val emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()
    }

    /**
     * Checks if the given name is valid.
     *
     * @param name The name.
     * @return True if the name is valid, false otherwise.
     */
    fun isUsernameValid(name: String): Boolean {
        return usernameRegex.matches(name)
    }

    fun isEmailValid(email: String): Boolean {
        if (email.length < MIN_EMAIL_SIZE || email.length > MAX_EMAIL_SIZE) return false
        return emailRegex.matches(email)
    }
}
