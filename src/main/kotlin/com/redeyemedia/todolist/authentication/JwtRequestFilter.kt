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
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Autowired



open class JwtRequestFilter(
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
            val authorizationHeader = request.getHeader("Authorization")

                // var jwt: String
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
                val token = request.getHeader(SecurityConstants.TOKEN_HEADER)

                // jwt = authorizationHeader.substring(7)

                val signingKey = SecurityConstants.JWT_SECRET.toByteArray()

                val parsedToken = Jwts.parser()
                    .setSigningKey(signingKey)
                    .parseClaimsJws(token.replace("Bearer ", ""))

                val username = parsedToken
                    .body
                    .subject

                /*val authorities = (parsedToken.body["rol"] as MutableList<*>?)!!.stream()
                    .map { authority: Any? -> SimpleGrantedAuthority(authority as String?) }
                    .collect(Collectors.toList())*/
                /*val authorities = (parsedToken.body["rol"] as List<*>).stream()
                    .map { authority: Any? -> SimpleGrantedAuthority(authority as String?) }
                    .collect(Collectors.toList())*/

                val authorities = parsedToken.body["rol"].toString()
                    .split(",").filterNot { it.isEmpty() }
                    .map(::SimpleGrantedAuthority)




                if (StringUtils.isNotEmpty(username)) {
                    println("JWT is magically being authorized &&&&&&&&&&&&&&&&&&&&&& $username")
                    //return UsernamePasswordAuthenticationToken(username, null, authorities)
                    return UsernamePasswordAuthenticationToken(username, null, authorities)
                }
            }
            return null
        }

}