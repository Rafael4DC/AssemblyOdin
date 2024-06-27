package pt.isel.odin.http.controllers.user.models

import pt.isel.odin.model.CreditLog

/**
 * Represents the output model for the user logs.
 *
 * @property date the date of the log
 * @property description the description of the log
 * @property value the value of the log
 *
 * @constructor Creates a GetLogOutputModel instance.
 */
data class GetLogOutputModel(
    val date: String,
    val description: String,
    val value: Int
) {
    constructor(log: CreditLog) : this(
        date = log.date.toString(),
        description = log.description,
        value = log.value
    )
}
