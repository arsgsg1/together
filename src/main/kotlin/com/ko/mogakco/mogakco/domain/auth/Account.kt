package com.ko.mogakco.mogakco.domain.auth

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Column

@Entity
@Table(name = "account")
class Account(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0L,
    @Column(name = "email", nullable = false, unique = true)
    val email: String,
    nickname: String,
    password: String
) {
    @Column(name = "nickname", nullable = false, unique = true)
    var nickname: String = nickname
        protected set
    var password: String = password
        protected set

    fun updateNickname(newPassword: String, newNickname: String) {
        this.nickname = newNickname
    }

    fun updatePassword(newPassword: String) {
        this.password = newPassword
    }
}