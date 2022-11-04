package com.ko.mogakco.mogakco.exception

class AccountNotFoundException(errorCode: ErrorCode = ErrorCode.ACCOUNT_NOT_FOUND) :
    BusinessException(errorCode)