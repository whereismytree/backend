package org.whatismytree.wimt.annotation

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.whatismytree.wimt.config.QuerydslConfig

@DataJpaTest
@EnableJpaAuditing
@Import(QuerydslConfig::class)
@TestEnvironment
annotation class RepositoryTest
