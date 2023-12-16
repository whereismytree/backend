package org.whatismytree.wimt.common.exception

abstract class BaseException(
    message: String?,
    throwable: Throwable?,
) : RuntimeException(message, throwable)
