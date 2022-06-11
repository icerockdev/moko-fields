/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.validations

import dev.icerock.moko.fields.core.validations.ValidationResult
import dev.icerock.moko.fields.core.validations.isEqual
import dev.icerock.moko.fields.core.validations.notNull
import dev.icerock.moko.fields.core.validations.validate
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.test.AndroidArchitectureInstantTaskExecutorRule
import dev.icerock.moko.test.TestRule
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class AnyValidationsTest {
    @get:TestRule
    val instantTaskExecutorRule = AndroidArchitectureInstantTaskExecutorRule()

    @Test
    fun `notNull any validation`() {
        var anyObj: String? = "test"
        var result = ValidationResult.of(anyObj)
            .notNull("error 0".desc())
            .validate()

        assertNull(result)

        anyObj = null
        result = ValidationResult.of(anyObj)
            .notNull("error 1".desc())
            .validate()

        assertNotNull(result)
    }

    @Test
    fun `isEqual any validation`() {
        var str: String? = "test"
        var result = ValidationResult.of(str)
            .isEqual("error 0".desc(), str)
            .validate()

        assertNull(result)

        result = ValidationResult.of(str)
            .isEqual("error 1".desc(), "test")
            .validate()

        assertNull(result)

        result = ValidationResult.of(str)
            .isEqual("error 2".desc(), null)
            .validate()

        assertNotNull(result)

        str = null
        result = ValidationResult.of(str)
            .isEqual("error 3".desc(), null)
            .validate()

        assertNull(result)
    }
}
