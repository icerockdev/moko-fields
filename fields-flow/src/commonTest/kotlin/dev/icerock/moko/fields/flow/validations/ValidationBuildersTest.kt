/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields.flow.validations

import dev.icerock.moko.fields.core.validations.ValidationResult
import dev.icerock.moko.fields.core.validations.matchRegex
import dev.icerock.moko.fields.core.validations.notBlank
import dev.icerock.moko.fields.flow.FormField
import dev.icerock.moko.fields.flow.flowBlock
import dev.icerock.moko.resources.desc.StringDesc
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.test.AndroidArchitectureInstantTaskExecutorRule
import dev.icerock.moko.test.TestRule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class ValidationBuildersTest {
    @get:TestRule
    val instantTaskExecutorRule = AndroidArchitectureInstantTaskExecutorRule()

    val coroutineScope = CoroutineScope(Dispatchers.Unconfined)

    val errorStr0 = "Error0".desc()
    val errorStr1 = "Error1".desc()

    @Test
    fun `fieldValidation builder`() {
        val validation = fieldValidation<String> {
            notBlank(errorStr0)
            matchRegex(errorStr1, Regex(pattern = "^[0-9]{3}\$"))
        }
        testValidationWithForm(validation)
    }

    @Test
    fun `ValidationResult of DSL builder`() {
        val validation: (Flow<String>) -> Flow<StringDesc?> = flowBlock { value ->
            ValidationResult.of(value) {
                notBlank(errorStr0)
                matchRegex(errorStr1, Regex(pattern = "^[0-9]{3}\$"))
            }
        }

        testValidationWithForm(validation)
    }

    private fun testValidationWithForm(validation: (Flow<String>) -> Flow<StringDesc?>) {
        val field = FormField<String, StringDesc>(
            scope = coroutineScope, initialValue = "", validation = validation
        )

        field.data.value = ""
        field.validate()

        assertNotNull(field.error.value)
        assertEquals(errorStr0, field.error.value)

        field.data.value = "asdzxc"
        field.validate()

        assertNotNull(field.error.value)
        assertEquals(errorStr1, field.error.value)

        field.data.value = "123"
        field.validate()

        assertNull(field.error.value)

        field.data.value = "1234"
        field.validate()

        assertNotNull(field.error.value)
        assertEquals(errorStr1, field.error.value)
    }

}
