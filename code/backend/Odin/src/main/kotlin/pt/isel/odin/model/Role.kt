package pt.isel.odin.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

/**
 * Represents a role in the system.
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
    /**
     * Creates a copy of the role with the given values.
     *
     * @param id the role id
     * @param name the role name
     *
     * @return the new [Role]
     */
    fun copy(
        id: Long? = this.id,
        name: String? = this.name
    ) = Role(id, name)

    /**
     * Enum class that represents the role types.
     */
    enum class RoleEnum {
        ADMIN,
        TEACHER,
        STUDENT
    }
}
