package org.whatismytree.wimt.tree.repository

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.CaseBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import org.whatismytree.wimt.favorite.domain.QFavorite.favorite
import org.whatismytree.wimt.review.domain.QReview.review
import org.whatismytree.wimt.tree.controller.dto.FindPostedTreeListDto
import org.whatismytree.wimt.tree.controller.dto.FindSavedTreeListDto
import org.whatismytree.wimt.tree.controller.dto.FindTreeListDto
import org.whatismytree.wimt.tree.entity.QTree.tree

@Repository
class TreeRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
) : TreeRepositoryCustom {
    override fun findTreeList(query: String): List<FindTreeListDto.Res> {
        return jpaQueryFactory
            .select(
                Projections.constructor(
                    FindTreeListDto.Res::class.java,
                    tree.id,
                    tree.name,
                    tree.lat,
                    tree.lng,
                    CaseBuilder().`when`(tree.addressType.eq("STREET"))
                        .then(tree.streetAddress).otherwise(tree.roadAddress).`as`("address"),
                    tree.imageUrl,
                ),
            )
            .from(tree)
            .where(
                tree.name.containsIgnoreCase(query)
                    .or(tree.streetAddress.containsIgnoreCase(query).or(tree.roadAddress.containsIgnoreCase(query))),
            )
            .fetch()
    }

    override fun findPostedTreeList(
        userId: Long,
    ): MutableList<FindPostedTreeListDto.Res> {
        return jpaQueryFactory
            .select(
                Projections.constructor(
                    FindPostedTreeListDto.Res::class.java,
                    tree.id,
                    tree.name,
                    tree.lat,
                    tree.lng,
                    CaseBuilder().`when`(tree.addressType.eq("STREET"))
                        .then(tree.streetAddress).otherwise(tree.roadAddress).`as`("address"),
                    review.count().`as`("reviewsCount"),
                ),
            )
            .from(tree)
            .leftJoin(review).on(
                review.treeId.eq(tree.id)
                    .and(review.deletedAt.isNull),
            )
            .where(
                tree.userId.eq(userId)
                    .and(tree.deletedAt.isNull),
            )
            .groupBy(tree.id)
            .fetch()
    }

    // favorites 테이블 inner join하여 리스트 return
    override fun findSavedTreeList(
        userId: Long,
    ): List<FindSavedTreeListDto.Res> {
        return jpaQueryFactory
            .select(
                Projections.constructor(
                    FindSavedTreeListDto.Res::class.java,
                    tree.id,
                    tree.name,
                    tree.lat,
                    tree.lng,
                    CaseBuilder().`when`(tree.addressType.eq("STREET"))
                        .then(tree.streetAddress).otherwise(tree.roadAddress).`as`("address"),
                    review.count().`as`("reviewsCount"),
                ),
            )
            .from(tree)
            .join(favorite).on(tree.id.eq(favorite.treeId))
            .leftJoin(review).on(tree.id.eq(review.treeId))
            .where(
                tree.deletedAt.isNull()
                    .and(review.deletedAt.isNull()),
            )
            .groupBy(tree.id)
            .fetch()
    }
}
