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
import com.example.chefjuna.ui.navigation.NavigationRoutes.SPLASH
import com.example.chefjuna.ui.navigation.NavigationRoutes.LOGIN
import com.example.chefjuna.ui.navigation.NavigationRoutes.REGISTER
import com.example.chefjuna.ui.components.BottomNavigationBar
import com.example.chefjuna.ui.pages.*
import com.example.chefjuna.ui.model.Dish

@Composable
fun ChefJunaApp() {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination?.route

    var selectedIndex by remember { mutableStateOf(0) }
    var selectedDish by remember { mutableStateOf<Dish?>(null) }
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    val favoriteDishes = remember { mutableStateListOf<Dish>() }
    var showFavoriteToast by remember { mutableStateOf(false) }
    var favoriteToastMessage by remember { mutableStateOf("") }

    val showBottomBar = currentDestination != SPLASH && currentDestination != RECIPE_DETAIL && currentDestination != LOGIN && currentDestination != REGISTER

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (showBottomBar) {
                BottomNavigationBar(
                    selectedIndex = selectedIndex,
                    onItemSelected = {
                        selectedIndex = it
                        when (it) {
                            0 -> navController.navigate(HOME) {
                                popUpTo(HOME) { inclusive = true }
                            }
                            1 -> navController.navigate(EXPLORE) {
                                popUpTo(HOME)
                            }
                            2 -> navController.navigate(FAVORITE) {
                                popUpTo(HOME)
                            }
                            3 -> navController.navigate(PROFILE) {
                                popUpTo(HOME)
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
            startDestination = SPLASH,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(SPLASH) {
                SplashScreen(navController)
            }

            composable(LOGIN) {
                LoginScreen(navController)
            }

            composable(REGISTER) {
                RegisterScreen(navController)
            }

            composable(HOME) {
                HomeScreen(
                    onDishClick = { dish ->
                        selectedDish = dish
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
                ExploreScreen()
            }

            composable(FAVORITE) {
                FavoriteScreen(
                    favoriteDishes = favoriteDishes,
                    onDishClick = { dish ->
                        selectedDish = dish
                        navController.navigate(RECIPE_DETAIL)
                    }
                )
            }

            composable(PROFILE) {
                ProfileScreen(navController = navController)
            }
        }
    }
}
