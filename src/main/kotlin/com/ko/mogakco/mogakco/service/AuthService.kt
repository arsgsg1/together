package com.ko.mogakco.mogakco.service

import com.ko.mogakco.mogakco.domain.Account
import com.ko.mogakco.mogakco.exception.EntityAlreadyExistException
import com.ko.mogakco.mogakco.exception.EntityNotFoundException
import com.ko.mogakco.mogakco.repository.AccountRepository
import com.ko.mogakco.mogakco.service.dto.*
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(private val accountRepository: AccountRepository) {

    @Transactional(readOnly = true)
    fun checkExistingEmail(email: String) {
        accountRepository.findByEmail(email).ifPresent { throw EntityAlreadyExistException() }
    }

    @Transactional(readOnly = true)
    fun checkExistingNickname(checkExistingNickname: String) {
        accountRepository.findByNickname(checkExistingNickname)
            .ifPresent { throw EntityAlreadyExistException() }
    }

    @Transactional
    fun createAccount(dto: CreateAccountDto): Long {
        checkExistingEmail(dto.email)
        checkExistingNickname(dto.nickname)
        return accountRepository.save(
            Account(
                email = dto.email,
                password = dto.password,
                nickname = dto.nickname
            )
        ).id!!
    }

    @Transactional(readOnly = true)
    fun getAccountsList(pageRequest: PageRequest): GetAccountListDto {
        return fromEntities(accountRepository.findAll(pageRequest))
    }

    @Transactional(readOnly = true)
    fun getAccountDetailById(id: Long): GetAccountDetailDto {
        return fromEntity(
            accountRepository.findById(id).orElseThrow { throw EntityNotFoundException() })
    }

    @Transactional
    fun updateAccount(id: Long, dto: UpdateAccountDto): Long {
        var existingAccount = accountRepository.findById(id).orElseThrow()
        existingAccount.updateNickname(newPassword = dto.password, newNickname = dto.nickname)
        return existingAccount.id!!
    }
}