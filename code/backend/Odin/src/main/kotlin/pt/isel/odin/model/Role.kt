package pt.isel.odin.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

/**
 * Represents a course type in the system.
 *
 * @property id the course type id
 * @property name the course type name
 */
@Entity
@Table(name = "role")
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(unique = true, nullable = false)
    val name: String? = null
) {
    fun copy(
        id: Long? = this.id,
        name: String? = this.name
    ) = Role(id, name)

    enum class RoleEnum {
        ADMIN,
        TEACHER,
        STUDENT
    }
}
