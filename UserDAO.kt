package database

import java.sql.Connection
import java.sql.SQLException
import java.security.MessageDigest

class UserDAO(private val connection: Connection) {

    init {
        createUsersTable()
    }

    private fun createUsersTable() {
        val sql = """
            CREATE TABLE IF NOT EXISTS users (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT UNIQUE NOT NULL,
                email TEXT UNIQUE NOT NULL,
                password_hash TEXT NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """
        try {
            connection.createStatement().execute(sql)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    private fun hashPassword(password: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(password.toByteArray())
        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun createUser(username: String, email: String, password: String): Boolean {
        val sql = "INSERT INTO users (username, email, password_hash) VALUES (?, ?, ?)"
        return try {
            connection.prepareStatement(sql).use { stmt ->
                stmt.setString(1, username)
                stmt.setString(2, email)
                stmt.setString(3, hashPassword(password))
                stmt.executeUpdate() > 0
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    fun getUserByUsername(username: String): User? {
        val sql = "SELECT id, username, email, password_hash FROM users WHERE username = ?"
        return try {
            connection.prepareStatement(sql).use { stmt ->
                stmt.setString(1, username)
                val rs = stmt.executeQuery()
                if (rs.next()) {
                    User(
                        id = rs.getInt("id"),
                        username = rs.getString("username"),
                        email = rs.getString("email"),
                        passwordHash = rs.getString("password_hash")
                    )
                } else {
                    null
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        }
    }

    fun validateUser(username: String, password: String): Boolean {
        val user = getUserByUsername(username)
        return user?.let { hashPassword(password) == it.passwordHash } ?: false
    }
}
