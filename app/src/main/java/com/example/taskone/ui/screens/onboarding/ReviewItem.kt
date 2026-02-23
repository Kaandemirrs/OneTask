package com.example.taskone.ui.screens.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReviewItem(
    userName: String,
    comment: String,
    rating: Int,
    highlighted: Boolean,
    avatarResName: String?,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(24.dp)

    val cardColors = if (highlighted) {
        CardDefaults.cardColors(containerColor = Color.Transparent)
    } else {
        CardDefaults.cardColors(containerColor = Color.White)
    }

    val gradientBrush = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF7F5AF0),
            Color(0xFF00D1FF)
        )
    )

    val context = LocalContext.current

    val avatarResId = remember(avatarResName) {
        avatarResName?.let { name ->
            context.resources.getIdentifier(name, "drawable", context.packageName)
        } ?: 0
    }

    val verifiedResId = remember {
        context.resources.getIdentifier("ver", "drawable", context.packageName)
    }

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = cardColors
    ) {
        val contentModifier = if (highlighted) {
            Modifier
                .background(gradientBrush)
                .padding(horizontal = 16.dp, vertical = 14.dp)
        } else {
            Modifier
                .background(Color.Transparent)
                .padding(horizontal = 16.dp, vertical = 14.dp)
        }

        Box(
            modifier = contentModifier
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (avatarResId != 0) {
                        Image(
                            painter = painterResource(id = avatarResId),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(
                                    color = Color(0xFFDADDE5),
                                    shape = CircleShape
                                )
                        )
                    }

                    Spacer(modifier = Modifier.size(12.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = userName,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = if (highlighted) Color.White else Color(0xFF111111)
                        )

                        if (verifiedResId != 0) {
                            Spacer(modifier = Modifier.size(6.dp))
                            Image(
                                painter = painterResource(id = verifiedResId),
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    RatingBar(
                        rating = rating,
                        activeColor = Color(0xFFFFD54F),
                        inactiveColor = if (highlighted) Color(0x66FFD54F) else Color(0x33FFD54F)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = comment,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (highlighted) Color.White else Color(0xFF4A4A4A),
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
private fun RatingBar(
    rating: Int,
    activeColor: Color,
    inactiveColor: Color,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        repeat(5) { index ->
            val filled = index < rating
            Text(
                text = if (filled) "★" else "☆",
                color = if (filled) activeColor else inactiveColor,
                fontSize = 14.sp
            )
        }
    }
}
