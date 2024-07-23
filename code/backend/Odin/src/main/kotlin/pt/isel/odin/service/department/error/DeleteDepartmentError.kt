package pt.isel.odin.service.department.error

/**
 * Represents the department delete error.
 */
sealed class DeleteDepartmentError {
    data object NotFoundDepartment : DeleteDepartmentError()
}
