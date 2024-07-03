package pt.isel.odin.http.controllers.user.models

import pt.isel.odin.model.Role

/**
 * Represents the output model to get all Roles
 *
 * @property roles The roles
 *
 * @constructor Creates a [GetAllRolesOutputModel] from a list of [Role]
 */
data class GetAllRolesOutputModel(
    val roles: List<GetRoleOutputModel>
)

/**
 * Creates a [GetAllRolesOutputModel] from a list of [Role]
 *
 * @param roles The roles
 *
 * @return The [GetAllRolesOutputModel]
 */
fun getAllRolesOutputModel(roles: List<Role>): GetAllRolesOutputModel {
    return GetAllRolesOutputModel(
        roles.map {
            GetRoleOutputModel(it)
        }
    )
}
