package org.whatismytree.wimt.annotation

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.core.annotation.AliasFor
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.whatismytree.wimt.config.QuerydslConfig
import kotlin.reflect.KClass

@DataJpaTest
@Import(QuerydslConfig::class)
@EnableJpaAuditing
@TestEnvironment
annotation class ServiceIntTest(
    @get:AliasFor(annotation = Import::class, attribute = "value") val value: KClass<*> = Unit::class,
)
