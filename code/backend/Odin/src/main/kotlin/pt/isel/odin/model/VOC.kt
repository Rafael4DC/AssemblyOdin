package pt.isel.odin.model

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
 * Represents the Pratical classes (VOC) in the system
 * @property id the VOC id
 * @property student the student that is responsible for the VOC
 * @property course the course that the VOC is about
 * @property date the date of the VOC
 * @property length the length of the VOC
 */
@Entity
@Table(name = "voc")
class VOC(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @OneToOne
    val student: Student,

    @ManyToOne
    val course: Course,

    @Convert(converter = LocalDateConverter::class)
    val date: LocalDate,

    @Convert(converter = DurationConverter::class)
    val length: Duration
)
