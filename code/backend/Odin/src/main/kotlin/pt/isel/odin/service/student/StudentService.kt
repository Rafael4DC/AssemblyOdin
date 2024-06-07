package pt.isel.odin.service.student

import org.springframework.stereotype.Service
import pt.isel.odin.http.controllers.student.models.StudentRequest
import pt.isel.odin.http.controllers.student.models.toStudent
import pt.isel.odin.model.Student
import pt.isel.odin.repository.StudentRepository
import pt.isel.odin.service.NotFoundException

@Service
class StudentService(private val studentRepository: StudentRepository) {

    fun getById(id: String): Student {
        return studentRepository.findById(id).orElseThrow { NotFoundException("No Student Found") }
    }

    fun getByEmail(email: String): Student? {
        return studentRepository.findByEmail(email)
    }

    fun getAll(): List<Student> {
        return studentRepository.findAll()
    }

    fun save(studentRequest: StudentRequest): Student {
        return studentRepository.save(studentRequest.toStudent())
    }

    fun delete(id: String) {
        studentRepository.deleteById(id)
    }
}
