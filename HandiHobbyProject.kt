package database

data class HandiHobbyProject(
    val title: String,
    val description: String,
    val materials: String,
    val steps: String,
    val imagePath: String? = null
)
