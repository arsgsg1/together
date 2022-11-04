package com.ko.mogakco.mogakco.config.auth

import com.ko.mogakco.mogakco.domain.auth.TokenProvider
import com.ko.mogakco.mogakco.exception.auth.ExpiredTokenException
import com.ko.mogakco.mogakco.exception.auth.UnAuthorizationException
import com.ko.mogakco.mogakco.exception.auth.AuthenticationExceptionUtils.Companion.EXCEPTION_ATTRIBUTE_KEY
import com.ko.mogakco.mogakco.service.auth.AuthService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JwtFilter(
    private val tokenProvider: TokenProvider,
    private val authService: AuthService
) : GenericFilterBean() {
    companion object {
        const val AUTHORIZATION = "Authorization"
    }

    override fun doFilter(
        request: ServletRequest,
        response: ServletResponse,
        chain: FilterChain
    ) {
        //todo: try catch 대신 코틀린스럽게 작성하는 방법 찾아보기
        try {
            val accessToken = extractTokenOrNullInHeader(request as HttpServletRequest)
            val accountId = tokenProvider.extractAccountIdIfValidate(accessToken)
            val userInfo = authService.loadUserByUsername(accountId.toString())
            SecurityContextHolder.getContext().authentication =
                PreAuthenticatedAuthenticationToken(userInfo, null, userInfo.authorities)
        } catch (ex: ExpiredTokenException) {
            request.setAttribute(EXCEPTION_ATTRIBUTE_KEY, ex.errorCode);
        } catch (ex: UnAuthorizationException) {
            request.setAttribute(EXCEPTION_ATTRIBUTE_KEY, ex.errorCode)
        }
        chain.doFilter(request, response)
    }

    private fun extractTokenOrNullInHeader(request: HttpServletRequest): String? {
        //todo: null 처리 코틀린스럽게 작성하는 방법 찾아보기
        val token = request.getHeader(AUTHORIZATION)
        if (token.isNullOrEmpty()) throw UnAuthorizationException()
        if (token.startsWith("Bearer")) {
            return token.substring(7)
        }
        return null
    }
}