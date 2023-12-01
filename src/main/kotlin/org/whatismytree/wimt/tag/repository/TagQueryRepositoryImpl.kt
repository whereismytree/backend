package org.whatismytree.wimt.tag.repository

import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import org.whatismytree.wimt.tag.domain.QTag.tag
import org.whatismytree.wimt.tag.repository.dto.ValidTagResult

@Repository
class TagQueryRepositoryImpl(
    private val query: JPAQueryFactory,
) : TagQueryRepository {
    override fun findAll(): List<ValidTagResult> {
        return query.select(
            Projections.constructor(
                ValidTagResult::class.java,
                tag.id,
                tag.content,
            ),
        ).from(tag).where(
            tag.deletedAt.isNull,
        )
            .orderBy(tag.id.desc())
            .fetch()
    }
}
