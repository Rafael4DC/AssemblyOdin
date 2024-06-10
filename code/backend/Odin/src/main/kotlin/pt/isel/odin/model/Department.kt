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

@Entity
@Table(name = "department")
class Department(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String,

    @OneToMany(mappedBy = "department", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JsonManagedReference
    val fieldsStudy: List<FieldStudy> = emptyList()
) {
    fun copy(
        id: Long? = this.id,
        name: String = this.name,
        subCategories: List<FieldStudy> = this.fieldsStudy
    ) = Department(id, name, subCategories)

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
