package pt.isel.odin.http.controllers.tech.models

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

data class UpdateScheduleTechInputModel (
    val teacher: Long? = null,
    val section: Long,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val classTime: LocalTime,
    val classLengthHours: Long,
    val dayOfWeek: DayOfWeek,
)