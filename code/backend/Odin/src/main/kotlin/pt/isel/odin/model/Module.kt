package pt.isel.odin.model

import com.fasterxml.jackson.annotation.JsonBackReference
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

@Entity
@Table(name = "module")
class Module(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JoinColumn(name = "field_study_id", nullable = false)
    @JsonBackReference
    val fieldStudy: FieldStudy,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = true)
    val tier: Int = 1,

/*    @OneToMany(mappedBy = "module")
    val sections: MutableList<Section> = mutableListOf()*/
) {
    fun copy(
        id: Long? = this.id,
        fieldStudy: FieldStudy = this.fieldStudy,
        name: String = this.name,
        tier: Int = this.tier
    ) = Module(id, fieldStudy, name, tier)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Module) return false
        return id == other.id &&
                fieldStudy == other.fieldStudy &&
                name == other.name &&
                tier == other.tier
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + fieldStudy.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + tier
        return result
    }

}

