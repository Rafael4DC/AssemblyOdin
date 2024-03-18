package pt.isel.odin.model

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

/**
 * Represents a course in the system.
 *
 * @property id the course id
 * @property name the course name
 * @property description the course description
 */
@Entity
@Table(name = "course")
class Course( // Curricular Unit  - Unit curriculum
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val description: String
)
