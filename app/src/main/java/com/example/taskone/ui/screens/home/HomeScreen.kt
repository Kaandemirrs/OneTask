package com.example.taskone.ui.screens.home

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.LocaleListCompat
import com.example.taskone.R

@Composable
fun HomeScreen(
    onStartOnboarding: () -> Unit
) {
    val configuration = LocalConfiguration.current
    val currentLanguage = configuration.locales[0]?.language ?: "en"

    var isEnglish by remember(currentLanguage) {
        mutableStateOf(currentLanguage != "es")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(id = R.string.home_title))

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    isEnglish = !isEnglish
                    val tag = if (isEnglish) "en" else "es"
                    val appLocale = LocaleListCompat.forLanguageTags(tag)
                    AppCompatDelegate.setApplicationLocales(appLocale)
                }
            ) {
                Text(
                    text = if (isEnglish) {
                        stringResource(id = R.string.home_language_toggle_en)
                    } else {
                        stringResource(id = R.string.home_language_toggle_es)
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onStartOnboarding
            ) {
                Text(text = stringResource(id = R.string.home_start_onboarding))
            }
        }
    }
}
