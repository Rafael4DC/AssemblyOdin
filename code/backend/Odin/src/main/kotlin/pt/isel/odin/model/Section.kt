package pt.isel.odin.model

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

@Entity
@Table(name = "section")
class Section(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val summary: String,

    @ManyToOne
    @JoinColumn(name = "module_id")
    var module: Module? = null,

    @ManyToMany(fetch = FetchType.EAGER)
    val students: MutableList<User> = mutableListOf()
) {
    fun copy(
        id: Long? = this.id,
        name: String = this.name,
        summary: String = this.summary,
        module: Module? = this.module,
        students: MutableList<User> = this.students
    ) = Section(id, name, summary, module, students)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Section) return false
        return id == other.id &&
            name == other.name &&
            summary == other.summary &&
            students == other.students
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + name.hashCode()
        result = 31 * result + summary.hashCode()
        result = 31 * result + students.hashCode()
        return result
    }
}
