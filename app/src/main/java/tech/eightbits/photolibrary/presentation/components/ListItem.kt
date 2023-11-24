package tech.eightbits.photolibrary.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun ListItem(
    minItemWidth: Int,
    url: String
) {
    val height = minItemWidth * 12 / 9

    Card(
        modifier = Modifier
            .size(width = minItemWidth.dp, height = height.dp)
            .padding(5.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.LightGray.copy(alpha = 0.3f)
        )
    ) {

        Box(
            modifier = Modifier.padding(5.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(width = minItemWidth.dp, height = height.dp)
                    .clip(RoundedCornerShape(5.dp)),
                model = url,
                contentDescription = "image",
                contentScale = ContentScale.Crop
            )
        }
    }

}