/*
 * Copyright 2020 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.samples.apps.sunflower.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.samples.apps.sunflower.data.UnsplashPhoto
import com.google.samples.apps.sunflower.data.UnsplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class EntryViewModel @Inject constructor(
    private val repository: UnsplashRepository
) : ViewModel() {

    private val _data: MutableLiveData<Int> = MutableLiveData(0)
    val data: LiveData<Int> = _data

    private val _dataFlow: MutableSharedFlow<Int> = MutableSharedFlow(0,1,BufferOverflow.DROP_OLDEST)
    val dataFlow: SharedFlow<Int> = _dataFlow

    fun plus() {
        _data.value = _data.value!!+1
        _dataFlow.tryEmit(_data.value ?: 0)
    }

    suspend fun collect(collector: FlowCollector<Int>): Nothing = dataFlow.collect(collector)
}
