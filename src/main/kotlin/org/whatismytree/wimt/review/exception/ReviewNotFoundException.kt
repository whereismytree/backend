package org.whatismytree.wimt.review.exception

class ReviewNotFoundException(
    message: String?,
    throwable: Throwable?,
) : RuntimeException(message, throwable) {
    constructor(message: String) : this(message, null)
}
