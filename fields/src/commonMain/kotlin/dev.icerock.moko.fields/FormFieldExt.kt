/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields

fun List<FormField<*, *>>.validate(): Boolean {
    forEach { it.validate() }
    return all { it.isValid.value }
}
