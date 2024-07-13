package pt.isel.odin.config.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.security.web.savedrequest.RequestCache
import org.springframework.security.web.savedrequest.SavedRequest
import org.springframework.stereotype.Component

@Component
class OAuth2LoginSuccessHandler : SavedRequestAwareAuthenticationSuccessHandler() {

    private val requestCache: RequestCache = HttpSessionRequestCache()

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val savedRequest: SavedRequest? = requestCache.getRequest(request, response)
        if (savedRequest != null) {
            val res = savedRequest.redirectUrl.replaceFirst("/api/users/session?continue", "")
            redirectStrategy.sendRedirect(request, response, res)
        }
        else{
            super.onAuthenticationSuccess(request, response, authentication)
        }

    }
}
