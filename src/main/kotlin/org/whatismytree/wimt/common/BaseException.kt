package org.whatismytree.wimt.common

abstract class BaseException(
    message: String?,
    throwable: Throwable?,
) : RuntimeException(message, throwable)
