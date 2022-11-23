/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.horologist.auth.oauth.devicegrant

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Text
import com.google.android.horologist.auth.ui.oauth.devicegrant.AuthDeviceGrantScreenState

@Composable
fun AuthDeviceGrantSampleScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthDeviceGrantSampleViewModel = viewModel(factory = AuthDeviceGrantSampleViewModel.Factory)
) {
    var executedOnce by rememberSaveable { mutableStateOf(false) }
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    if (state == AuthDeviceGrantScreenState.Idle) {
        SideEffect {
            if (!executedOnce) {
                executedOnce = true
                viewModel.startAuthFlow()
            }
        }
    }

    val stateText = when (state) {
        AuthDeviceGrantScreenState.Idle -> "Idle"
        AuthDeviceGrantScreenState.Loading -> "Loading"
        is AuthDeviceGrantScreenState.CheckPhone -> {
            val code = (state as AuthDeviceGrantScreenState.CheckPhone).code
            "CheckPhone: $code"
        }

        AuthDeviceGrantScreenState.Failed -> "Failed"
        AuthDeviceGrantScreenState.Success -> "Success"
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center

    ) {
        Text(
            text = stateText,
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}