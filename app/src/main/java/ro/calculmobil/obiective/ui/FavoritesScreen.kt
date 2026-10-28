package ro.calculmobil.obiective.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ro.calculmobil.obiective.data.PlacesRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(favoriteIds: Set<Int>, onRemove: (Int) -> Unit) {
    val favorites = PlacesRepository.places.filter { it.id in favoriteIds }

    Scaffold(topBar = { TopAppBar(title = { Text("Favorite") }) }) { padding ->
        if (favorites.isEmpty()) {
            Box(Modifier.fillMaxSize().padding(padding).padding(24.dp), contentAlignment = Alignment.Center) {
                Text(
                    "Nu ai niciun loc favorit. Deschide un obiectiv din Categorii și apasă „Adaugă la favorite”.",
                    textAlign = TextAlign.Center,
                )
            }
        } else {
            LazyColumn(Modifier.padding(padding)) {
                items(favorites, key = { it.id }) { place ->
                    ListItem(
                        headlineContent = { Text(place.name) },
                        supportingContent = { Text("${place.category.label} · ${place.town}") },
                        trailingContent = {
                            IconButton(onClick = { onRemove(place.id) }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Elimină din favorite")
                            }
                        },
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}
