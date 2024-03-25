package pt.isel.odin.model

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import kotlinx.datetime.LocalDate
import pt.isel.odin.utils.LocalDateConverter

/**
 * Represents the Teoric lectures (TEC) in the system.
 *
 * @property id the TEC id
 * @property teacher the teacher that is responsible for the TEC
 * @property curricularUnit the course that the TEC is about
 * @property date the date of the TEC
 * @property summary the summary of the TEC
 * @property students the students that are in the TEC
 */
@Entity
@Table(name = "tech")
class Tech(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne
    val teacher: User,

    @ManyToOne
    val curricularUnit: CurricularUnit,

    @Convert(converter = LocalDateConverter::class)
    val date: LocalDate,

    @Column(nullable = false)
    val summary: String
)
