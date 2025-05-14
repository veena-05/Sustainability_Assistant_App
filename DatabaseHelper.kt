package database

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

private const val DB_URL = "jdbc:sqlite:sustainability.db"

object DatabaseHelper {
    fun getConnection(): Connection {
        return try {
            val connection = DriverManager.getConnection(DB_URL)
            // Initialize all tables
            UserDAO(connection)
            connection
        } catch (e: SQLException) {
            e.printStackTrace()
            throw RuntimeException("Database connection failed: ${e.message}")
        }
    }
}
