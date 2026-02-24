package com.example.taskone.ui.screens.onboarding

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskone.R
import com.example.taskone.ui.components.OnboardingContinueButton
import com.example.taskone.ui.components.OnboardingProgressBar

@Composable
fun OnboardingGoalScreen(
    viewModel: OnboardingViewModel,
    onBack: () -> Unit,
    onContinue: () -> Unit
) {
    val selectedGoalId by viewModel.selectedGoalId.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "←",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable { onBack() }
                    )

                    OnboardingProgressBar(
                        progress = 0.5f,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = stringResource(id = R.string.onboarding_goal_title),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(24.dp))

                AnimatedGoalChart(
                    selectedGoalId = selectedGoalId,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                val goals = viewModel.goalDurations
                val rows = goals.chunked(2)

                rows.forEachIndexed { index, rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        rowItems.forEach { goal ->
                            val selected = goal.id == selectedGoalId
                            val backgroundColor = if (selected) {
                                Color(0xFFE4E7FF)
                            } else {
                                Color(0xFFF5F5F7)
                            }
                            val borderColor = if (selected) {
                                Color(0xFF4D5BFF)
                            } else {
                                Color(0xFFE0E0E0)
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(56.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(backgroundColor)
                                    .border(
                                        width = 2.dp,
                                        color = borderColor,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .clickable { viewModel.onGoalSelected(goal.id) },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = stringResource(id = goal.labelRes),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        if (rowItems.size == 1) {
                            Spacer(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(56.dp)
                            )
                        }
                    }

                    if (index != rows.lastIndex) {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }

            val continueEnabled = selectedGoalId != null

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.onboardingone),
                        contentDescription = null,
                        modifier = Modifier
                            .size(72.dp)
                            .clip(RoundedCornerShape(36.dp))
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color(0xFFF5F5F7))
                            .border(
                                width = 1.dp,
                                color = Color(0xFFB0B0B0),
                                shape = RoundedCornerShape(24.dp)
                            )
                            .padding(horizontal = 20.dp, vertical = 14.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.onboarding_goal_helper_text),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OnboardingContinueButton(
                    enabled = continueEnabled,
                    onClick = onContinue
                )
            }
        }
    }
}

@Composable
private fun AnimatedGoalChart(
    selectedGoalId: String?,
    modifier: Modifier = Modifier
) {
    val dayLabels = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

    val barHeights = remember(selectedGoalId) {
        when (selectedGoalId) {
            "5_min" -> listOf(0.25f, 0.35f, 0.20f, 0.40f, 0.30f, 0.15f, 0.20f)
            "10_min" -> listOf(0.40f, 0.55f, 0.45f, 0.60f, 0.50f, 0.35f, 0.40f)
            "15_min" -> listOf(0.55f, 0.70f, 0.60f, 0.80f, 0.65f, 0.50f, 0.60f)
            "30_min" -> listOf(0.75f, 0.90f, 0.80f, 1.00f, 0.85f, 0.70f, 0.80f)
            else -> listOf(0.15f, 0.15f, 0.15f, 0.15f, 0.15f, 0.15f, 0.15f)
        }
    }

    val animatedHeights = barHeights.map { target ->
        animateFloatAsState(
            targetValue = target,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ),
            label = "barHeight"
        ).value
    }

    val textMeasurer = rememberTextMeasurer()
    val labelStyle = TextStyle(
        fontSize = 11.sp,
        color = Color(0xFF8E8E93),
        fontWeight = FontWeight.Medium
    )

    val barGradientColors = listOf(Color(0xFF7F5AF0), Color(0xFFA78BFA))
    val inactiveColor = Color(0xFFE8E5F0)

    Canvas(modifier = modifier) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val labelAreaHeight = 28f
        val chartHeight = canvasHeight - labelAreaHeight
        val barCount = 7
        val totalSpacing = canvasWidth * 0.3f
        val barWidth = (canvasWidth - totalSpacing) / barCount
        val spacing = totalSpacing / (barCount + 1)
        val cornerRadius = barWidth / 3f

        dayLabels.forEachIndexed { index, label ->
            val barX = spacing + index * (barWidth + spacing)
            val animHeight = animatedHeights[index]
            val barHeight = chartHeight * animHeight
            val barY = chartHeight - barHeight

            // Inactive background bar
            drawRoundRect(
                color = inactiveColor,
                topLeft = Offset(barX, 0f),
                size = Size(barWidth, chartHeight),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )

            // Active animated bar
            val barBrush = Brush.verticalGradient(
                colors = barGradientColors,
                startY = barY,
                endY = chartHeight
            )
            drawRoundRect(
                brush = barBrush,
                topLeft = Offset(barX, barY),
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )

            // Day label
            val textLayout = textMeasurer.measure(label, labelStyle)
            val textX = barX + (barWidth - textLayout.size.width) / 2f
            val textY = chartHeight + (labelAreaHeight - textLayout.size.height) / 2f
            drawText(textLayout, topLeft = Offset(textX, textY))
        }
    }
}
