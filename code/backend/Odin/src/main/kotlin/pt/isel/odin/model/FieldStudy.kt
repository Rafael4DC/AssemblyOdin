package pt.isel.odin.model

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
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

    @OneToMany(mappedBy = "fieldStudy", fetch = FetchType.EAGER)
    @JsonManagedReference
    val modules: List<Module> = emptyList()
) {
    fun copy(
        id: Long? = this.id,
        department: Department = this.department,
        name: String = this.name,
        modules: List<Module> = this.modules
    ) = FieldStudy(id, department, name, modules)

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
