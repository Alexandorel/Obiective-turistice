package ro.calculmobil.obiective.navigation

import kotlinx.serialization.Serializable

// Rute type-safe: niciun sir de caractere de tip "detail/{id}".
@Serializable object Login

@Serializable object CategoriesGraph
@Serializable object PlaceList
@Serializable data class PlaceDetail(val id: Int)

@Serializable object Favorites
@Serializable object About
