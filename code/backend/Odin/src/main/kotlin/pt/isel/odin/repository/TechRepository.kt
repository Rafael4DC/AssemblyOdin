package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import pt.isel.odin.model.Tech

/**
 * Repository for techs.
 */
@Repository
interface TechRepository : JpaRepository<Tech, Long> {

    @Query(nativeQuery = true,
        value =
        """select t.id, t.teacher_id, t.curricular_unit_id, t.date, t.summary 
                from tech t right join class_attendance c on t.id = c.tech_id
                where c.student_id = :id""")
    fun getByStudentId(id: Long): List<Tech>

    @Query(nativeQuery = true,
        value =
        """select t.id, t.teacher_id, t.curricular_unit_id, t.date, t.summary 
                from tech t where t.teacher_id = :id""")
    fun getByUserId(id: Long): List<Tech>
}
