package pt.isel.odin.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

/**
 * Represents the attendance of a student in a theorical class.
 *
 * @property id the class attendance id
 * @property student the student that attended the class
 * @property tech the class that the student attended
 * @property attended if the student attended the class
 */
@Entity
@Table(name = "class_attendance")
class ClassAttendance(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne // (cascade = CascadeType.)
    val student: Student? = null,

    @ManyToOne
    val tech: Tech? = null,

    @Column(nullable = false)
    val attended: Boolean? = null
)

fun ClassAttendance.copy(
    id: Long? = this.id,
    student: Student? = this.student,
    tech: Tech? = this.tech,
    attended: Boolean? = this.attended
) = ClassAttendance(id, student, tech, attended)
