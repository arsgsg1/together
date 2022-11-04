package com.ko.mogakco.mogakco.exception.auth

import com.ko.mogakco.mogakco.exception.BusinessException
import com.ko.mogakco.mogakco.exception.ErrorCode

class SignInFailedException(errorCode: ErrorCode = ErrorCode.SIGN_IN_FAILED) :
    BusinessException(errorCode)