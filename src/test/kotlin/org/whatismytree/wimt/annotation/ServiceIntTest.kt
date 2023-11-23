package org.whatismytree.wimt.annotation

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.core.annotation.AliasFor
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.test.context.TestConstructor
import org.whatismytree.wimt.config.QuerydslConfig
import kotlin.reflect.KClass

@DataJpaTest
@Import(QuerydslConfig::class)
@EnableJpaAuditing
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ServiceIntTest(
    @get:AliasFor(annotation = Import::class, attribute = "value") val value: KClass<*> = Unit::class,
)
