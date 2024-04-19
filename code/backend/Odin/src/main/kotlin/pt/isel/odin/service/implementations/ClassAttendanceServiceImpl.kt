package pt.isel.odin.service.implementations

import org.springframework.stereotype.Service
import pt.isel.odin.controller.dto.classattendance.ClassAttendanceRequest
import pt.isel.odin.controller.dto.classattendance.toClassAttendance
import pt.isel.odin.model.ClassAttendance
import pt.isel.odin.model.Student
import pt.isel.odin.model.Tech
import pt.isel.odin.model.copy
import pt.isel.odin.repository.ClassAttendanceRepository
import pt.isel.odin.service.exception.NotFoundException
import pt.isel.odin.service.interfaces.ClassAttendanceService

@Service
class ClassAttendanceServiceImpl(
    private val classAttendanceRepository: ClassAttendanceRepository
) : ClassAttendanceService {

    override fun getById(id: Long): ClassAttendance {
        return classAttendanceRepository.findById(id).orElseThrow { NotFoundException("No Class Attendance Found") }
    }

    override fun getAll(): List<ClassAttendance> {
        return classAttendanceRepository.findAll()
    }

    override fun save(classAttendRequest: ClassAttendanceRequest): ClassAttendance {
        return classAttendanceRepository.save(classAttendRequest.toClassAttendance())
    }

    override fun update(classAttendRequest: ClassAttendanceRequest): ClassAttendance {
        val classAttendance = getById(classAttendRequest.id!!)

        return classAttendanceRepository.save(
            classAttendance.copy(
                student = classAttendRequest.studentId?.let { Student(it) } ?: classAttendance.student,
                tech = classAttendRequest.techId?.let { Tech(it) } ?: classAttendance.tech,
                attended = classAttendRequest.attended ?: classAttendance.attended
            )
        )
    }

    override fun delete(id: Long) {
        classAttendanceRepository.deleteById(id)
    }
}
