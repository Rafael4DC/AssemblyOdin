package pt.isel.odin.model

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

/**
 * Represents a department in the system.
 *
 * @property id the department id
 * @property name the department name
 * @property fieldsStudy the department [FieldStudy]
 */
@Entity
@Table(name = "department")
class Department(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String,

    val description: String? = null,

    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER, cascade = [CascadeType.ALL], orphanRemoval = true)
    @JsonManagedReference
    val fieldsStudy: List<FieldStudy> = emptyList()
) {
    /**
     * Creates a copy of the department with the given values.
     *
     * @param id the department id
     * @param name the department name
     * @param fieldsStudy the department [FieldStudy]
     *
     * @return the new [Department]
     */
    fun copy(
        id: Long? = this.id,
        name: String = this.name,
        description: String? = this.description,
        fieldsStudy: List<FieldStudy> = this.fieldsStudy
    ) = Department(id, name, description, fieldsStudy)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Department) return false
        return id == other.id &&
            name == other.name &&
            fieldsStudy == other.fieldsStudy
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + fieldsStudy.hashCode()
        return result
    }
}
