package pt.isel.odin.http.controllers.section.models

import pt.isel.odin.model.Section

/**
 * Represents the output model for getting all sections.
 *
 * @property sections The sections.
 *
 * @constructor Creates a [GetAllSectionsOutputModel] from a list of [Section].
 */
data class GetAllSectionsOutputModel(
    val sections: List<GetSectionOutputModel>
)

/**
 * Creates a [GetAllSectionsOutputModel] from a list of [Section].
 *
 * @param sections The sections.
 *
 * @return The [GetAllSectionsOutputModel].
 */
fun getAllSectionsOutputModel(sections: List<Section>): GetAllSectionsOutputModel {
    return GetAllSectionsOutputModel(
        sections.map {
            GetSectionOutputModel(it)
        }
    )
}
