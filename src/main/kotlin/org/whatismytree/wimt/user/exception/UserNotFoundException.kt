package org.whatismytree.wimt.user.exception

import org.whatismytree.wimt.common.exception.NotFoundException

class UserNotFoundException(
    message: String?,
    throwable: Throwable?,
) : NotFoundException(message, throwable) {
    constructor(message: String) : this(message, null)
}
