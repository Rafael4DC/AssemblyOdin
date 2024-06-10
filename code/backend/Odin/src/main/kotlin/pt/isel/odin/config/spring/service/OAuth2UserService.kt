package pt.isel.odin.config.spring.service

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service
import pt.isel.odin.http.controllers.user.models.SaveUserInputModel
import pt.isel.odin.http.utils.isStudent
import pt.isel.odin.service.user.UserService
import pt.isel.odin.utils.Success

@Service
class OAuth2UserService(
    private val userService: UserService,
) : OidcUserService() {

    override fun loadUser(userRequest: OidcUserRequest): OidcUser {
        val oAuth2User = super.loadUser(userRequest)

        val email = oAuth2User.getAttribute<String>("email") ?: throw IllegalArgumentException("Email not found")
        val name = oAuth2User.getAttribute<String>("name") ?: throw IllegalArgumentException("Name not found")

        if (userService.getByEmail(email) is Success) return oAuth2User

        if (isStudent(email)) {
            userService.save(SaveUserInputModel(email = email, username = name, role = 1))
        } else {
            userService.save(SaveUserInputModel(email = email, username = name, role = 2))
        }

        return oAuth2User
    }
}
