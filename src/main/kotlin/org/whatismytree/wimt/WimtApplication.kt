package org.whatismytree.wimt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class WimtApplication

fun main(args: Array<String>) {
    @Suppress("SpreadOperator")
    runApplication<WimtApplication>(*args)
}
