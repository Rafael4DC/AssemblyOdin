package pt.isel.odin.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

/**
 * Represents a field study in the system.
 *
 * @property id the field study id
 * @property department the field study [Department]
 * @property name the field study name
 * @property modules the field study [Module]
 */
@Entity
@Table(name = "field_study")
class FieldStudy(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    @JsonBackReference
    val department: Department,

    @Column(nullable = false)
    val name: String,

    val description: String? = null,

    @OneToMany(mappedBy = "fieldStudy", fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val modules: List<Module> = emptyList()
) {
    /**
     * Creates a copy of the field study with the given values.
     *
     * @param id the field study id
     * @param department the field study [Department]
     * @param name the field study name
     * @param modules the field study [Module]
     *
     * @return the new [FieldStudy]
     */
    fun copy(
        id: Long? = this.id,
        department: Department = this.department,
        name: String = this.name,
        description: String? = this.description,
        modules: List<Module> = this.modules
    ) = FieldStudy(id, department, name, description, modules)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FieldStudy) return false
        return id == other.id &&
            department == other.department &&
            name == other.name &&
            modules == other.modules
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + department.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + modules.hashCode()
        return result
    }
}
