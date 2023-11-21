package org.whatismytree.wimt.support

import com.navercorp.fixturemonkey.ArbitraryBuilder
import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import jakarta.persistence.EntityManager

val fixtureMonkey = FixtureMonkey.builder().plugin(KotlinPlugin())
    .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE).build()

inline fun <reified T> createSample(builder: ArbitraryBuilder<T>.() -> Unit = {}): T =
    fixtureMonkey.giveMeBuilder(T::class.java).apply(builder).build().sample()

inline fun <reified T> EntityManager.makeSample(
    builder: ArbitraryBuilder<T>.() -> Unit = {},
): T =
    fixtureMonkey.giveMeBuilder(T::class.java).setNull("id").apply(builder).build().sample()
        .apply { persist(this) }
