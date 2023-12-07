package org.whatismytree.wimt.auth.exception

import org.springframework.security.core.AuthenticationException

class LoginRequiredException(
    message: String?,
    throwable: Throwable?,
) : AuthenticationException(message, throwable) {
    constructor(message: String) : this(message, null)
}
