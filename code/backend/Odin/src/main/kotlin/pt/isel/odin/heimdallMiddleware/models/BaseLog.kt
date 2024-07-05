package pt.isel.odin.heimdallMiddleware.models

import java.time.LocalDateTime


/**
 * Represents the input model for saving a department.
 *
 * @property name The department name.
 */

data class BaseLog(
    val identifier: String,
    val type: String,
    val timestamp: LocalDateTime,
    val machineName: String
)
data class BaseLogModel(
    val identifier: String,
    val type: String,
    val timestamp: String,
    val machineName: String
)
