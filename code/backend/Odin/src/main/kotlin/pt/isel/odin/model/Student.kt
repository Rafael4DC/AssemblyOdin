package pt.isel.odin.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

/**
 * Represents a student in the system, an extension of [User]
 * for the cases where the user is a student, needing points.
 *
 * @property id the student id
 * @property username the student username
 * @property email the student email
 * @property role the student role, is a [Role]
 * @property credits the student points
 */
@Entity
@Table(name = "student")
class Student(
    id: Long,
    username: String,
    email: String,
    role: Role,

    @Column(nullable = false)
    val credits: Int = 0

) : User(id, username, email, role)
