package com.ko.mogakco.mogakco.service

import com.ko.mogakco.mogakco.domain.Account
import com.ko.mogakco.mogakco.repository.AccountRepository
import com.ko.mogakco.mogakco.service.dto.*
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(private val accountRepository: AccountRepository) {
    @Transactional
    fun createAccount(dto: CreateAccountDto): Long {
        return accountRepository.save(Account(email = dto.email, password = dto.password, nickname = dto.nickname)).id!!
    }

    @Transactional(readOnly = true)
    fun getAccountsList(pageRequest: PageRequest): GetAccountListDto {
        return fromEntities(accountRepository.findAll(pageRequest))
    }

    @Transactional(readOnly = true)
    fun getAccountDetailById(id: Long): GetAccountDetailDto {
        return fromEntity(accountRepository.findById(id).orElseThrow())
    }

    @Transactional
    fun updateAccount(id: Long, dto: UpdateAccountDto): Long {
        var existingAccount = accountRepository.findById(id).orElseThrow()
        existingAccount.updateNickname(newPassword = dto.password, newNickname = dto.nickname)
        return existingAccount.id!!
    }
}