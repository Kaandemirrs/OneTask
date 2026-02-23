package com.example.taskone.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.taskone.R

@Composable
fun OnboardingProgressBar(
    progress: Float,
    modifier: Modifier = Modifier
) {
    LinearProgressIndicator(
        progress = { progress },
        modifier = modifier
            .fillMaxWidth()
            .height(10.dp)
            .clip(RoundedCornerShape(16.dp)),
        color = Color(0xFF02C46D),
        trackColor = Color(0xFFE0FFE9)
    )
}

@Composable
fun PrimaryGradientButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val baseModifier = modifier
        .fillMaxWidth()
        .height(56.dp)
        .clip(RoundedCornerShape(28.dp))

    val buttonModifier = if (enabled) {
        baseModifier
            .background(
                Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF7F5AF0),
                        Color(0xFFFFA6E7)
                    )
                )
            )
            .clickable { onClick() }
    } else {
        baseModifier
            .background(Color(0xFFF2F0FF))
    }

    Box(
        modifier = buttonModifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (enabled) Color.White else Color(0xFFBDBDBD),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun OnboardingContinueButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    PrimaryGradientButton(
        text = stringResource(id = R.string.onboarding_continue_button),
        enabled = enabled,
        onClick = onClick,
        modifier = modifier
    )
}

