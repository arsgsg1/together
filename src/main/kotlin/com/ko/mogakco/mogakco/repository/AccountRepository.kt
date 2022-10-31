package com.ko.mogakco.mogakco.repository

import com.ko.mogakco.mogakco.domain.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface AccountRepository : JpaRepository<Account, Long> {
    fun findByEmail(email: String): Optional<Account>
    fun findByNickname(nickname: String): Optional<Account>
}