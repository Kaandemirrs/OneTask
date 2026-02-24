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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
            .padding(start = 24.dp, end = 24.dp, top = 24.dp, bottom = 10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
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

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = stringResource(id = R.string.onboarding_goal_title),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Chart fills remaining space
            AnimatedGoalChart(
                selectedGoalId = selectedGoalId,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Spacer(modifier = Modifier.height(12.dp))

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
                                .height(68.dp)
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
                                .height(68.dp)
                        )
                    }
                }

                if (index != rows.lastIndex) {
                    Spacer(modifier = Modifier.height(14.dp))
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            val continueEnabled = selectedGoalId != null

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.onboardingone),
                    contentDescription = null,
                    modifier = Modifier
                        .size(96.dp)
                        .clip(RoundedCornerShape(48.dp))
                )

                Spacer(modifier = Modifier.width(14.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(Color(0xFFF5F5F7))
                        .border(
                            width = 1.dp,
                            color = Color(0xFFB0B0B0),
                            shape = RoundedCornerShape(24.dp)
                        )
                        .padding(horizontal = 20.dp, vertical = 18.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.onboarding_goal_helper_text),
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OnboardingContinueButton(
                enabled = continueEnabled,
                onClick = onContinue
            )
        }
    }
}

@Composable
private fun AnimatedGoalChart(
    selectedGoalId: String?,
    modifier: Modifier = Modifier
) {
    // 3 points: start (orange), middle (yellow), end (green)
    // Steeper: orange at very bottom-left, green at upper-right
    val pointHeights = remember(selectedGoalId) {
        when (selectedGoalId) {
            "5_min" -> Triple(0.05f, 0.15f, 0.28f)
            "10_min" -> Triple(0.05f, 0.18f, 0.35f)
            "15_min" -> Triple(0.06f, 0.22f, 0.42f)
            "30_min" -> Triple(0.07f, 0.26f, 0.55f)
            else -> Triple(0.04f, 0.10f, 0.18f)
        }
    }

    val animSpec = spring<Float>(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessLow
    )

    val animY1 by animateFloatAsState(targetValue = pointHeights.first, animationSpec = animSpec, label = "y1")
    val animY2 by animateFloatAsState(targetValue = pointHeights.second, animationSpec = animSpec, label = "y2")
    val animY3 by animateFloatAsState(targetValue = pointHeights.third, animationSpec = animSpec, label = "y3")

    val orangeColor = Color(0xFFFF8C00)
    val yellowColor = Color(0xFFFFD600)
    val limeColor = Color(0xFFB2D300)
    val greenColor = Color(0xFF4CAF50)
    val gridColor = Color(0xFFE0E0E0)
    val dashColor = Color(0xFFCBCBCB)

    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val padding = 24f

        val chartLeft = padding
        val chartRight = w - padding
        val chartTop = padding
        val chartBottom = h - padding
        val chartW = chartRight - chartLeft
        val chartH = chartBottom - chartTop

        // --- Grid lines ---
        val hLines = 3
        val vLines = 3
        for (i in 0..hLines) {
            val y = chartTop + chartH * i / hLines
            drawLine(gridColor, Offset(chartLeft, y), Offset(chartRight, y), strokeWidth = 1.5f)
        }
        for (i in 0..vLines) {
            val x = chartLeft + chartW * i / vLines
            drawLine(gridColor, Offset(x, chartTop), Offset(x, chartBottom), strokeWidth = 1.5f)
        }

        // --- 3 main points ---
        val p1x = chartLeft + chartW * 0.06f
        val p1y = chartBottom - chartH * animY1
        val p2x = chartLeft + chartW * 0.46f
        val p2y = chartBottom - chartH * animY2
        val p3x = chartLeft + chartW * 0.86f
        val p3y = chartBottom - chartH * animY3

        // --- 3 dashed curves fanning out from the orange dot ---
        // All start at p1 (same origin), spread upward progressively
        val fanMultipliers = listOf(1.3f, 1.6f, 1.9f)
        val dashSegmentLen = 20f
        val dashGapLen = 14f
        val totalSegments = 80

        for (mult in fanMultipliers) {
            // Dashed curve: same start point, but middle and end are progressively higher
            val dp2y = p1y - (p1y - p2y) * mult
            val dp3y = p1y - (p1y - p3y) * mult

            var dashDrawn = 0f
            var drawing = true
            for (i in 0 until totalSegments) {
                val t1 = i.toFloat() / totalSegments
                val t2 = (i + 1).toFloat() / totalSegments

                val x1 = (1 - t1) * (1 - t1) * p1x + 2 * (1 - t1) * t1 * p2x + t1 * t1 * p3x
                val y1 = ((1 - t1) * (1 - t1) * p1y + 2 * (1 - t1) * t1 * dp2y + t1 * t1 * dp3y).coerceAtLeast(chartTop)
                val x2 = (1 - t2) * (1 - t2) * p1x + 2 * (1 - t2) * t2 * p2x + t2 * t2 * p3x
                val y2 = ((1 - t2) * (1 - t2) * p1y + 2 * (1 - t2) * t2 * dp2y + t2 * t2 * dp3y).coerceAtLeast(chartTop)

                val segLen = kotlin.math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1))

                if (drawing) {
                    drawLine(
                        color = dashColor,
                        start = Offset(x1, y1),
                        end = Offset(x2, y2),
                        strokeWidth = 4f,
                        cap = StrokeCap.Round
                    )
                }

                dashDrawn += segLen
                if (drawing && dashDrawn >= dashSegmentLen) {
                    drawing = false
                    dashDrawn = 0f
                } else if (!drawing && dashDrawn >= dashGapLen) {
                    drawing = true
                    dashDrawn = 0f
                }
            }
        }

        // --- Gradient curved line using segments ---
        val segments = 100
        val points = mutableListOf<Offset>()
        for (i in 0..segments) {
            val t = i.toFloat() / segments
            val x = (1 - t) * (1 - t) * p1x + 2 * (1 - t) * t * p2x + t * t * p3x
            val y = (1 - t) * (1 - t) * p1y + 2 * (1 - t) * t * p2y + t * t * p3y
            points.add(Offset(x, y))
        }

        // Draw the curve with gradient segments
        for (i in 0 until points.size - 1) {
            val t = i.toFloat() / (points.size - 1)
            val color = when {
                t < 0.33f -> {
                    val lt = t / 0.33f
                    lerp(orangeColor, yellowColor, lt)
                }
                t < 0.66f -> {
                    val lt = (t - 0.33f) / 0.33f
                    lerp(yellowColor, limeColor, lt)
                }
                else -> {
                    val lt = (t - 0.66f) / 0.34f
                    lerp(limeColor, greenColor, lt)
                }
            }
            drawLine(
                color = color,
                start = points[i],
                end = points[i + 1],
                strokeWidth = 7f,
                cap = StrokeCap.Round
            )
        }

        // --- Dots ---
        val dotRadius = 16f
        val dotBorderWidth = 4f

        // Orange dot
        drawCircle(color = Color.White, radius = dotRadius + dotBorderWidth, center = Offset(p1x, p1y))
        drawCircle(color = orangeColor, radius = dotRadius, center = Offset(p1x, p1y))

        // Yellow dot
        drawCircle(color = Color.White, radius = dotRadius + dotBorderWidth, center = Offset(p2x, p2y))
        drawCircle(color = yellowColor, radius = dotRadius, center = Offset(p2x, p2y))

        // Green dot
        drawCircle(color = Color.White, radius = dotRadius + dotBorderWidth, center = Offset(p3x, p3y))
        drawCircle(color = greenColor, radius = dotRadius, center = Offset(p3x, p3y))
    }
}

private fun lerp(start: Color, end: Color, fraction: Float): Color {
    return Color(
        red = start.red + (end.red - start.red) * fraction,
        green = start.green + (end.green - start.green) * fraction,
        blue = start.blue + (end.blue - start.blue) * fraction,
        alpha = 1f
    )
}


