package com.ko.mogakco.mogakco.repository

import com.ko.mogakco.mogakco.domain.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, Long>