package org.whatismytree.wimt.tree.repository

import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.CaseBuilder
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import org.whatismytree.wimt.tree.controller.dto.FindTreeListDto
import org.whatismytree.wimt.tree.entity.QTree.tree
import org.whatismytree.wimt.tree.entity.Tree

@Repository
class TreeRepositoryImpl(
    private val jpaQueryFactory: JPAQueryFactory,
): TreeRepositoryCustom {
    override fun findTreeList(
        nameParam: String?,
        addressParam: String?
    ): List<FindTreeListDto.Res> {
        val query = jpaQueryFactory
            .select(
                Projections.constructor(
                    FindTreeListDto.Res::class.java,
                    tree.id,
                    tree.name,
                    tree.lat,
                    tree.lng,
                    CaseBuilder().`when`(tree.addressType.eq("STREET"))
                        .then(tree.streetAddress).otherwise(tree.roadAddress).`as`("address"),
                    tree.imageUrl
                )
            )
            .from(tree)


        var predicate: BooleanExpression? = null

        if (nameParam != null) {
            predicate = tree.name.like("%$nameParam%")
        }

        if (addressParam != null) {
            val addressCondition = tree.streetAddress.like("%$addressParam%")
                .or(tree.roadAddress.like("%$addressParam%"))
            predicate = if (predicate != null) {
                predicate.or(addressCondition)
            } else {
                addressCondition
            }
        }

        if (predicate != null) {
            query.where(predicate)
        }

        return query.fetch()
    }

}
