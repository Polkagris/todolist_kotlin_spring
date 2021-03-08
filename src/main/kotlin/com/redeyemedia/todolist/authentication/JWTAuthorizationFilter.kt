package com.redeyemedia.todolist.authentication

import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import java.util.stream.Collectors
import org.springframework.security.core.authority.SimpleGrantedAuthority
import javax.servlet.http.HttpServletRequest
import org.springframework.security.core.context.SecurityContextHolder
import javax.servlet.ServletException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;



open class JwtAuthorizationFilter(
    authenticationManager: AuthenticationManager?) :
    BasicAuthenticationFilter(authenticationManager) {
    @Throws(IOException::class, ServletException::class)

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authentication = getAuthentication(request)
        if (authentication == null) {
            filterChain.doFilter(request, response)
            return
        }
        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(request, response)
    }

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(SecurityConstants.TOKEN_HEADER)
        if (StringUtils.isNotEmpty(token) && token.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            try {
                val signingKey = SecurityConstants.JWT_SECRET.toByteArray()
                val parsedToken = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token.replace("Bearer ", ""))
                val username = parsedToken
                    .body
                    .subject
                val authorities = (parsedToken.body["rol"] as List<*>?)!!.stream()
                    .map { authority: Any? -> SimpleGrantedAuthority(authority as String?) }
                    .collect(Collectors.toList())
                if (StringUtils.isNotEmpty(username)) {
                    return UsernamePasswordAuthenticationToken(username, null, authorities)
                }
            } catch (exception: ExpiredJwtException) {
                log.warn("Request to parse expired JWT : {} failed : {}", token, exception.message)
            } catch (exception: UnsupportedJwtException) {
                log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.message)
            } catch (exception: MalformedJwtException) {
                log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.message)
            } catch (exception: SignatureException) {
                log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.message)
            } catch (exception: IllegalArgumentException) {
                log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.message)
            }
        }
        return null
    }

    companion object {
        val log: Logger = LoggerFactory.getLogger(JwtAuthorizationFilter::class.java)
    }
}