package pt.isel.odin.model

import jakarta.persistence.Column
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table


/**
 * There should be a better way to do this
 * TODO() Fix it later
 */
@Entity
@Table(name = "student")
class Student(
    id: Long,
    username: String,
    email: String,
    role: Role,

    @Column(nullable = true) // Assuming points can be nullable or have a default value
    val pontos: Int

) : User(id, username, email, role)