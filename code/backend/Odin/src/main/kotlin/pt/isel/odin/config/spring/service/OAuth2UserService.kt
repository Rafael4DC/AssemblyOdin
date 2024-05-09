package pt.isel.odin.config.spring.service

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.stereotype.Service
import pt.isel.odin.controller.dto.user.UserRequest
import pt.isel.odin.service.interfaces.UserService

@Service
class OAuth2UserService(private val userService: UserService) : OidcUserService() {

    override fun loadUser(userRequest: OidcUserRequest): OidcUser {
        val oAuth2User = super.loadUser(userRequest)

        val email = oAuth2User.getAttribute<String>("email") ?: throw IllegalArgumentException("Email not found")
        val name = oAuth2User.getAttribute<String>("name") ?: throw IllegalArgumentException("Name not found")

        userService.save(UserRequest(email, name))

        return oAuth2User
    }
}
