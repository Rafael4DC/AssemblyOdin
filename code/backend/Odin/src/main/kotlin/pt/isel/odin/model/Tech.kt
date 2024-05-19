package pt.isel.odin.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

/**
 * Represents the Teoric lectures (TEC) in the system.
 *
 * @property id the TEC id
 * @property teacher the teacher that is responsible for the TEC
 * @property module the course that the TEC is about
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
    val teacher: User? = null,

    @ManyToOne
    val module: Module? = null,

    val date: LocalDateTime? = null,

    @Column(nullable = false)
    val summary: String? = null
)

fun Tech.copy(
    id: Long? = this.id,
    teacher: User? = this.teacher,
    curricularUnit: Module? = this.module,
    date: LocalDateTime? = this.date,
    summary: String? = this.summary
) = Tech(id, teacher, curricularUnit, date, summary)
