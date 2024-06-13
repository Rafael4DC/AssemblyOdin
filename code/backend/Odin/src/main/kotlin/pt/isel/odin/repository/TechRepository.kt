package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import pt.isel.odin.model.Tech
import pt.isel.odin.model.user.User
import java.util.Optional

/**
 * Repository for Techs.
 */
@Repository
interface TechRepository : JpaRepository<Tech, Long> {
    fun findByTeacher(user: User): Optional<List<Tech>>


    @Query(
        nativeQuery = true,
        value =
        """SELECT t.id, t.date, t.summary, t.section_id, t.teacher_id FROM tech t
                JOIN section s ON s.id = t.section_id
                JOIN section_students ss ON ss.section_id = s.id
                WHERE ss.students_id = :studentId"""
    )
    fun findByStudent(studentId: Long): Optional<List<Tech>>
}
