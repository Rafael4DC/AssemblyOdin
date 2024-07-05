package pt.isel.odin.http.controllers.tech.models

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

/**
 * Schedule multiple techs input model.
 *
 * @property teacher The tech teacher.
 * @property section The tech section.
 * @property startDate start of the period.
 * @property endDate end of the period.
 * @property classTime The class time.
 * @property classLengthHours The class length in hours.
 * @property dayOfWeek The day of the week.
 */
data class SaveScheduleTechInputModel(
    val teacher: Long? = null,
    val section: Long,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val classTime: LocalTime,
    val classLengthHours: Long,
    val dayOfWeek: DayOfWeek
)
