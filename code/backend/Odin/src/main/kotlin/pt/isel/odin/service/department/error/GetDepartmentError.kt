package pt.isel.odin.service.department.error

/**
 * Represents the department get error.
 */
sealed class GetDepartmentError {
    data object NotFoundDepartment : GetDepartmentError()
}