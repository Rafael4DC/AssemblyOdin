package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import pt.isel.odin.model.Voc

/**
 * Repository for vocs.
 */
@Repository
interface VocRepository : JpaRepository<Voc, Long>{

    fun findByStudentId(studentId: Long): List<Voc>
}
