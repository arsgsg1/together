package com.ko.mogakco.mogakco.domain.auth

import com.ko.mogakco.mogakco.exception.auth.ExpiredTokenException
import com.ko.mogakco.mogakco.exception.auth.UnAuthorizationException
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*

@Component
class TokenProvider(
    @Value("\${jwt.secret}") val secret: String,
    @Value("\${jwt.access.validity-period}") val accessTokenValidityInMilliseconds: Long,
    @Value("\${jwt.refresh.validity-period}") val refreshTokenValidityInMilliseconds: Long,
) {
    companion object {
        const val AUTHORITIES_KEY = "authority"
        const val ACCOUNT_ID_KEY = "accountId"
    }

    val keyBytes: ByteArray = Decoders.BASE64.decode(secret)
    val key: Key = Keys.hmacShaKeyFor(keyBytes)

    fun createAccessToken(accountId: Long): String {
        val now = Date().time
        val validity = Date(now + accessTokenValidityInMilliseconds)
        return Jwts.builder()
            .claim(ACCOUNT_ID_KEY, accountId.toString())
            .claim(AUTHORITIES_KEY, "ROLE_USER")
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact()
    }

    fun createRefreshToken(accountId: Long): String {
        val now = Date().time
        val validity = Date(now + refreshTokenValidityInMilliseconds)
        return Jwts.builder()
            .claim(ACCOUNT_ID_KEY, accountId)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact()
    }

    fun extractAccountIdIfValidate(token: String?): Long {
        try {
            checkNotNull(token).run { throw UnAuthorizationException() }
            val claims = Jwts.parserBuilder().setSigningKey(key)
                .build().parseClaimsJws(token).body
            return claims[ACCOUNT_ID_KEY].toString().toLong()
        } catch (ex: ExpiredJwtException) {
            throw ExpiredTokenException()
        } catch (ex: Exception) {
            throw IllegalArgumentException(ex.message)
        }
    }
}