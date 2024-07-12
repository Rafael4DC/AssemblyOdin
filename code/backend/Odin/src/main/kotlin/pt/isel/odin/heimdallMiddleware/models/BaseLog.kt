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
    Login,
    Logout,
    Lock
}


data class BaseLogInputModel(
    val _id: String,
    val identifier: String,
    val type: String,
    val timestamp: String,
    val machineName: String
)

val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSS'Z'")
data class BaseLog(
    val _id: String,
    val identifier: String,
    val type: LogType,
    val timestamp: LocalDateTime,
    val machineName: String
) {
    constructor(inputModel: BaseLogInputModel) : this(
        inputModel._id,
        inputModel.identifier,
        LogType.valueOf(inputModel.type),
        dtParser(inputModel.timestamp),
        inputModel.machineName
    )
}

fun dtParser(timestamp: String): LocalDateTime {
    val instant = Instant.parse(timestamp)
    return instant.toLocalDateTime(TimeZone.UTC)
}


data class LogPair(
    val logon: BaseLog,
    val logout: BaseLog
)