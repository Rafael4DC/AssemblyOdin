package pt.isel.odin.model

import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import kotlinx.datetime.LocalDate
import pt.isel.odin.utils.LocalDateConverter


@Entity
@Table(name = "tec")
class TEC(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id:Long,

    @ManyToOne
    val teacher:User,

    @ManyToOne
    val course:Course,

    @Convert(converter = LocalDateConverter::class)
    val date: LocalDate,

    val summary:String,

    @OneToMany
    val students: List<Student>
) {
}