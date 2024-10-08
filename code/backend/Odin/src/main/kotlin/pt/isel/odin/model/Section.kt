package pt.isel.odin.model

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import pt.isel.odin.model.user.User

/**
 * Represents a section in the system.
 *
 * @property id the section id
 * @property name the section name
 * @property module the section [Module]
 * @property students the section [User]
 */
@Entity
@Table(name = "section")
class Section(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String,

    @ManyToOne
    @JoinColumn(name = "module_id")
    var module: Module? = null,

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    val students: MutableList<User> = mutableListOf()
) {
    /**
     * Creates a copy of the section with the given values.
     *
     * @param id the section id
     * @param name the section name
     * @param module the section [Module]
     * @param students the section [User]
     *
     * @return the new [Section]
     */
    fun copy(
        id: Long? = this.id,
        name: String = this.name,
        module: Module? = this.module,
        students: MutableList<User> = this.students
    ) = Section(id, name, module, students)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Section) return false
        return id == other.id &&
            name == other.name &&
            students == other.students
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + students.hashCode()
        return result
    }
}
