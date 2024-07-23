package pt.isel.odin.model

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import pt.isel.odin.model.user.User
import java.time.LocalDateTime

/**
 * Represents a credit log in the system.
 *
 * @property id the credit log id
 * @property description the description of the credit log
 * @property value the value used in the credit log
 * @property date the date of the credit log
 * @property user the user that is responsible for the credit log
 */
@Entity
@Table(name = "credit_log")
class CreditLog(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    val description: String = "",

    val value: Int,

    val date: LocalDateTime,

    @ManyToOne(fetch = FetchType.EAGER)
    val user: User
) {
    fun copy(
        id: Long? = this.id,
        description: String = this.description,
        value: Int = this.value,
        date: LocalDateTime = this.date,
        user: User = this.user
    ) = CreditLog(id, description, value, date, user)

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + description.hashCode()
        result = 31 * result + value
        result = 31 * result + date.hashCode()
        result = 31 * result + user.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CreditLog

        if (id != other.id) return false
        if (description != other.description) return false
        if (value != other.value) return false
        if (date != other.date) return false
        if (user != other.user) return false

        return true
    }
}
