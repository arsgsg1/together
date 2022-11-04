package com.ko.mogakco.mogakco.exception.auth

import com.ko.mogakco.mogakco.exception.BusinessException
import com.ko.mogakco.mogakco.exception.ErrorCode

class UnAuthorizationException(errorCode: ErrorCode = ErrorCode.UNAUTHORIZATION) :
    BusinessException(errorCode)