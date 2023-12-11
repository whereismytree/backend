package org.whatismytree.wimt.user.exception

import org.whatismytree.wimt.common.BaseException

class UserNotFoundException(
    message: String?,
    throwable: Throwable?,
) : BaseException(message, throwable) {
    constructor(message: String) : this(message, null)
}
