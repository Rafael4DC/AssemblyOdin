package pt.isel.odin.heimdallMiddleware.models

import kotlinx.datetime.LocalDateTime
import pt.isel.odin.model.Department

/**
 * Represents the output model for getting a department.
 *
 * @property id The department id.
 * @property name The department name.
 * @property fieldsStudy The department fields study.
 *
 * @constructor Creates a [GetDepartmentOutputModel] from a [Department].
 */
data class ProcessedLog(
    val username: String,
    val email: String,
    val type: String,
    val timestamp: List<LocalDateTime>,
    val machienName: String
) {
    /*fun copy(
        id: Long? = this.username,
        description: String = this.type,
        value: Int = this.timestamp
    ) = CreditLog(id, description, value, date, user)

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + description.hashCode()
        result = 31 * result + value
        result = 31 * result + date.hashCode()
        result = 31 * result + user.hashCode()
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CreditLog

        if (id != other.id) return false
        if (description != other.description) return false
        if (value != other.value) return false
        if (date != other.date) return false
        if (user != other.user) return false

        return true
    }*/
}
