package org.whatismytree.wimt.tree.exception

import org.whatismytree.wimt.common.exception.NotFoundException

class TreeNotFoundException(
    message: String,
    throwable: Throwable? = null,
) : NotFoundException(message, throwable)
