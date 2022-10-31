package com.ko.mogakco.mogakco.service.dto

import com.ko.mogakco.mogakco.domain.Account
import org.springframework.data.domain.Slice
import java.util.stream.Collectors

data class CreateAccountDto(val email: String, val password: String, val nickname: String)
data class UpdateAccountDto(val password: String, val nickname: String)
data class GetAccountDto(val id: Long?, val email: String, val nickname: String)
data class GetAccountListDto(val contents: List<GetAccountDto?>, val hasNext: Boolean)
data class GetAccountDetailDto(val id: Long?, val email: String, val nickname: String)

fun fromEntities(slice: Slice<Account>): GetAccountListDto {
    return GetAccountListDto(
        slice.stream()
            .map { account -> GetAccountDto(id = account.id, email = account.email, nickname = account.nickname) }
            .collect(Collectors.toList()), slice.hasNext()
    )
}

fun fromEntity(account: Account): GetAccountDetailDto {
    return GetAccountDetailDto(id = account.id, email = account.email, nickname = account.nickname)
}