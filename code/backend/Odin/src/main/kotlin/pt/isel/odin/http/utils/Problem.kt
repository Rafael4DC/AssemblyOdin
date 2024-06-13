package pt.isel.odin.http.utils

import org.springframework.http.ResponseEntity
import pt.isel.odin.service.department.error.DeleteDepartmentError
import pt.isel.odin.service.department.error.GetDepartmentError
import pt.isel.odin.service.department.error.SaveUpdateDepartmentError
import pt.isel.odin.service.fieldstudy.error.DeleteFieldStudyError
import pt.isel.odin.service.fieldstudy.error.GetFieldStudyError
import pt.isel.odin.service.fieldstudy.error.SaveUpdateFieldStudyError
import pt.isel.odin.service.module.error.DeleteModuleError
import pt.isel.odin.service.module.error.GetModuleError
import pt.isel.odin.service.module.error.SaveUpdateModuleError
import pt.isel.odin.service.section.error.DeleteSectionError
import pt.isel.odin.service.section.error.GetSectionError
import pt.isel.odin.service.section.error.SaveUpdateSectionError
import pt.isel.odin.service.tech.error.DeleteTechError
import pt.isel.odin.service.tech.error.GetTechError
import pt.isel.odin.service.tech.error.SaveUpdateTechError
import pt.isel.odin.service.user.error.DeleteUserError
import pt.isel.odin.service.user.error.GetUserError
import pt.isel.odin.service.user.error.SaveUpdateUserError
import pt.isel.odin.service.voc.error.DeleteVocError
import pt.isel.odin.service.voc.error.GetVocError
import pt.isel.odin.service.voc.error.SaveUpdateVocError
import java.net.URI

