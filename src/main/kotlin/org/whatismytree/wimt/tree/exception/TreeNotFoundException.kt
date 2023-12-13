package org.whatismytree.wimt.tree.exception

import org.whatismytree.wimt.common.BaseException

class TreeNotFoundException(
    message: String,
    throwable: Throwable? = null
): BaseException(message, throwable)
