package pt.isel.odin.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

/**
 * Represents a student in the system, an extension of [User]
 * for the cases where the user is a student, needing points
 * @property id the student id
 * @property username the student username
 * @property email the student email
 * @property role the student role, is a [Role]
 * @property pontos the student points
 */
@Entity
@Table(name = "students")
class Student(
    id: Long,
    username: String,
    email: String,
    role: Role,

    @Column(nullable = true) // Assuming points can be nullable or have a default value
    val pontos: Int

) : User(id, username, email, role)