class Problem(
    val status: Int,
    typeUri: URI,
    title: String
) {
    val type = typeUri.toASCIIString()

    val title = title

    companion object {
        private const val GITHUB_URL =
            "https://github.com/Rafael4DC/AssemblyOdin/blob/feature/%2378_endpoints_and_frontend_improve/docs/problems/"
        private const val MEDIA_TYPE = "application/problem+json"
        fun response(problem: Problem): ResponseEntity<Any> = ResponseEntity
            .status(problem.status)
            .header("Content-Type", MEDIA_TYPE)
            .body<Any>(problem)

        fun responseForError(any: Any) =
            response(
                errorToProblem.getOrDefault(
                    any,
                    unexpectedError
                )
            )

        private val notFoundUser = Problem(
            404,
            URI(
                GITHUB_URL + "user-not-found"
            ),
            "User Not Found"
        )

        private val emailIncorrectUser = Problem(
            400,
            URI(
                GITHUB_URL + "email-incorrect"
            ),
            "Email Incorrect"
        )

        private val emailAlreadyExistsUser = Problem(
            409,
            URI(
                GITHUB_URL + "user-already-exists"
            ),
            "A User Already Exists With That Email"
        )

        private val nameIncorrect = Problem(
            400,
            URI(
                GITHUB_URL + "name-incorrect"
            ),
            "Name Incorrect"
        )

        private val roleIncorrectUser = Problem(
            400, URI(
                GITHUB_URL + "role-incorrect"
            ),
            "Role Incorrect"
        )

        private val notFoundDepartment = Problem(
            404,
            URI(
                GITHUB_URL + "department-not-found"
            ),
            "Department Not Found"
        )

        private val alreadyExistsDepartment = Problem(
            409,
            URI(
                GITHUB_URL + "department-already-exists"
            ),
            "Department Already Exists"
        )

        private val notFoundFieldStudy = Problem(
            404,
            URI(
                GITHUB_URL + "field-study-not-found"
            ),
            "Field Study Not Found"
        )

        private val alreadyExistsFieldStudy = Problem(
            409,
            URI(
                GITHUB_URL + "field-study-already-exists"
            ),
            "Field Study Already Exists"
        )

        private val notFoundModule = Problem(
            404,
            URI(
                GITHUB_URL + "module-not-found"
            ),
            "Module Not Found"
        )

        private val alreadyExistsModule = Problem(
            409,
            URI(
                GITHUB_URL + "module-already-exists"
            ),
            "Module Already Exists"
        )

        private val notFoundSection = Problem(
            404,
            URI(
                GITHUB_URL + "section-not-found"
            ),
            "Section Not Found"
        )

        private val alreadyExistsSection = Problem(
            409,
            URI(
                GITHUB_URL + "section-already-exists"
            ),
            "Section Already Exists"
        )

        private val notFoundTech = Problem(
            404,
            URI(
                GITHUB_URL + "tech-not-found"
            ),
            "Tech Not Found"
        )

        private val notFoundVoc = Problem(
            404,
            URI(
                GITHUB_URL + "voc-not-found"
            ),
            "Voc Not Found"
        )

        private val unexpectedError = Problem(
            500,
            URI(
                GITHUB_URL + "unexpected-error"
            ),
            "Unexpected Error"
        )

        private val errorToProblem: HashMap<Any, Problem> = hashMapOf(
            /**
             * User
             */
            GetUserError.NotFoundUser to notFoundUser,
            DeleteUserError.NotFoundUser to notFoundUser,
            SaveUpdateUserError.NotFoundUser to notFoundUser,
            SaveUpdateUserError.IncorrectEmailUser to emailIncorrectUser,
            SaveUpdateUserError.EmailAlreadyExistsUser to emailAlreadyExistsUser,
            SaveUpdateUserError.IncorrectNameUser to nameIncorrect,
            SaveUpdateUserError.RoleIncorrectUser to roleIncorrectUser,

            /**
             * Department
             */
            GetDepartmentError.NotFoundDepartment to notFoundDepartment,
            DeleteDepartmentError.NotFoundDepartment to notFoundDepartment,
            SaveUpdateDepartmentError.NotFoundDepartment to notFoundDepartment,
            SaveUpdateDepartmentError.AlreadyExistsDepartment to alreadyExistsDepartment,
            SaveUpdateDepartmentError.IncorrectNameDepartment to nameIncorrect,

            /**
             * Field Study
             */
            GetFieldStudyError.NotFoundFieldStudy to notFoundFieldStudy,
            DeleteFieldStudyError.NotFoundFieldStudy to notFoundFieldStudy,
            SaveUpdateFieldStudyError.NotFoundFieldStudy to notFoundFieldStudy,
            SaveUpdateFieldStudyError.AlreadyExistsFieldStudy to alreadyExistsFieldStudy,
            SaveUpdateFieldStudyError.NotFoundDepartment to notFoundDepartment,
            SaveUpdateFieldStudyError.IncorrectNameFieldStudy to nameIncorrect,

            /**
             * Module
             */
            GetModuleError.NotFoundModule to notFoundModule,
            DeleteModuleError.NotFoundModule to notFoundModule,
            SaveUpdateModuleError.NotFoundModule to notFoundModule,
            SaveUpdateModuleError.AlreadyExistsModule to alreadyExistsModule,
            SaveUpdateModuleError.NotFoundFieldStudy to notFoundFieldStudy,

            /**
             * Section
             */
            GetSectionError.NotFoundSection to notFoundSection,
            DeleteSectionError.NotFoundSection to notFoundSection,
            SaveUpdateSectionError.NotFoundSection to notFoundSection,
            SaveUpdateSectionError.AlreadyExistsSection to alreadyExistsSection,
            SaveUpdateSectionError.NotFoundFieldStudy to notFoundFieldStudy,

            /**
             * Tech
             */
            GetTechError.NotFoundTech to notFoundTech,
            DeleteTechError.NotFoundTech to notFoundTech,
            SaveUpdateTechError.NotFoundTech to notFoundTech,
            SaveUpdateTechError.NotFoundUser to notFoundUser,
            SaveUpdateTechError.NotFoundSection to notFoundSection,
            SaveUpdateTechError.NotFoundFieldStudy to notFoundFieldStudy,

            /**
             * Voc
             */
            GetVocError.NotFoundVoc to notFoundVoc,
            GetVocError.NotFoundUser to notFoundUser,
            DeleteVocError.NotFoundVoc to notFoundVoc,
            SaveUpdateVocError.NotFoundVoc to notFoundVoc,
            SaveUpdateVocError.NotFoundUser to notFoundUser,
            SaveUpdateVocError.NotFoundSection to notFoundSection,
            SaveUpdateVocError.NotFoundFieldStudy to notFoundFieldStudy,
        )
    }
}
