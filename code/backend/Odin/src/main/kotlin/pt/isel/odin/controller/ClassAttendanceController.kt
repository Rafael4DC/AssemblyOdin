package pt.isel.odin.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.odin.controller.dto.classattendance.ClassAttendSaveInputModel
import pt.isel.odin.controller.dto.classattendance.ClassAttendUpdateInputModel
import pt.isel.odin.model.ClassAttendance
import pt.isel.odin.service.interfaces.ClassAttendanceService

/**
 * Represents the controller that contains the endpoints related to the class attendances.
 */
@RestController
@RequestMapping("/classattendances")
class ClassAttendanceController(private val classAttendanceService: ClassAttendanceService) {

    /**
     * Gets a class attendance by its id.
     *
     * @param id the class attendance id.
     */
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): ClassAttendance? {
        return classAttendanceService.getById(id)
    }

    /**
     * Gets all class attendances.
     */
    @GetMapping
    fun getAll(): List<ClassAttendance> {
        return classAttendanceService.getAll()
    }

    /**
     * Saves a class attendance.
     *
     * @param classAttendInputModel the class attendance info to save.
     */
    @PostMapping("/save")
    fun save(@RequestBody classAttendInputModel: ClassAttendSaveInputModel): ClassAttendance {
        return classAttendanceService.save(classAttendInputModel)
    }

    /**
     * Updates a class attendance.
     *
     * @param classAttendInputModel the class attendance info to update.
     */
    @PutMapping("/update")
    fun update(@RequestBody classAttendInputModel: ClassAttendUpdateInputModel): ClassAttendance {
        return classAttendanceService.update(classAttendInputModel)
    }

    /**
     * Deletes a class attendance by its id.
     *
     * @param id the class attendance id.
     */
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        classAttendanceService.delete(id)
    }
}
