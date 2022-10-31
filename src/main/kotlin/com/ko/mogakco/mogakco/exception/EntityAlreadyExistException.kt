package com.ko.mogakco.mogakco.exception

class EntityAlreadyExistException(override val errorCode: ErrorCode = ErrorCode.ENTITY_ALREADY_EXIST) :
    BusinessException(errorCode)