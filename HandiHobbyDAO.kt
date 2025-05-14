package database

import java.sql.Connection
import java.sql.SQLException

class HandiHobbyDAO(private val connection: Connection) {

    fun getAllProjects(): List<HandiHobbyProject> {
        val sql = "SELECT title, description, materials, steps, image_path FROM handi_hobby_projects"
        val projects = mutableListOf<HandiHobbyProject>()

        try {
            connection.prepareStatement(sql).use { stmt ->
                val rs = stmt.executeQuery()
                while (rs.next()) {
                    projects.add(
                        HandiHobbyProject(
                            title = rs.getString("title"),
                            description = rs.getString("description"),
                            materials = rs.getString("materials"),
                            steps = rs.getString("steps"),
                            imagePath = rs.getString("image_path")
                        )
                    )
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return projects
    }
}
