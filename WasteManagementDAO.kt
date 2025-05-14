package database

import java.sql.Connection
import java.sql.SQLException

data class WasteItemDetails(
    val itemName: String,
    val materialType: String,
    val condition: String,
    val reusingIdea: String,
    val recyclingMethod: String
)

class WasteManagementDAO(private val connection: Connection) {

    fun getWasteDetails(query: String): List<WasteItemDetails> {
        val sql = """
            SELECT 
                item_name,
                material_type,
                condition,
                reusing_idea,
                recycling_method
            FROM WasteManagement 
            WHERE LOWER(item_name) = LOWER(?)
            OR LOWER(item_name) LIKE LOWER(?)
            ORDER BY 
                CASE 
                    WHEN LOWER(item_name) = LOWER(?) THEN 0 
                    ELSE 1 
                END
            LIMIT 1
        """
        val items = mutableListOf<WasteItemDetails>()
        val searchTerm = "%$query%"

        try {
            connection.prepareStatement(sql).use { stmt ->
                stmt.setString(1, query)
                stmt.setString(2, searchTerm)
                stmt.setString(3, query)

                val rs = stmt.executeQuery()
                while (rs.next()) {
                    items.add(WasteItemDetails(
                        itemName = rs.getString("item_name"),
                        materialType = rs.getString("material_type") ?: "Not specified",
                        condition = rs.getString("condition") ?: "Not specified",
                        reusingIdea = rs.getString("reusing_idea") ?: "No reuse ideas available",
                        recyclingMethod = rs.getString("recycling_method") ?: "Not recyclable"
                    ))
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
        }

        return items
    }
}
