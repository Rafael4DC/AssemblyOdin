package pt.isel.odin.service.implementations

import org.springframework.stereotype.Service
import pt.isel.odin.controller.dto.student.StudentRequest
import pt.isel.odin.controller.dto.student.toStudent
import pt.isel.odin.model.Student
import pt.isel.odin.repository.StudentRepository
import pt.isel.odin.service.exception.NotFoundException
import pt.isel.odin.service.interfaces.StudentService

@Service
class StudentServiceImpl(private val studentRepository: StudentRepository) : StudentService {

    override fun getById(id: String): Student {
        return studentRepository.findById(id).orElseThrow { NotFoundException("No Student Found") }
    }

    override fun getAll(): List<Student> {
        return studentRepository.findAll()
    }

    override fun save(studentRequest: StudentRequest): Student {
        return studentRepository.save(studentRequest.toStudent())
    }

    override fun delete(id: String) {
        studentRepository.deleteById(id)
    }
}
