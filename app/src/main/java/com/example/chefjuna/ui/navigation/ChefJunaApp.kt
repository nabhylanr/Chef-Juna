package com.example.chefjuna.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.example.chefjuna.R
import com.example.chefjuna.ui.navigation.NavigationRoutes.HOME
import com.example.chefjuna.ui.navigation.NavigationRoutes.EXPLORE
import com.example.chefjuna.ui.navigation.NavigationRoutes.FAVORITE
import com.example.chefjuna.ui.navigation.NavigationRoutes.PROFILE
import com.example.chefjuna.ui.navigation.NavigationRoutes.RECIPE_DETAIL
import com.example.chefjuna.ui.components.BottomNavigationBar
import com.example.chefjuna.ui.pages.*
import com.example.chefjuna.ui.model.Dish

@Composable
fun ChefJunaApp() {
    var selectedIndex by remember { mutableStateOf(0) }
    var currentScreen by remember { mutableStateOf(HOME) }
    var selectedDish by remember { mutableStateOf<Dish?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    val favoriteDishes = remember { mutableStateListOf<Dish>() }
    var showFavoriteToast by remember { mutableStateOf(false) }
    var favoriteToastMessage by remember { mutableStateOf("") }

    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentScreen != RECIPE_DETAIL) {
                BottomNavigationBar(
                    selectedIndex = selectedIndex,
                    onItemSelected = {
                        selectedIndex = it
                        when (it) {
                            0 -> {
                                currentScreen = HOME
                                navController.navigate(HOME) {
                                    popUpTo(HOME) { inclusive = true }
                                }
                            }
                            1 -> {
                                currentScreen = EXPLORE
                                navController.navigate(EXPLORE) {
                                    popUpTo(HOME)
                                }
                            }
                            2 -> {
                                currentScreen = FAVORITE
                                navController.navigate(FAVORITE) {
                                    popUpTo(HOME)
                                }
                            }
                            3 -> {
                                currentScreen = PROFILE
                                navController.navigate(PROFILE) {
                                    popUpTo(HOME)
                                }
                            }
                        }
                    }
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = remember { SnackbarHostState() }) {
                if (showFavoriteToast) {
                    Snackbar(
                        modifier = Modifier.padding(16.dp),
                        action = {
                            TextButton(onClick = { showFavoriteToast = false }) {
                                Text("Dismiss")
                            }
                        }
                    ) {
                        Text(favoriteToastMessage)
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HOME,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(HOME) {
                currentScreen = HOME
                HomeScreen(
                    onDishClick = { dish ->
                        selectedDish = dish
                        currentScreen = RECIPE_DETAIL
                        navController.navigate(RECIPE_DETAIL)
                    },
                    searchQuery = searchQuery,
                    onSearchChanged = { query ->
                        searchQuery = query
                        isSearching = query.isNotEmpty()
                    },
                    isSearching = isSearching
                )
            }

            composable(RECIPE_DETAIL) {
                RecipeDetailScreen(
                    dish = selectedDish ?: Dish("Avocado Toast", "245 kcal", "$45", R.drawable.avocado_toast),
                    onBackClick = {
                        currentScreen = HOME
                        navController.popBackStack()
                    },
                    favoriteDishes = favoriteDishes,
                    onToggleFavorite = { dish ->
                        if (favoriteDishes.contains(dish)) {
                            favoriteDishes.remove(dish)
                            favoriteToastMessage = "Removed from favorites"
                        } else {
                            favoriteDishes.add(dish)
                            favoriteToastMessage = "Added to favorites"
                        }
                        showFavoriteToast = true
                    }
                )
            }

            composable(EXPLORE) {
                currentScreen = EXPLORE
                ExploreScreen()
            }

            composable(FAVORITE) {
                currentScreen = FAVORITE
                FavoriteScreen(
                    favoriteDishes = favoriteDishes,
                    onDishClick = { dish ->
                        selectedDish = dish
                        currentScreen = RECIPE_DETAIL
                        navController.navigate(RECIPE_DETAIL)
                    }
                )
            }

            composable(PROFILE) {
                currentScreen = PROFILE
                ProfileScreen()
            }
        }
    }
}
