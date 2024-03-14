package pt.isel.odin.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

/*
@Entity
@Table(name = "role")
class Role(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val roleId: Long,

    @Column(nullable = false)
    val roleName: String
)*/
enum class Role {
    Student,
    Teacher,
    Admin

}
