package com.example.chefjuna.ui.model

data class Dish(
    val name: String,
    val calories: String,
    val price: String,
    val imageRes: Int,
    val description: String = "",
    val rating: Float = 4.5f,
    val cookingTime: String = "15 min",
    val difficulty: String = "Easy",
    val category: String = "Main Course"
)