package pt.isel.odin.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.Table
/**
 * Represents a user in the system, mainly used to represent Teacher and Admins
 * @property id the user id
 * @property username the user username
 * @property email the user email
 * @property role the user role, is a [Role]
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
open class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long,

    @Column(nullable = false)
    open val username: String,

    @Column(nullable = false)
    open val email: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    open val role: Role
)
