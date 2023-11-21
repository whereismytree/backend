package org.whatismytree.wimt.review.repository

import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.test.context.TestConstructor
import org.whatismytree.wimt.review.domain.Review

@DataJpaTest
@EnableJpaAuditing
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
internal class ReviewRepositoryTest(
    private val reviewRepository: ReviewRepository,
    private val entityManager: EntityManager
) {

    @Test
    fun test() {
        val review = Review.of(
            treeId = 1L,
            userId = 1L,
            content = "content",
            tagIds = listOf(0)
        )

        reviewRepository.save(review)

        entityManager.flush()
        entityManager.clear()

        reviewRepository.findById(review.id)
    }
}
