package pt.isel.odin.service.implementations

import org.springframework.stereotype.Service
import pt.isel.odin.controller.dto.classattendance.ClassAttendSaveInputModel
import pt.isel.odin.controller.dto.classattendance.ClassAttendUpdateInputModel
import pt.isel.odin.model.ClassAttendance
import pt.isel.odin.model.copy
import pt.isel.odin.repository.ClassAttendanceRepository
import pt.isel.odin.repository.StudentRepository
import pt.isel.odin.repository.TechRepository
import pt.isel.odin.service.exception.NotFoundException
import pt.isel.odin.service.interfaces.ClassAttendanceService

@Service
class ClassAttendanceServiceImpl(
    private val classAttendanceRepository: ClassAttendanceRepository,
    private val studentRepository: StudentRepository,
    private val techRepository: TechRepository
) : ClassAttendanceService {

    override fun getById(id: Long): ClassAttendance {
        return classAttendanceRepository.findById(id).orElseThrow { NotFoundException("No Class Attendance Found") }
    }

    override fun getAll(): List<ClassAttendance> {
        return classAttendanceRepository.findAll()
    }

    override fun save(classAttendInputModel: ClassAttendSaveInputModel): ClassAttendance {
        val student = studentRepository.findById(classAttendInputModel.studentId)
            .orElseThrow { NotFoundException("No Student Found") }
        val tech = techRepository.findById(classAttendInputModel.techId)
            .orElseThrow { NotFoundException("No Tech Found") }

        return classAttendanceRepository.save(
            ClassAttendance(
                student = student,
                tech = tech,
                attended = classAttendInputModel.attended
            )
        )
    }

    override fun update(classAttendInputModel: ClassAttendUpdateInputModel): ClassAttendance {
        val classAttendance = classAttendanceRepository.findById(classAttendInputModel.id)
            .orElseThrow { NotFoundException("No Class Attendance Found") }
        val student = classAttendInputModel.studentId?.let {
            studentRepository.findById(it).orElseThrow { NotFoundException("No Student Found") }
        } ?: classAttendance.student
        val tech = classAttendInputModel.techId?.let {
            techRepository.findById(it).orElseThrow { NotFoundException("No Tech Found") }
        } ?: classAttendance.tech

        return classAttendanceRepository.save(
            classAttendance.copy(
                student = student,
                tech = tech,
                attended = classAttendInputModel.attended ?: classAttendance.attended
            )
        )
    }

    override fun delete(id: Long) {
        classAttendanceRepository.deleteById(id)
    }
}
