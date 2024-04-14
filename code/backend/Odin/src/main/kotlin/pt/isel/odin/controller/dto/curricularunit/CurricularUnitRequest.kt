package pt.isel.odin.controller.dto.curricularunit

import pt.isel.odin.model.CurricularUnit

class CurricularUnitRequest(
    val id: Long?,
    val name: String?,
    val description: String?
)

fun CurricularUnitRequest.toCurricularUnit(): CurricularUnit {
    return CurricularUnit(
        id = id,
        name = name,
        description = description
    )
}
