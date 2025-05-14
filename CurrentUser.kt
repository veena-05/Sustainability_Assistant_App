package app

import database.User
import tornadofx.*

class CurrentUser : ItemViewModel<User>() {
    val id = bind(User::id)
    val username = bind(User::username)
    val email = bind(User::email)
    val passwordHash = bind(User::passwordHash)
}

// Global instance accessible throughout the app
val currentUser = CurrentUser()

object UserSession {
    var user: User? = null
    val isLoggedIn: Boolean get() = user != null

    fun login(user: User) {
        this.user = user
        currentUser.item = user
    }

    fun logout() {
        user = null
        currentUser.item = null
    }
}
