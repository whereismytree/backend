package org.whatismytree.wimt.common

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest,
    ): ResponseEntity<Any> {
        logger.error("MethodArgumentNotValidException occured", ex)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.message)
    }

    @ExceptionHandler(BaseException::class)
    fun handleBaseException(exception: BaseException): ResponseEntity<String> {
        logger.error("CustomException occured", exception)
        return makeErrorResponseEntity(HttpStatus.BAD_REQUEST, exception.message)
    }

    @ExceptionHandler(IllegalArgumentException::class, IllegalStateException::class)
    fun handleBadRequestException(exception: Exception): ResponseEntity<String> {
        logger.error("IllegalArgumentException or IllegalStateException occured", exception)
        return makeErrorResponseEntity(HttpStatus.BAD_REQUEST, exception.message)
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(exception: Exception): ResponseEntity<String> {
        logger.error("AuthenticationException occured", exception)
        return makeErrorResponseEntity(HttpStatus.UNAUTHORIZED, exception.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleGlobalException(exception: Exception): ResponseEntity<String> {
        logger.error("Unexpected Exception occured", exception)
        return makeErrorResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR, exception.message)
    }

    private fun makeErrorResponseEntity(httpStatus: HttpStatus, message: String?): ResponseEntity<String> =
        ResponseEntity.status(httpStatus).body(message)
}