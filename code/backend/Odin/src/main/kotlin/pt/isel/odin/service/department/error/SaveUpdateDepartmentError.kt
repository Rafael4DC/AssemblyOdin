package pt.isel.odin.service.department.error

/**
 * Represents the department creation error.
 */
sealed class SaveUpdateDepartmentError {
    data object AlreadyExistsDepartment : SaveUpdateDepartmentError()
    data object NotFoundDepartment : SaveUpdateDepartmentError()
    data object IncorrectNameDepartment : SaveUpdateDepartmentError()
}
