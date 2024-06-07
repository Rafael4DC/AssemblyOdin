package pt.isel.odin.service.voc

import org.springframework.stereotype.Service
import pt.isel.odin.http.controllers.voc.models.VocRequest
import pt.isel.odin.model.Module
import pt.isel.odin.model.Student
import pt.isel.odin.model.Voc
import pt.isel.odin.model.copy
import pt.isel.odin.repository.VocRepository
import pt.isel.odin.service.NotFoundException
import pt.isel.odin.service.student.StudentService
import pt.isel.odin.service.user.UserService

@Service
class VocService(
    private val vocRepository: VocRepository,
    private val studentService: StudentService,
    private val userService: UserService
) {

    fun getById(id: Long): Voc {
        return vocRepository.findById(id).orElseThrow { NotFoundException("No Voc Found") }
    }

    fun getAll(): List<Voc> {
        return vocRepository.findAll()
    }

    fun save(vocRequest: Voc, email: String): Voc {
        val voc = vocRequest.copy(
            student = vocRequest.student ?: studentService.getByEmail(email)
        )
        return vocRepository.save(voc)
    }

    fun update(vocRequest: VocRequest): Voc {
        val voc = getById(vocRequest.id!!)

        return vocRepository.save(
            voc.copy(
                description = vocRequest.description ?: voc.description,
                approved = vocRequest.approved ?: voc.approved,
                student = vocRequest.studentId?.let { Student(it) } ?: voc.student,
                curricularUnit = vocRequest.moduleId?.let { Module(it) } ?: voc.module,
                started = vocRequest.started ?: voc.started,
                ended = vocRequest.ended ?: voc.ended
            )
        )
    }

    fun delete(id: Long) {
        vocRepository.deleteById(id)
    }

    fun getByStudent(email: String): List<Voc> {
        val student = studentService.getByEmail(email) ?: userService.getByEmail(email) ?: throw NotFoundException("No Student Found")
        return vocRepository.findByStudentId(student.id!!)
    }
}
