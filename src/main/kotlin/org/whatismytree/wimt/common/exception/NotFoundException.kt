package org.whatismytree.wimt.common.exception

open class NotFoundException(
    message: String?,
    throwable: Throwable?,
) : BaseException(message, throwable) {
    constructor(message: String) : this(message, null)
}
