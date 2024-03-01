package org.whatismytree.wimt.tree.entity

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

enum class SpaceType {
    INDOOR,
    OUTDOOR,
    UNKNOWN,
}

@Converter(autoApply = true)
class SpaceTypeConverter : AttributeConverter<SpaceType, String> {
    override fun convertToDatabaseColumn(attribute: SpaceType?): String? {
        return attribute?.name
    }

    @Suppress("SwallowedException")
    override fun convertToEntityAttribute(dbData: String?): SpaceType {
        return dbData?.let { SpaceType.valueOf(it) } ?: SpaceType.UNKNOWN
    }
}
