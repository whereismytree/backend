package org.whatismytree.wimt.tag.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.whatismytree.wimt.tag.domain.Tag

interface TagRepository : JpaRepository<Tag, Long> {

    fun findAllByDeletedAtIsNullAndIdIn(ids: List<Long>): List<Tag>
}
