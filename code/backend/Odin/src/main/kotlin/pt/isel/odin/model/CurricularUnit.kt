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

@Entity
@Table(name = "categories")
class Category(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String? = null,

    @OneToMany(mappedBy = "category", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JsonManagedReference
    val subCategories: List<SubCategory>? = emptyList()
)

@Entity
@Table(name = "subcategories")
class SubCategory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonBackReference
    val category: Category? = null,

    @Column(nullable = false)
    val name: String? = null,

    @OneToMany(mappedBy = "subCategory", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    @JsonManagedReference
    val modules: List<Module>? = emptyList()
)

@Entity
@Table(name = "modules")
class Module(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "subcategory_id", nullable = false)
    @JsonBackReference
    val subCategory: SubCategory? = null,

    @Column(nullable = false)
    val name: String? = null,

    @Column(nullable = false)
    val tier: Int? = null
)

