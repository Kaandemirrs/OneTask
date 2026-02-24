package com.example.taskone.ui.screens.home

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskone.R
import com.example.taskone.ui.components.PrimaryGradientButton
import com.example.taskone.util.LocaleHelper

@Composable
fun HomeScreen(
    isOnboardingCompleted: Boolean,
    onStartOnboarding: () -> Unit
) {
    val context = LocalContext.current
    var currentLang by remember { mutableStateOf(LocaleHelper.getLanguage(context)) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Language toggle row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                val enBg = if (currentLang == "en") Color(0xFF4D5BFF) else Color(0xFFF5F5F7)
                val enTextColor = if (currentLang == "en") Color.White else Color(0xFF333333)
                val esBg = if (currentLang == "es") Color(0xFF4D5BFF) else Color(0xFFF5F5F7)
                val esTextColor = if (currentLang == "es") Color.White else Color(0xFF333333)

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(enBg)
                        .clickable {
                            if (currentLang != "en") {
                                LocaleHelper.setLocale(context, "en")
                                currentLang = "en"
                                (context as? Activity)?.recreate()
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "EN 🇬🇧",
                        color = enTextColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(esBg)
                        .clickable {
                            if (currentLang != "es") {
                                LocaleHelper.setLocale(context, "es")
                                currentLang = "es"
                                (context as? Activity)?.recreate()
                            }
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "ES 🇪🇸",
                        color = esTextColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.onboardingone),
                contentDescription = null,
                modifier = Modifier
                    .size(140.dp)
                    .clip(RoundedCornerShape(70.dp))
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(id = R.string.home_welcome_title),
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(id = R.string.home_welcome_subtitle),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = Color(0xFF8E8E93),
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.weight(1f))

            PrimaryGradientButton(
                text = stringResource(id = R.string.home_start_onboarding),
                enabled = true,
                onClick = onStartOnboarding,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
