package org.whatismytree.wimt.common

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataAccessException
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class DatabaseConnectionScheduler {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Scheduled(fixedRate = 6 * 60 * 60 * 1000)
    fun executeDatabaseQuery() {
        try {
            val query = "select * from users"
            val results = jdbcTemplate.queryForList(query)

            println("Query executed successfully. Results: $results")
        } catch (e: DataAccessException) {
            println("Error executing query: ${e.message}")
        }
    }
}
