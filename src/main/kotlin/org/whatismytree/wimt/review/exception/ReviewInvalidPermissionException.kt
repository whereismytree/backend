package org.whatismytree.wimt.review.exception

class ReviewInvalidPermissionException(
    message: String?,
    throwable: Throwable?,
) : RuntimeException(message, throwable) {
    constructor(message: String) : this(message, null)
}
