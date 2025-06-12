package com.example.chefjuna.ui.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chefjuna.Dish
import com.example.chefjuna.Ingredient
import com.example.chefjuna.R

@Composable
fun RecipeDetailScreen(
    dish: Dish,
    onBackClick: () -> Unit,
    favoriteDishes: SnapshotStateList<Dish>,
    onToggleFavorite: (Dish) -> Unit
) {
    var quantity by remember { mutableStateOf(1) }
    val isFavorite = favoriteDishes.contains(dish)

    val ingredients = listOf(
        Ingredient("Tomato", R.drawable.tomato),
        Ingredient("Mushroom", R.drawable.mushroom),
        Ingredient("Lettuce", R.drawable.lettuce),
        Ingredient("Cucumber", R.drawable.cucumber),
        Ingredient("Egg", R.drawable.egg)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header with curved green background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .background(
                        color = Color(0xFF508130),
                        shape = RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp)
                    )
            ) {
                // Top bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }

                    Row {
                        // Favorite button
                        IconButton(onClick = { onToggleFavorite(dish) }) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                                tint = Color.White
                            )
                        }

                        IconButton(onClick = { /* Search action */ }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.White
                            )
                        }
                        IconButton(onClick = { /* Grid view action */ }) {
                            Icon(
                                imageVector = Icons.Outlined.GridView,
                                contentDescription = "Grid view",
                                tint = Color.White
                            )
                        }
                    }
                }

                // Recipe title
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(bottom = 80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = dish.name,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${dish.calories} - 300 g",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }

            // Recipe image overlapping the green background
            Box(modifier = Modifier
                .fillMaxWidth()
                .offset(y = (-70).dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = dish.imageRes),
                    contentDescription = dish.name,
                    modifier = Modifier
                        .size(180.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                )

                // Decorative elements
                Image(
                    painter = painterResource(id = R.drawable.carrot),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .offset(x = (-80).dp, y = (-40).dp)
                )

                Image(
                    painter = painterResource(id = R.drawable.leaf),
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                        .offset(x = (80).dp, y = (40).dp)
                )
            }

            // Price and quantity
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .offset(y = (-40).dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(24.dp),
                    color = Color(0xFFF5F5F5)
                ) {
                    Text(
                        text = dish.price,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color(0xFFF5F5F5), RoundedCornerShape(24.dp))
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    IconButton(
                        onClick = { if (quantity > 1) quantity-- },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Remove,
                            contentDescription = "Decrease",
                            tint = Color.Gray
                        )
                    }

                    Text(
                        text = quantity.toString(),
                        modifier = Modifier.padding(horizontal = 8.dp),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )

                    IconButton(
                        onClick = { quantity++ },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Increase",
                            tint = Color.Gray
                        )
                    }
                }
            }

            // Ingredients section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .offset(y = (-20).dp)
            ) {
                Text(
                    text = "Ingredients",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(ingredients) { ingredient ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = Color(0xFFF5F5F5),
                                modifier = Modifier.size(64.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = ingredient.imageRes),
                                    contentDescription = ingredient.name,
                                    modifier = Modifier
                                        .padding(12.dp)
                                        .fillMaxSize()
                                )
                            }
                        }
                    }
                }
            }

            // Description section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp)
            ) {
                Text(
                    text = "Description",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "Fresh avocado on toasted sourdough bread, topped with cherry tomatoes, microgreens, and a sprinkle of sea salt. A healthy and delicious breakfast option that's quick to prepare and packed with nutrients.",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    lineHeight = 24.sp
                )
            }
        }

        // Floating action button
        FloatingActionButton(
            onClick = { /* Add to cart action */ },
            containerColor = Color(0xFF508130),
            contentColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add to cart"
            )
        }
    }
}
