package pt.isel.odin.heimdallMiddleware.models

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter


/**
 * Represents the input model for saving a department.
 *
 * @property name The department name.
 */

enum class LogType {
    Logon,
    Logout,
    Lock
}


data class BaseLogInputModel(
    val identifier: String,
    val type: String,
    val timestamp: String,
    val machineName: String
)

private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'")
data class BaseLog(
    val identifier: String,
    val type: LogType,
    val timestamp: LocalDateTime,
    val machineName: String
) {
    constructor(inputModel: BaseLogInputModel) : this(
        inputModel.identifier,
        LogType.valueOf(inputModel.type),
        Instant.parse(inputModel.timestamp).toLocalDateTime(TimeZone.UTC), //LocalDateTime.parse(inputModel.timestamp,formatter),
        inputModel.machineName
    )
}