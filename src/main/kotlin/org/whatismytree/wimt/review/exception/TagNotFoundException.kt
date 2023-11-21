package org.whatismytree.wimt.review.exception

class TagNotFoundException(
    message: String?,
    throwable: Throwable?,
) : RuntimeException(message, throwable) {
    constructor(message: String) : this(message, null)
}
