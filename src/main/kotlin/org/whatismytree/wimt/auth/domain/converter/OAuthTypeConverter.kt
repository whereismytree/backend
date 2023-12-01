package org.whatismytree.wimt.auth.domain.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.whatismytree.wimt.auth.domain.OAuthType

@Converter(autoApply = true)
class OAuthTypeConverter : AttributeConverter<OAuthType, String> {
    override fun convertToDatabaseColumn(attribute: OAuthType): String {
        return attribute.name
    }

    override fun convertToEntityAttribute(dbData: String): OAuthType {
        return OAuthType.valueOf(dbData)
    }
}
