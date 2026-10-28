package ro.calculmobil.obiective.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ro.calculmobil.obiective.data.PlacesRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlaceDetailScreen(
    id: Int,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    onBack: () -> Unit,
) {
    // Primeste doar id-ul; restul vine din sursa de date.
    val place = PlacesRepository.place(id)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(place.name, maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Înapoi")
                    }
                },
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(place.name, style = MaterialTheme.typography.headlineSmall)
            Text("Categorie: ${place.category.label}")
            Text("Localitate: ${place.town}")
            Text(place.description, style = MaterialTheme.typography.bodyLarge)
            if (isFavorite) {
                OutlinedButton(onClick = onToggleFavorite) { Text("Elimină din favorite") }
            } else {
                Button(onClick = onToggleFavorite) { Text("Adaugă la favorite") }
            }
        }
    }
}
