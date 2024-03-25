package pt.isel.odin.model

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import kotlinx.datetime.LocalDate
import pt.isel.odin.utils.DurationConverter
import pt.isel.odin.utils.LocalDateConverter
import kotlin.time.Duration

/**
 * Represents the Pratical classes (VOC) in the system.
 *
 * @property id the VOC id
 * @property description the description of the VOC
 * @property student the student that is responsible for the VOC
 * @property curricularUnit the course that the VOC is about
 * @property date the date of the VOC
 * @property length the length of the VOC
 * @property approved if the VOC was approved
 */
@Entity
@Table(name = "voc")
class Voc(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val approved: Boolean,

    @OneToOne
    val student: Student,

    @ManyToOne
    val curricularUnit: CurricularUnit,

    @Convert(converter = LocalDateConverter::class)
    val date: LocalDate,

    @Convert(converter = DurationConverter::class)
    val length: Duration
)
