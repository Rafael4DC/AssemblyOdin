package pt.isel.odin.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

/**
 * Represents a curricular unit in the system
 * is meant to represent to what Curricular unit a TEC or a VOC is related to
 * so that we can know the relation between the TEC/VOC and the UC
 * @property id the course id
 * @property name the course name
 * @property description the course description
 */
@Entity
@Table(name = "curricular_unit")
class CurricularUnit(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val description: String
)
