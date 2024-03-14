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

@Entity
@Table(name = "voc")
class VOC (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long,

    @OneToOne
    val student: Student,

    @ManyToOne
    val course: Course,

    @Convert(converter = LocalDateConverter::class)
    val date: LocalDate,

    @Convert(converter = DurationConverter::class)
    val length: Duration
) {
}