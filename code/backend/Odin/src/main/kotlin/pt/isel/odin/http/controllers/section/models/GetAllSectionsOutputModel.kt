package pt.isel.odin.http.controllers.section.models

import pt.isel.odin.model.Section

data class GetAllSectionsOutputModel(
    val sections: List<GetSectionOutputModel>
)

fun getAllSectionsOutputModel(sections: List<Section>): GetAllSectionsOutputModel {
    return GetAllSectionsOutputModel(
        sections.map {
            GetSectionOutputModel(it)
        }
    )
}
