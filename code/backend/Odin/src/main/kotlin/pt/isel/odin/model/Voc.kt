package pt.isel.odin.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import pt.isel.odin.model.user.User
import java.time.LocalDateTime

/**
 * Represents the Pratical classes (VOC) in the system.
 *
 * @property id the VOC id
 * @property description the description of the VOC
 * @property user the student that is responsible for the VOC
 * @property module the course that the VOC is about
 * @property started the date of the VOC
 * @property ended the length of the VOC
 * @property approved if the VOC was approved
 */
@Entity
@Table(name = "voc")
class Voc(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val approved: Boolean,

    @ManyToOne
    val user: User,

    @ManyToOne
    val module: Module,

    val started: LocalDateTime,

    val ended: LocalDateTime
) {
    fun copy(
        id: Long? = this.id,
        description: String = this.description,
        approved: Boolean = this.approved,
        user: User = this.user,
        module: Module = this.module,
        started: LocalDateTime = this.started,
        ended: LocalDateTime = this.ended
    ) = Voc(id, description, approved, user, module, started, ended)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Voc) return false
        return id == other.id &&
                description == other.description &&
                approved == other.approved &&
                user == other.user &&
                module == other.module &&
                started == other.started &&
                ended == other.ended
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + description.hashCode()
        result = 31 * result + approved.hashCode()
        result = 31 * result + user.hashCode()
        result = 31 * result + module.hashCode()
        result = 31 * result + started.hashCode()
        result = 31 * result + ended.hashCode()
        return result
    }
}