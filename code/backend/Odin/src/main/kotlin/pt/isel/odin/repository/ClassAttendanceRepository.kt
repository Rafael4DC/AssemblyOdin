package pt.isel.odin.repository

import org.springframework.data.jpa.repository.JpaRepository
import pt.isel.odin.model.ClassAttendance

/**
 * Repository for class attendance.
 */
interface ClassAttendanceRepository : JpaRepository<ClassAttendance, Long>{
    fun findByStudentEmail(email: String): List<ClassAttendance>
}
