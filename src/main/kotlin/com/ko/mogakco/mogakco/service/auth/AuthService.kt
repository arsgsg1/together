package com.ko.mogakco.mogakco.service.auth

import com.ko.mogakco.mogakco.domain.auth.Account
import com.ko.mogakco.mogakco.domain.auth.TokenProvider
import com.ko.mogakco.mogakco.domain.auth.UserInfo
import com.ko.mogakco.mogakco.exception.AccountNotFoundException
import com.ko.mogakco.mogakco.exception.EntityAlreadyExistException
import com.ko.mogakco.mogakco.exception.EntityNotFoundException
import com.ko.mogakco.mogakco.exception.auth.SignInFailedException
import com.ko.mogakco.mogakco.repository.auth.AccountRepository
import com.ko.mogakco.mogakco.service.auth.dto.*
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class AuthService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenProvider: TokenProvider
) : UserDetailsService {

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
    fun signUp(dto: CreateAccountDto): Long {
        checkExistingEmail(dto.email)
        checkExistingNickname(dto.nickname)
        return accountRepository.save(
            Account(
                email = dto.email,
                password = passwordEncoder.encode(dto.password),
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

    @Transactional(readOnly = true)
    fun signIn(dto: LoginDto): TokenDto {
        val account =
            accountRepository.findByEmail(dto.email).orElseThrow { AccountNotFoundException() }
        if (!passwordEncoder.matches(dto.password, account.password)) {
            throw SignInFailedException()
        }
        return TokenDto(
            accessToken = tokenProvider.createAccessToken(account.id!!),
            refreshToken = tokenProvider.createRefreshToken(account.id!!)
        )
    }

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetails {
        val account = accountRepository.findById(username.toLong())
            .orElseThrow { throw AccountNotFoundException() }
        return UserInfo(
            accountId = account.id!!,
            accountEmail = account.email,
            mutableSetOf(SimpleGrantedAuthority("ROLE_USER"))
        )
    }
}