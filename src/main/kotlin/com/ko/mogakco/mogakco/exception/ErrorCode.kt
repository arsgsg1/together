package com.ko.mogakco.mogakco.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(val description: String, val status: HttpStatus) {
    ENTITY_NOT_FOUND("지정한 대상을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    ENTITY_ALREADY_EXIST("이미 존재하는 이메일 혹은 닉네임입니다", HttpStatus.BAD_REQUEST),
    EXPIRED_TOKEN("만료된 토큰입니다.", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZATION("토큰이 없거나 권한이 없는 요청입니다.", HttpStatus.UNAUTHORIZED),
    ACCOUNT_NOT_FOUND("회원가입 이력이 없습니다.", HttpStatus.BAD_REQUEST),
    SIGN_IN_FAILED("로그인에 실패했습니다. 이메일 혹은 비밀번호를 확인해주세요", HttpStatus.BAD_REQUEST);

    fun getCode(): String {
        return "ERR_${this.ordinal}"
    }
}