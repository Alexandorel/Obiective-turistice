package ro.calculmobil.obiective

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import ro.calculmobil.obiective.data.Category
import ro.calculmobil.obiective.navigation.About
import ro.calculmobil.obiective.navigation.CategoriesGraph
import ro.calculmobil.obiective.navigation.Favorites
import ro.calculmobil.obiective.navigation.Login
import ro.calculmobil.obiective.navigation.PlaceDetail
import ro.calculmobil.obiective.navigation.PlaceList
import ro.calculmobil.obiective.ui.AboutScreen
import ro.calculmobil.obiective.ui.FavoritesScreen
import ro.calculmobil.obiective.ui.LoginScreen
import ro.calculmobil.obiective.ui.PlaceDetailScreen
import ro.calculmobil.obiective.ui.PlaceListScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { App() } }
    }
}

private data class Tab(val route: Any, val label: String, val icon: ImageVector)

private val tabs = listOf(
    Tab(CategoriesGraph, "Categorii", Icons.AutoMirrored.Filled.List),
    Tab(Favorites, "Favorite", Icons.Filled.Favorite),
    Tab(About, "Despre", Icons.Filled.Info),
)

@Composable
fun App() {
    val navController = rememberNavController()
    // Favoritele sunt partajate intre Detaliu si Favorite, deci stau deasupra NavHost.
    var favorites by rememberSaveable { mutableStateOf(setOf<Int>()) }

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    val showBottomBar = currentDestination != null && !currentDestination.hasRoute<Login>()

    Scaffold(
        // Insets-urile le trateaza ecranele (TopAppBar) si NavigationBar.
        contentWindowInsets = WindowInsets(0),
        bottomBar = { if (showBottomBar) BottomBar(navController) },
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Login,
            modifier = Modifier.padding(padding).consumeWindowInsets(padding),
        ) {
            composable<Login> {
                LoginScreen(onLogin = {
                    navController.navigate(CategoriesGraph) {
                        // Login dispare din stiva: Back din lista inchide aplicatia.
                        popUpTo<Login> { inclusive = true }
                    }
                })
            }

            navigation<CategoriesGraph>(startDestination = PlaceList) {
                composable<PlaceList> {
                    // Salvat in intrarea din stiva: supravietuieste detaliului, tab-urilor si rotirii.
                    var filter by rememberSaveable { mutableStateOf<Category?>(null) }
                    PlaceListScreen(
                        filter = filter,
                        onFilterChange = { filter = it },
                        onNavigateToDetail = { id -> navController.navigate(PlaceDetail(id)) },
                    )
                }
                composable<PlaceDetail> { entry ->
                    val id = entry.toRoute<PlaceDetail>().id
                    PlaceDetailScreen(
                        id = id,
                        isFavorite = id in favorites,
                        onToggleFavorite = { favorites = if (id in favorites) favorites - id else favorites + id },
                        onBack = { navController.popBackStack() },
                    )
                }
            }

            composable<Favorites> {
                FavoritesScreen(favoriteIds = favorites, onRemove = { favorites = favorites - it })
            }
            composable<About> { AboutScreen() }
        }
    }
}

@Composable
private fun BottomBar(navController: NavHostController) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination
    NavigationBar {
        tabs.forEach { tab ->
            NavigationBarItem(
                // hierarchy: in PlaceDetail ramane marcat tab-ul Categorii.
                selected = currentDestination?.hierarchy?.any { it.hasRoute(tab.route::class) } == true,
                onClick = {
                    navController.navigate(tab.route) {
                        // PlaceList e mereu la baza stivei dupa login (Login a fost scos).
                        popUpTo<PlaceList> { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(tab.icon, contentDescription = null) },
                label = { Text(tab.label) },
            )
        }
    }
}
