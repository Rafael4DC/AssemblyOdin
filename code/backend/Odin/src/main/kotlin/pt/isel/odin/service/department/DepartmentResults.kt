package pt.isel.odin.service.department

import pt.isel.odin.model.Department
import pt.isel.odin.service.department.error.DeleteDepartmentError
import pt.isel.odin.service.department.error.GetDepartmentError
import pt.isel.odin.service.department.error.SaveUpdateDepartmentError
import pt.isel.odin.utils.Either

/**
 * Represents the result of a department get operation.
 *
 * @see GetDepartmentError
 * @see Department
 */
typealias GetDepartmentResult = Either<GetDepartmentError, Department>

/**
 * Represents the result of a department get all operations.
 *
 * @see GetDepartmentError
 * @see Department
 */
typealias GetAllDepartmentsResult = Either<GetDepartmentError, List<Department>>

/**
 * Represents the result of a department creation operation.
 *
 * @see SaveUpdateDepartmentError
 * @see Department
 */
typealias CreationDepartmentResult = Either<SaveUpdateDepartmentError, Department>

/**
 * Represents the result of a department delete operation.
 *
 * @see DeleteDepartmentError
 * @see Department
 */
typealias DeleteDepartmentResult = Either<DeleteDepartmentError, Department>