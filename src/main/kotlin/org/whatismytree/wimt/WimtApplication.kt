package org.whatismytree.wimt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WimtApplication

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<WimtApplication>(*args)
}
