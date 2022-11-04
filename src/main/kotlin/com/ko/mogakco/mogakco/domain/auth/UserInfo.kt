package com.ko.mogakco.mogakco.domain.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.lang.UnsupportedOperationException

class UserInfo(
    val accountId: Long,
    val accountEmail: String,
    private val accountAuthorities: MutableCollection<out GrantedAuthority>
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return accountAuthorities
    }

    override fun getPassword(): String {
        throw UnsupportedOperationException()
    }

    override fun getUsername(): String {
        return accountEmail
    }

    override fun isAccountNonExpired(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun isAccountNonLocked(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun isCredentialsNonExpired(): Boolean {
        throw UnsupportedOperationException()
    }

    override fun isEnabled(): Boolean {
        throw UnsupportedOperationException()
    }
}
