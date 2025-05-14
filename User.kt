package database

data class User(
    var id: Int,
    var username: String,
    var email: String,
    var passwordHash: String
)
