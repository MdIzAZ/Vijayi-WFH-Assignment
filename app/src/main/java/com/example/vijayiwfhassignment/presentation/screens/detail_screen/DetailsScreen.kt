package com.example.vijayiwfhassignment.presentation.screens.detail_screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.vijayiwfhassignment.domain.models.TitleDetails
import com.valentinilk.shimmer.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    onBackClick: () -> Unit,
    state: DetailsUiState
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        when (val currentState = state) {
            is DetailsUiState.Loading -> ShimmerLoadingDetails()
            is DetailsUiState.Success -> {
                CollapsingToolbarLayout(
                    details = currentState.details,
                    onBackClick = onBackClick
                )
            }

            is DetailsUiState.Error -> {
                Toast.makeText(LocalContext.current, currentState.message, Toast.LENGTH_LONG).show()

            }
        }
    }
}

@Composable
private fun CollapsingToolbarLayout(
    details: TitleDetails,
    onBackClick: () -> Unit
) {

    val lazyListState = rememberLazyListState()
    val expandedHeight = LocalConfiguration.current.screenHeightDp.dp * 0.7f
    val collapsedHeight = 80.dp

    val expandedHeightPx = with(LocalDensity.current) { expandedHeight.toPx() }
    val collapsedHeightPx = with(LocalDensity.current) { collapsedHeight.toPx() }
    val scrollThreshold = expandedHeightPx - collapsedHeightPx


    val scrollOffset by remember {
        derivedStateOf {
            if (lazyListState.firstVisibleItemIndex == 0) {
                lazyListState.firstVisibleItemScrollOffset.toFloat()
            } else {
                scrollThreshold
            }
        }
    }


    val collapseFraction = (scrollOffset / scrollThreshold).coerceIn(0f, 1f)


    Box(modifier = Modifier.fillMaxSize()) {


        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(top = expandedHeight)
        ) {
            item {
                DetailsContent(details = details)
            }
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(
                    lerp(
                        expandedHeight,
                        collapsedHeight,
                        collapseFraction
                    )
                )
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF00C6FF),
                            Color(0xFF0072FF)
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(1000f, 1000f)
                    )
                )
        ) {

            // Background Image
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(details.poster)
                    .crossfade(true)
                    .build(),
                contentDescription = "Toolbar Background",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = 1f + (collapseFraction * 0.5f)
                        scaleY = 1f + (collapseFraction * 0.5f)
                        alpha = 1f - collapseFraction
                    }
            )

            // Dark gradient for text readability when expanded
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Black.copy(alpha = 0.4f * collapseFraction),
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.6f * (1 - collapseFraction))
                            )
                        )
                    )
            )

            // Title that animates its position and size
            Text(
                text = details.title,
                color = Color.White,
                fontSize = lerp(32.sp, 20.sp, collapseFraction),
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .padding(
                        start = lerp(16.dp, 70.dp, collapseFraction),
                        end = 16.dp,
                        top = 40.dp
                    )
                    .align(Alignment.BottomStart)
                    .graphicsLayer {
                        val yTranslation = lerp(0f, -45f, collapseFraction)
                        translationY = yTranslation
                    },
                style = MaterialTheme.typography.bodyMedium.copy(
                    lineHeight = 48.sp
                )
            )

            // Back Button
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 28.dp)
                    .clip(CircleShape)
                    .background(
                        Color.DarkGray.copy(
                            alpha = lerp(0.7f, 0f, collapseFraction)
                        )
                    )
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun DetailsContent(details: TitleDetails) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Release Date: ${details.releaseDate ?: "N/A"}",
            style = MaterialTheme.typography.bodyLarge
        )
        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
        Text(
            text = "Overview",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = details.plotOverview ?: "No overview available.",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(600.dp))
    }
}

@Composable
private fun ShimmerLoadingDetails() {
    val expandedHeight = LocalConfiguration.current.screenHeightDp.dp * 0.7f
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(expandedHeight)
                .background(Color.Gray)
                .shimmer()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(30.dp)
                    .background(Color.Gray)
                    .shimmer()
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .height(20.dp)
                    .background(Color.Gray)
                    .shimmer()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color.Gray)
                    .shimmer()
            )
        }
    }
}