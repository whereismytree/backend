package org.whatismytree.wimt.common.constraint

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class NullOrNotBlankValidator: ConstraintValidator<NullOrNotBlank, CharSequence> {
    override fun isValid(value: CharSequence?, context: ConstraintValidatorContext?): Boolean {
        return value == null || value.isNotBlank()
    }
}
