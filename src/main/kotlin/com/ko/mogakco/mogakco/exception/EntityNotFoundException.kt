package com.ko.mogakco.mogakco.exception

class EntityNotFoundException(override val errorCode: ErrorCode = ErrorCode.ENTITY_NOT_FOUND) :
    BusinessException(errorCode)