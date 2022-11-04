package com.ko.mogakco.mogakco.exception.auth

import com.google.gson.JsonObject
import com.ko.mogakco.mogakco.exception.ErrorCode
import org.springframework.http.HttpStatus
import javax.servlet.http.HttpServletResponse

class AuthenticationExceptionUtils {
    companion object {
        val EXCEPTION_ATTRIBUTE_KEY = "exception"

        fun setResponse(
            response: HttpServletResponse,
            errorCode: ErrorCode?
        ) {
            val json = JsonObject()
            json.addProperty("code", errorCode?.getCode())
            json.addProperty("description", errorCode?.description)
            response.contentType = "application/json;charset=UTF-8"
            response.characterEncoding = "UTF-8"
            response.status = (errorCode?.status?.value() ?: HttpStatus.UNAUTHORIZED.value())
            response.writer.print(json)
        }
    }
}