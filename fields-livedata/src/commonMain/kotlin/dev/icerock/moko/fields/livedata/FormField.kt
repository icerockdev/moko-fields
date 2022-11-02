/*
 * Copyright 2022 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

package dev.icerock.moko.fields.livedata

import dev.icerock.moko.mvvm.livedata.LiveData

expect class FormField<D, E>(
    initialValue: D,
    validation: (LiveData<D>) -> LiveData<E?>
) : BaseLiveDataFormField<D, E>
