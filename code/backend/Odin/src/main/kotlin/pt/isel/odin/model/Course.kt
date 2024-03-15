package pt.isel.odin.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

/**
 * Represents a course in the system.
 *
 * @property id the course id
 * @property name the course name
 * @property descriptions the course description
 * @property type the course type, is a [CourseType]
 */
@Entity
@Table(name = "courses")
class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val descriptions: String,

    @ManyToOne
    @JoinColumn(name = "course_type_id", referencedColumnName = "id")
    val type: CourseType
)
