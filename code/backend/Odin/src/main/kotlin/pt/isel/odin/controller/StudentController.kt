package pt.isel.odin.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.odin.model.Student
import pt.isel.odin.service.interfaces.StudentService

/**
 * Represents the controller that contains the endpoints related to the student.
 */
@RestController
@RequestMapping("/students")
class StudentController(private val studentService: StudentService) {

    /**
     * Gets a student by its id.
     *
     * @param id the student id.
     */
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): Student? {
        return studentService.getById(id)
    }

    /**
     * Gets all students.
     */
    @GetMapping
    fun getAll(): List<Student> {
        return studentService.getAll()
    }

    /**
     * Saves a student.
     *
     * @param student the student to save.
     */
    @PostMapping("/save")
    fun save(@RequestBody student: Student): Student {
        return studentService.save(student)
    }

    /**
     * Updates a student.
     *
     * @param student the student to update.
     */
    @PutMapping("/update")
    fun update(@RequestBody student: Student): Student {
        return studentService.save(student)
    }

    /**
     * Deletes a student by its id.
     *
     * @param id the student id.
     */
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        studentService.delete(id)
    }
}
