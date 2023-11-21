package org.whatismytree.wimt.support

import com.navercorp.fixturemonkey.ArbitraryBuilder
import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import jakarta.persistence.EntityManager

val fixtureMonkey = FixtureMonkey.builder().plugin(KotlinPlugin())
    .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE).build()

fun <T> createSample(clazz: Class<T>): T = fixtureMonkey.giveMeOne(clazz)

inline fun <reified T> EntityManager.makeSample(
    builder: ArbitraryBuilder<T>.() -> Unit = {}
): T =
    fixtureMonkey.giveMeBuilder(T::class.java).setNull("id").apply(builder).build().sample().apply { persist(this) }
