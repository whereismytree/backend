package org.whatismytree.wimt.tag.repository

import org.whatismytree.wimt.tag.repository.dto.ValidTagResult

interface TagQueryRepository {

    fun findAll(): List<ValidTagResult>
}
