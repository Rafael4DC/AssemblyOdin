package pt.isel.odin.service.classattendance

import org.springframework.stereotype.Service
import pt.isel.odin.model.ClassAttendance
import pt.isel.odin.model.copy
import pt.isel.odin.repository.ClassAttendanceRepository
import pt.isel.odin.service.NotFoundException

@Service
class ClassAttendanceService(
    private val classAttendanceRepository: ClassAttendanceRepository
) {

    fun getById(id: Long): ClassAttendance {
        return classAttendanceRepository.findById(id).orElseThrow { NotFoundException("No Class Attendance Found") }
    }

    fun getAll(): List<ClassAttendance> {
        return classAttendanceRepository.findAll()
    }

    fun save(classAttendRequest: ClassAttendance): ClassAttendance {
        return classAttendanceRepository.save(classAttendRequest)
    }

    fun update(classAttendRequest: ClassAttendance): ClassAttendance {
        val classAttendance = getById(classAttendRequest.id!!)

        return classAttendanceRepository.save(
            classAttendance.copy(
                student = classAttendRequest.student ?: classAttendance.student,
                tech = classAttendRequest.tech ?: classAttendance.tech,
                attended = classAttendRequest.attended ?: classAttendance.attended
            )
        )
    }

    fun delete(id: Long) {
        classAttendanceRepository.deleteById(id)
    }

    fun getByTechId(techId: Long): List<ClassAttendance> {
        return classAttendanceRepository.findByTechId(techId)
    }
}
