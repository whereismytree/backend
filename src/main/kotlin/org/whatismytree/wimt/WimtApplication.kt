package org.whatismytree.wimt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WimtApplication

fun main(args: Array<String>) {
    runApplication<WimtApplication>(*args)
}
