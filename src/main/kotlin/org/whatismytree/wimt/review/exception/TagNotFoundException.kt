package org.whatismytree.wimt.review.exception

import org.whatismytree.wimt.common.exception.NotFoundException

class TagNotFoundException(
    message: String,
    throwable: Throwable?,
) : NotFoundException(message, throwable) {
    constructor(message: String) : this(message, null)
}
