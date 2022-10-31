package com.ko.mogakco.mogakco.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalApiExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(BusinessException::class)
    fun handleRuntimeException(ex: BusinessException): ResponseEntity<ErrorCommonResponse> {
        val errorCode = ex.errorCode
        return ResponseEntity.status(errorCode.status).body(ErrorCommonResponse.of(errorCode))
    }
}