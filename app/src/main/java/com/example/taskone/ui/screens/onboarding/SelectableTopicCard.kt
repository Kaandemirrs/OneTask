package com.example.taskone.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.taskone.R

@Composable
fun SelectableTopicCard(
    topic: OnboardingViewModel.Topic,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val placeholderResId = remember {
        context.resources.getIdentifier("img_placeholder_topic", "drawable", context.packageName)
    }

    val restaurantResId = remember {
        context.resources.getIdentifier("on4", "drawable", context.packageName)
    }

    val airportResId = remember {
        context.resources.getIdentifier("hostes", "drawable", context.packageName)
    }

    val businessResId = remember {
        context.resources.getIdentifier("business", "drawable", context.packageName)
    }

    val jobInterviewResId = remember {
        context.resources.getIdentifier("jobinter", "drawable", context.packageName)
    }

    val doctorResId = remember {
        context.resources.getIdentifier("doctor", "drawable", context.packageName)
    }

    val relationshipResId = remember {
        context.resources.getIdentifier("relotion", "drawable", context.packageName)
    }

    val travelResId = remember {
        context.resources.getIdentifier("travel", "drawable", context.packageName)
    }

    val schoolResId = remember {
        context.resources.getIdentifier("school", "drawable", context.packageName)
    }

    val borderColor = if (isSelected) {
        Color(0xFF4D5BFF)
    } else {
        Color(0xFFE0E0E0)
    }

    val backgroundBrush = if (isSelected) {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFFE4E7FF),
                Color(0xFFFFFFFF)
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFFF9FAFF),
                Color(0xFFFFFFFF)
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(170.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundBrush)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = RoundedCornerShape(24.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = topic.labelRes),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )

            val imageResId = when {
                topic.id == "restaurant" && restaurantResId != 0 -> restaurantResId
                topic.id == "airport" && airportResId != 0 -> airportResId
                topic.id == "business" && businessResId != 0 -> businessResId
                topic.id == "job_interview" && jobInterviewResId != 0 -> jobInterviewResId
                topic.id == "doctor" && doctorResId != 0 -> doctorResId
                topic.id == "relationship" && relationshipResId != 0 -> relationshipResId
                topic.id == "travel" && travelResId != 0 -> travelResId
                topic.id == "school" && schoolResId != 0 -> schoolResId
                else -> placeholderResId
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(90.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                if (imageResId != 0) {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = null,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .height(90.dp)
                            .clip(RoundedCornerShape(20.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .height(90.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFFDADDE5))
                    )
                }

                Spacer(modifier = Modifier.size(8.dp))

                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .clip(CircleShape)
                        .border(
                            width = 2.dp,
                            color = if (isSelected) Color(0xFF4D5BFF) else Color(0xFFCED4E0),
                            shape = CircleShape
                        )
                        .background(
                            color = if (isSelected) Color(0xFF4D5BFF) else Color.Transparent,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isSelected) {
                        Text(
                            text = "✓",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}
