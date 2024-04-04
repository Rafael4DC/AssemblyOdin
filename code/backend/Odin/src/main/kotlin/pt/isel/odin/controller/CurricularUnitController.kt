package pt.isel.odin.controller

import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pt.isel.odin.model.CurricularUnit
import pt.isel.odin.service.interfaces.CurricularUnitService

/**
 * Represents the controller that contains the endpoints related to the curricular units.
 */
@RestController
@RequestMapping("/curricularunits")
class CurricularUnitController(private val curricularUnitService: CurricularUnitService) {

    /**
     * Get a Curricular Unit by id
     *
     * @param id the Curricular Unit id
     */
    @GetMapping("/{id}")
    fun getById(@PathVariable id: Long): CurricularUnit? {
        return curricularUnitService.getById(id)
    }

    /**
     * Get all Curricular Units
     */
    @GetMapping
    fun getAll(): List<CurricularUnit> {
        return curricularUnitService.getAll()
    }

    /**
     * Save a Curricular Unit
     *
     * @param curricularUnit the Curricular Unit to save
     */
    @PostMapping("/save")
    fun save(@RequestBody curricularUnit: CurricularUnit): CurricularUnit {
        return curricularUnitService.save(curricularUnit)
    }

    /**
     * Update a Curricular Unit
     *
     * @param curricularUnit the Curricular Unit to update
     */
    @PutMapping("/update")
    fun update(@RequestBody curricularUnit: CurricularUnit): CurricularUnit {
        return curricularUnitService.save(curricularUnit)
    }

    /**
     * Delete a Curricular Unit by id
     *
     * @param id the Curricular Unit id
     */
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long) {
        curricularUnitService.delete(id)
    }
}
