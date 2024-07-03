package pt.isel.odin.http.controllers

import org.springframework.web.util.UriTemplate
import java.net.URI

/**
 * Represents the URIs.
 */
object Uris {

    const val PREFIX = "/api"
    const val HOME = PREFIX

    fun home(): URI = URI(HOME)

    /**
     * Represents the user URIs.
     */
    object Users {
        const val RESOURCE = "$PREFIX/users"

        const val GET_BY_ID = "/{id}"
        const val SAVE = "/save"
        const val UPDATE = "/update"
        const val DELETE = "/{id}"
        const val SESSION = "/session"
        const val GET_LOGS = "/logs"
        const val GET_STUDENTS = "/students"
        const val GET_ROLES = "/roles"

        const val LOGOUT = "$PREFIX/logout"

        fun byId(id: Long?) = UriTemplate(GET_BY_ID).expand(id)
    }

    /**
     * Represents the department URIs.
     */
    object Departments {
        const val RESOURCE = "$PREFIX/departments"

        const val GET_BY_ID = "/{id}"
        const val SAVE = "/save"
        const val UPDATE = "/update"
        const val DELETE = "/{id}"

        fun byId(id: Long?) = UriTemplate(GET_BY_ID).expand(id)
    }

    /**
     * Represents the field of study URIs.
     */
    object FieldsStudy {
        const val RESOURCE = "$PREFIX/fieldsstudy"

        const val GET_BY_ID = "/{id}"
        const val SAVE = "/save"
        const val UPDATE = "/update"
        const val DELETE = "/{id}"

        fun byId(id: Long?) = UriTemplate(GET_BY_ID).expand(id)
    }

    /**
     * Represents the module URIs.
     */
    object Modules {
        const val RESOURCE = "$PREFIX/modules"

        const val GET_BY_ID = "/{id}"
        const val SAVE = "/save"
        const val UPDATE = "/update"
        const val DELETE = "/{id}"

        fun byId(id: Long?) = UriTemplate(GET_BY_ID).expand(id)
    }

    /**
     * Represents the section URIs.
     */
    object Sections {
        const val RESOURCE = "$PREFIX/sections"

        const val GET_BY_ID = "/{id}"
        const val SAVE = "/save"
        const val UPDATE = "/update"
        const val DELETE = "/{id}"

        fun byId(id: Long?) = UriTemplate(GET_BY_ID).expand(id)
    }

    /**
     * Represents the tech URIs.
     */
    object Techs {
        const val RESOURCE = "$PREFIX/techs"

        const val GET_BY_ID = "/{id}"
        const val SAVE = "/save"
        const val UPDATE = "/update"
        const val DELETE = "/{id}"
        const val GET_BY_USER = "/user"
        const val SAVE_MULTIPLE = "/savemultiple"
        const val UPDATE_MULTIPLE = "/updatemultiple"

        fun byId(id: Long?) = UriTemplate(GET_BY_ID).expand(id)
    }

    /**
     * Represents the voc URIs.
     */
    object Vocs {
        const val RESOURCE = "$PREFIX/vocs"

        const val GET_BY_ID = "/{id}"
        const val SAVE = "/save"
        const val UPDATE = "/update"
        const val DELETE = "/{id}"
        const val GET_BY_USER = "/user"

        fun byId(id: Long?) = UriTemplate(GET_BY_ID).expand(id)
    }
}
