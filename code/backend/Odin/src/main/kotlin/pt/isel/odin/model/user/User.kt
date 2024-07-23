package pt.isel.odin.model.user

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import pt.isel.odin.model.Role

/**
 * Represents a user in the system, mainly used to represent Teacher and Admins.
 *
 * @property id the user id
 * @property username the user username
 * @property email the user email
 * @property credits the user credits
 * @property role the user role, is a [Role]
 */
@Entity
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val username: String,

    @Column(nullable = false)
    val credits: Int = 0,

    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    val role: Role
) {
    companion object {
        const val MIN_EMAIL_SIZE = 6
        const val MAX_EMAIL_SIZE = 320
        const val MIN_NAME_SIZE = 3
        const val MAX_NAME_SIZE = 50
    }

    /**
     * Creates a copy of the user with the given values.
     *
     * @param id the user id
     * @param email the user email
     * @param username the user username
     * @param credits the user credits
     * @param role the user role
     *
     * @return the new user
     */
    fun copy(
        id: Long? = this.id,
        email: String = this.email,
        username: String = this.username,
        credits: Int = this.credits,
        role: Role = this.role
    ) = User(id, email, username, credits, role)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false
        return id == other.id &&
            email == other.email &&
            username == other.username &&
            credits == other.credits &&
            role == other.role
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + email.hashCode()
        result = 31 * result + username.hashCode()
        result = 31 * result + credits
        result = 31 * result + role.hashCode()
        return result
    }
}
