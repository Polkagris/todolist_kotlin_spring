
import com.redeyemedia.todolist.authentication.SecurityConstants

import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.util.stream.Collectors
import org.springframework.security.core.GrantedAuthority
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import java.util.*
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.util.Date;
// import jdk.internal.org.jline.keymap.KeyMap.key
import org.springframework.security.core.userdetails.User;

import io.jsonwebtoken.io.Encoders
import javax.crypto.SecretKey




open class JWTAuthenticationFilter(authenticationManager: AuthenticationManager) :
    UsernamePasswordAuthenticationFilter() {

    // TRY TO AUTHENTICATE
    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse?
    ): Authentication {

/*        val username = request.getParameter("username")
        val password = request.getParameter("password")*/
        val username = request.getHeader("username")
        val password = request.getHeader("password")
        val authenticationToken = UsernamePasswordAuthenticationToken(username, password)

        return authenticationManager.authenticate(authenticationToken)
    }

    // SUCCESSFUL ATTEMPT
    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        filterChain: FilterChain?,
        authentication: Authentication
    ) {
        val user: User = authentication.principal as User
        val roles: MutableList<String>? = user.authorities
            .stream()
            .map { obj: GrantedAuthority -> obj.authority }
            .collect(Collectors.toList())


        val signingKey: ByteArray = SecurityConstants.JWT_SECRET.toByteArray()

        val base64Key = Encoders.BASE64.encode(signingKey)
        //creates a spec-compliant secure-random key:
        val superKey = Keys.secretKeyFor(SignatureAlgorithm.HS512) //or HS384 or HS512


        val token: String = Jwts.builder()
            .signWith(SignatureAlgorithm.HS512, signingKey)
            .setHeaderParam("typ", SecurityConstants.TOKEN_TYPE)
            .setIssuer(SecurityConstants.TOKEN_ISSUER)
            .setAudience(SecurityConstants.TOKEN_AUDIENCE)
            .setSubject(user.username)
            .setExpiration(Date(System.currentTimeMillis() + 864000000))
            .claim("rol", roles)
            .compact()
        response.addHeader(SecurityConstants.TOKEN_HEADER, SecurityConstants.TOKEN_PREFIX.toString() + token)
    }

    init {
        setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }
}