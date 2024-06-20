package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import pt.isel.odin.model.Tech
import pt.isel.odin.model.user.User
import java.util.*

/**
 * Repository for Techs.
 */
@Repository
interface TechRepository : JpaRepository<Tech, Long> {
    /**
     * Finds all techs by the teacher.
     *
     * @param user the teacher
     *
     * @return the list of [Tech] if found, [Optional.empty] otherwise
     */
    fun findByTeacher(user: User): Optional<List<Tech>>

    /**
     * Finds all techs by the student.
     *
     * @param studentId the student id
     *
     * @return the list of [Tech] if found, [Optional.empty] otherwise
     */
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
