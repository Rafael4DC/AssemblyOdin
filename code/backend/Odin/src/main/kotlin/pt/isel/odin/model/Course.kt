package pt.isel.odin.model

import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
/**
 * Represents a course in the system
 * @property id the course id
 * @property name the course name
 * @property descriptions the course description
 * @property type the course type, is a [CourseType]
 */
@Entity
@Table(name = "course")
class Course(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val descriptions: String,

    @Enumerated(EnumType.STRING)
    val type: CourseType
)
