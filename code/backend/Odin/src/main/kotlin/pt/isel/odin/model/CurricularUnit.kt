package pt.isel.odin.model

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
class CurricularUnit( // Curricular Unit  - Unit curriculum
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val name: String,
    val description: String
)
