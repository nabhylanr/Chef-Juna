package com.example.chefjuna.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chefjuna.ui.model.Dish

@Composable
fun DishRow(dishes: List<Dish>, onDishClick: (Dish) -> Unit) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(dishes) { dish ->
            Column(
                modifier = Modifier
                    .width(160.dp)
                    .background(Color.White, shape = RoundedCornerShape(16.dp))
                    .padding(8.dp)
                    .clickable(onClick = { onDishClick(dish) })
            ) {
                Image(
                    painter = painterResource(id = dish.imageRes),
                    contentDescription = dish.name,
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(dish.name, fontWeight = FontWeight.Bold)
                Text(dish.calories, color = Color.Gray, fontSize = 12.sp)
                Text(dish.price, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}