package com.redeyemedia.todolist.authentication

class SecurityConstants private constructor() {
    companion object {
        const val AUTH_LOGIN_URL = "/user/login"

        // Signing key for HS512 algorithm
        // You can use the page http://www.allkeysgenerator.com/ to generate all kinds of keys
        // n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y$B&E(H+MbQeThWmZq4t7ws!z%C*F-J@NcRf
        const val JWT_SECRET = "n2r5u8x/A%D*G-KaPdSgVkYp3s6v9y\$B&E(H+MbQeThWmZq4t7ws!z%C*F-J@NcRf"

        // JWT token defaults
        const val TOKEN_HEADER = "Authorization"
        const val TOKEN_PREFIX = "Bearer "
        const val TOKEN_TYPE = "JWT"
        const val TOKEN_ISSUER = "secure-api"
        const val TOKEN_AUDIENCE = "secure-app"
    }

    init {
        throw IllegalStateException("Cannot create instance of static util class")
    }
}