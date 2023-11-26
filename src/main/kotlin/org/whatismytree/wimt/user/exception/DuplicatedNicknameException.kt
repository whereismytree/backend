package org.whatismytree.wimt.user.exception

class DuplicatedNicknameException(
    message: String?,
    throwable: Throwable?
) : RuntimeException(message, throwable) {
    constructor(message: String) : this(message, null)
}
