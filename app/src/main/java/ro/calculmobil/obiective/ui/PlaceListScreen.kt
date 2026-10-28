package ro.calculmobil.obiective.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ro.calculmobil.obiective.data.Category
import ro.calculmobil.obiective.data.PlacesRepository

// Ecran fara stare proprie: filtrul vine din NavHost, navigarea iese prin lambda.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceListScreen(
    filter: Category?,
    onFilterChange: (Category?) -> Unit,
    onNavigateToDetail: (Int) -> Unit,
) {
    val places = PlacesRepository.places.filter { filter == null || it.category == filter }

    Scaffold(topBar = { TopAppBar(title = { Text("Obiective turistice") }) }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding)) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                FilterChip(selected = filter == null, onClick = { onFilterChange(null) }, label = { Text("Toate") })
                Category.entries.forEach { category ->
                    FilterChip(
                        selected = filter == category,
                        onClick = { onFilterChange(category) },
                        label = { Text(category.label) },
                    )
                }
            }
            LazyColumn {
                items(places, key = { it.id }) { place ->
                    ListItem(
                        headlineContent = { Text(place.name) },
                        supportingContent = { Text("${place.category.label} · ${place.town}") },
                        modifier = Modifier.clickable { onNavigateToDetail(place.id) },
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}
