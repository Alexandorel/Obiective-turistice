package ro.calculmobil.obiective.data

enum class Category(val label: String) {
    SPORT("Sport"),
    MUZEE("Muzee"),
    RESTAURANTE("Restaurante"),
}

data class Place(
    val id: Int,
    val name: String,
    val category: Category,
    val town: String,
    val description: String,
)

// Singura sursa de date a aplicatiei, scrisa direct in cod.
object PlacesRepository {
    val places = listOf(
        Place(1, "Muzeul Satului Maramureșean", Category.MUZEE, "Sighetu Marmației",
            "Muzeu în aer liber cu gospodării țărănești, biserici de lemn și porți maramureșene."),
        Place(2, "Memorialul Victimelor Comunismului și al Rezistenței", Category.MUZEE, "Sighetu Marmației",
            "Fosta închisoare politică de la Sighet, transformată în muzeu-memorial."),
        Place(3, "Muzeul Județean de Istorie și Arheologie Maramureș", Category.MUZEE, "Baia Mare",
            "Colecții de arheologie, istorie și numismatică din zona Maramureșului."),
        Place(4, "Domeniul schiabil Cavnic", Category.SPORT, "Cavnic",
            "Pârtii de schi pentru toate nivelurile, cu teleschiuri și telescaun."),
        Place(5, "Stațiunea Borșa", Category.SPORT, "Borșa",
            "Stațiune montană pentru schi iarna și drumeții vara, la poalele Munților Rodnei."),
        Place(6, "Parcul Național Munții Rodnei", Category.SPORT, "Borșa / Moisei",
            "Trasee montane marcate, lacuri glaciare și vârful Pietrosul Rodnei."),
        Place(7, "Restaurant Pescăruș", Category.RESTAURANTE, "Baia Mare",
            "Restaurant cunoscut din centrul orașului, cu bucătărie tradițională și internațională."),
        Place(8, "Casa Iurca de Călinești", Category.RESTAURANTE, "Sighetu Marmației",
            "Restaurant cu specific maramureșean, în stil tradițional."),
        Place(9, "Hanul Bulzu", Category.RESTAURANTE, "Baia Sprie",
            "Han cu preparate tradiționale românești, pe drumul spre Cavnic."),
    )

    fun place(id: Int): Place = places.first { it.id == id }
}
