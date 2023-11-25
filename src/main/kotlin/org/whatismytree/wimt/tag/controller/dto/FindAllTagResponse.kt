package org.whatismytree.wimt.tag.controller.dto

import org.whatismytree.wimt.tag.repository.dto.ValidTagResult

data class FindAllTagResponse(
    val tags: List<Tag>,
) {

    data class Tag(
        val id: Long,
        val content: String,
    )

    companion object {
        fun of(tags: List<ValidTagResult>): FindAllTagResponse {
            return FindAllTagResponse(
                tags = tags.map {
                    Tag(
                        id = it.id,
                        content = it.content,
                    )
                }
            )
        }
    }
}
