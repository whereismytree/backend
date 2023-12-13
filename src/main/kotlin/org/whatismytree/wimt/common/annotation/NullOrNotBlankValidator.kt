package org.whatismytree.wimt.common.annotation

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class NullOrNotBlankValidator: ConstraintValidator<NullOrNotBlank, CharSequence> {
    override fun isValid(value: CharSequence?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) {
            return true
        }

        return value.isNotBlank()
    }
}
