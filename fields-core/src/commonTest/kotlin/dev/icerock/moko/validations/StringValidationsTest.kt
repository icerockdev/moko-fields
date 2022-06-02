/*
 * Copyright 2021 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.validations

import dev.icerock.moko.fields.core.validations.ValidationResult
import dev.icerock.moko.fields.core.validations.containedIn
import dev.icerock.moko.fields.core.validations.matchRegex
import dev.icerock.moko.fields.core.validations.maxLength
import dev.icerock.moko.fields.core.validations.minLength
import dev.icerock.moko.fields.core.validations.notBlank
import dev.icerock.moko.fields.core.validations.notEmpty
import dev.icerock.moko.fields.core.validations.validate
import dev.icerock.moko.resources.desc.desc
import dev.icerock.moko.test.AndroidArchitectureInstantTaskExecutorRule
import dev.icerock.moko.test.TestRule
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class StringValidationsTest {
    @get:TestRule
    val instantTaskExecutorRule = AndroidArchitectureInstantTaskExecutorRule()

    @Test
    fun `notEmpty string validation`() {
        var strValue = "string"
        var result = ValidationResult.of(strValue)
            .notEmpty("error 0".desc())
            .validate()

        assertNull(result)

        strValue = "   "
        result = ValidationResult.of(strValue)
            .notEmpty("error 2".desc())
            .validate()

        assertNull(result)

        strValue = ""
        result = ValidationResult.of(strValue)
            .notEmpty("error 1".desc())
            .validate()

        assertNotNull(result)
    }

    @Test
    fun `notBlank string validation`() {
        var strValue = "string"
        var result = ValidationResult.of(strValue)
            .notBlank("error 0".desc())
            .validate()

        assertNull(result)

        strValue = "   "
        result = ValidationResult.of(strValue)
            .notBlank("error 2".desc())
            .validate()

        assertNotNull(result)

        strValue = ""
        result = ValidationResult.of(strValue)
            .notBlank("error 1".desc())
            .validate()

        assertNotNull(result)
    }

    @Test
    fun `minLength string validation`() {
        var strValue = "1234"
        var result = ValidationResult.of(strValue)
            .minLength("error 0".desc(), minLength = 3)
            .validate()

        assertNull(result)

        strValue = "123"
        result = ValidationResult.of(strValue)
            .minLength("error 1".desc(), minLength = 3)
            .validate()

        assertNull(result)

        strValue = "12"
        result = ValidationResult.of(strValue)
            .minLength("error 2".desc(), minLength = 3)
            .validate()

        assertNotNull(result)
    }

    @Test
    fun `maxLength string validation`() {
        var strValue = "12"
        var result = ValidationResult.of(strValue)
            .maxLength("error 0".desc(), maxLength = 3)
            .validate()

        assertNull(result)

        strValue = "123"
        result = ValidationResult.of(strValue)
            .maxLength("error 1".desc(), maxLength = 3)
            .validate()

        assertNull(result)

        strValue = "1234"
        result = ValidationResult.of(strValue)
            .maxLength("error 2".desc(), maxLength = 3)
            .validate()

        assertNotNull(result)
    }

    @Test
    fun `matchRegex string validation`() {
        val regex = Regex(pattern = "^[0-9]{3}\$")

        var strValue = "123"
        var result = ValidationResult.of(strValue)
            .matchRegex("error 0".desc(), regex)
            .validate()

        assertNull(result)

        strValue = "12"
        result = ValidationResult.of(strValue)
            .matchRegex("error 1".desc(), regex)
            .validate()

        assertNotNull(result)

        strValue = "1234"
        result = ValidationResult.of(strValue)
            .matchRegex("error 2".desc(), regex)
            .validate()

        assertNotNull(result)

        strValue = "1AB"
        result = ValidationResult.of(strValue)
            .matchRegex("error 3".desc(), regex)
            .validate()

        assertNotNull(result)
    }

    @Test
    fun `containedIn string validation`() {
        val list = listOf("1", "2", "3")

        var strValue = "1"
        var result = ValidationResult.of(strValue)
            .containedIn("error 0".desc(), list)
            .validate()

        assertNull(result)

        strValue = "4"
        result = ValidationResult.of(strValue)
            .containedIn("error 1".desc(), list)
            .validate()

        assertNotNull(result)
    }
}
