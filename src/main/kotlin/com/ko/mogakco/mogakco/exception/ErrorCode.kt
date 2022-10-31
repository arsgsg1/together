package com.ko.mogakco.mogakco.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val description: String, val status: HttpStatus) {
    ENTITY_NOT_FOUND("지정한 대상을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    ENTITY_ALREADY_EXIST("이미 존재하는 이메일 혹은 닉네임입니다", HttpStatus.BAD_REQUEST)
}