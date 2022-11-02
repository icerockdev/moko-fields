/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

expect class FormField<D, E>(
    scope: CoroutineScope,
    initialValue: D,
    validation: (Flow<D>) -> Flow<E?>
) : BaseFlowFormField<D, E>
