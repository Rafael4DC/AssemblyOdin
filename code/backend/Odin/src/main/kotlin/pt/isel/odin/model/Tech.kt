package pt.isel.odin.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import pt.isel.odin.model.user.User
import java.time.LocalDateTime

/**
 * Represents the Teoric lectures (tech) in the system.
 *
 * @property id the tech id
 * @property teacher the teacher that is responsible for the tech
 * @property section the course that the tech is about
 * @property date the date of the tech
 * @property summary the summary of the tech
 */
@Entity
@Table(name = "tech")
class Tech(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne
    val teacher: User,

    @ManyToOne
    val section: Section,

    val started: LocalDateTime,

    val ended: LocalDateTime,

    @Column(nullable = false)
    val summary: String = "",

    @ManyToMany(fetch = FetchType.EAGER)
    val missTech: MutableList<User> = mutableListOf()
) {
    /**
     * Creates a copy of the tech with the given values.
     *
     * @param id the tech id
     * @param teacher the teacher that is responsible for the tech
     * @param section the course that the tech is about
     * @param date the date of the tech
     * @param summary the summary of the tech
     * @param missTech the tech [User]
     *
     * @return the new [Tech]
     */
    fun copy(
        id: Long? = this.id,
        teacher: User = this.teacher,
        section: Section = this.section,
        started: LocalDateTime = this.started,
        ended: LocalDateTime = this.ended,
        summary: String = this.summary,
        missTech: MutableList<User> = this.missTech
    ) = Tech(id, teacher, section, started, ended, summary, missTech)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Tech) return false
        return id == other.id &&
            teacher == other.teacher &&
            section == other.section &&
            started == other.started &&
            ended == other.ended &&
            summary == other.summary &&
            missTech == other.missTech
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + teacher.hashCode()
        result = 31 * result + section.hashCode()
        result = 31 * result + started.hashCode()
        result = 31 * result + ended.hashCode()
        result = 31 * result + summary.hashCode()
        result = 31 * result + missTech.hashCode()
        return result
    }
}
