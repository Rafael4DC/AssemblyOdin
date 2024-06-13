package pt.isel.odin.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import pt.isel.odin.model.user.User
import java.time.LocalDateTime

/**
 * Represents the Teoric lectures (TEC) in the system.
 *
 * @property id the TEC id
 * @property teacher the teacher that is responsible for the TEC
 * @property section the course that the TEC is about
 * @property date the date of the TEC
 * @property summary the summary of the TEC
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

    val date: LocalDateTime,

    @Column(nullable = false)
    val summary: String,

    @ManyToMany
    val missTech: MutableList<User> = mutableListOf()
) {
    fun copy(
        id: Long? = this.id,
        teacher: User = this.teacher,
        section: Section = this.section,
        date: LocalDateTime = this.date,
        summary: String = this.summary,
        missTech: MutableList<User> = this.missTech
    ) = Tech(id, teacher, section, date, summary, missTech)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Tech) return false
        return id == other.id &&
                teacher == other.teacher &&
                section == other.section &&
                date == other.date &&
                summary == other.summary &&
                missTech == other.missTech
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + teacher.hashCode()
        result = 31 * result + section.hashCode()
        result = 31 * result + date.hashCode()
        result = 31 * result + summary.hashCode()
        result = 31 * result + missTech.hashCode()
        return result
    }
}
