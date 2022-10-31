package com.ko.mogakco.mogakco.exception

import java.time.LocalDateTime

abstract class BusinessException(open val errorCode: ErrorCode): RuntimeException(errorCode.description)

data class ErrorCommonResponse private constructor(val code: String, val description: String, val createAt: LocalDateTime) {
    companion object {
        fun of(errorCode: ErrorCode): ErrorCommonResponse {
            return ErrorCommonResponse(
                code = "ERR_" + errorCode.ordinal,
                description = errorCode.description,
                createAt = LocalDateTime.now()
            )
        }
    }
}