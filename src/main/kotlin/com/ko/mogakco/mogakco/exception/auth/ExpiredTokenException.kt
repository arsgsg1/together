package com.ko.mogakco.mogakco.exception.auth

import com.ko.mogakco.mogakco.exception.BusinessException
import com.ko.mogakco.mogakco.exception.ErrorCode

class ExpiredTokenException(errorCode: ErrorCode = ErrorCode.EXPIRED_TOKEN) :
    BusinessException(errorCode)