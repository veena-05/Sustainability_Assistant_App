package view

import tornadofx.*
import database.WasteManagementDAO
import database.DatabaseHelper
import javafx.scene.text.FontWeight
import javafx.geometry.Pos
import javafx.scene.layout.HBox
import javafx.geometry.Insets
import styles.MainStyles

class WasteManagementView : View("Waste Management") {
    private val wasteDao = WasteManagementDAO(DatabaseHelper.getConnection())
    private val mainView: MainView by inject()

    private val searchField = textfield {
        promptText = "Enter waste item (e.g. 'batteries', 'plastic bottles')"
        style {
            fontSize = 28.px
            minWidth = 600.px
            backgroundColor += c("#FFFFFF")
            borderRadius += box(10.px)
            padding = box(15.px)
            borderColor += box(c("#2E8B57"))
            borderWidth += box(2.px)
        }
    }

    private val searchButton = button("🔍 Search") {
        style {
            fontSize = 28.px
            backgroundColor += c("#2E8B57")
            textFill = c("#FFFFFF")
            padding = box(15.px, 30.px)
            borderRadius += box(10.px)
        }
        action { searchWasteItems() }
    }

    private val resultsBox = vbox(10) {
        style {
            padding = box(20.px)
            backgroundColor += c("#FFFFFF")
            borderRadius += box(15.px)
            minWidth = 600.px
            borderColor += box(c("#2E8B57"))
            borderWidth += box(2.px)
        }
    }

    override val root = borderpane {
        addClass(MainStyles.mainBackground)

        top = vbox {
            alignment = Pos.CENTER
            spacing = 10.0  // Reduced spacing from 20.0 to 10.0
            padding = Insets(10.0, 20.0, 10.0, 20.0)  // Reduced vertical padding

            add(createNavigationPane())

            // Main title container
            stackpane {
                alignment = Pos.CENTER
                label("♻ Waste Management") {
                    style {
                        fontSize = 36.px
                        textFill = c("#1B5E20")
                        fontWeight = FontWeight.BOLD
                        padding = box(10.px, 20.px)
                        backgroundColor += c("#FFFFFF")
                        borderRadius += box(10.px)
                        borderColor += box(c("#2E8B57"))
                        borderWidth += box(2.px)
                    }
                }
            }

            // Subtitle container
            stackpane {
                alignment = Pos.CENTER
                label("Find proper disposal methods and recycling options") {
                    style {
                        fontSize = 20.px
                        textFill = c("#2E8B57")
                        padding = box(10.px, 20.px)
                        backgroundColor += c("#FFFFFF")
                        borderRadius += box(10.px)
                        borderColor += box(c("#2E8B57"))
                        borderWidth += box(2.px)
                    }
                }
            }
        }

        center = vbox {
            alignment = Pos.CENTER
            padding = Insets(10.0, 20.0, 20.0, 20.0)  // Reduced top padding from 20.0 to 10.0
            spacing = 15.0  // Reduced spacing from 20.0 to 15.0

            // Search prompt container
            stackpane {
                alignment = Pos.CENTER
                label("Search for waste items to learn how to properly dispose, recycle, or reuse them:") {
                    style {
                        fontSize = 24.px
                        textFill = c("#1B5E20")
                        padding = box(10.px, 20.px)  // Reduced vertical padding
                        backgroundColor += c("#E8F5E9")
                        borderRadius += box(10.px)
                        borderColor += box(c("#81C784"))
                        borderWidth += box(2.px)
                    }
                }
            }

            // Search components
            hbox(15) {  // Reduced spacing from 20 to 15
                alignment = Pos.CENTER
                add(searchField)
                add(searchButton)
            }

            // Results
            scrollpane {
                style {
                    maxHeight = 500.px
                    minWidth = 650.px
                }
                content = resultsBox
            }
        }
    }

    private fun createNavigationPane(): HBox = hbox(10) {
        addClass(MainStyles.navPane)
        alignment = Pos.CENTER

        button("Home") {
            style {
                backgroundColor += c("transparent")
                textFill = c("#FFFFFF")
                fontSize = 16.px
                fontWeight = FontWeight.BOLD
            }
            action { replaceWith<MainView>() }
        }

        button("Waste Management") {
            style {
                backgroundColor += c("transparent")
                textFill = c("#FFFFFF")
                fontSize = 16.px
                fontWeight = FontWeight.BOLD
            }
            action { replaceWith<WasteManagementView>() }
        }

        button("Handi-Hobby") {
            style {
                backgroundColor += c("transparent")
                textFill = c("#FFFFFF")
                fontSize = 16.px
                fontWeight = FontWeight.BOLD
            }
            action { replaceWith<HandiHobbyView>() }
        }
    }

    private fun searchWasteItems() {
        val query = searchField.text.trim()
        if (query.isEmpty()) return

        resultsBox.clear()
        val results = wasteDao.getWasteDetails(query)

        if (results.isEmpty()) {
            resultsBox.add(label("No results found for '$query'") {
                style {
                    fontSize = 24.px
                    padding = box(20.px)
                    textFill = c("#666666")
                }
            })
        } else {
            results.forEach { result ->
                resultsBox.add(vbox(10) {
                    style {
                        padding = box(20.px)
                        backgroundColor += c("#E8F5E9")
                        borderRadius += box(10.px)
                        minWidth = 550.px
                        borderColor += box(c("#81C784"))
                        borderWidth += box(2.px)
                    }

                    label("🗑️ Item: ${result.itemName}") {
                        style {
                            fontSize = 26.px
                            fontWeight = FontWeight.BOLD
                            textFill = c("#1B5E20")
                        }
                    }
                    label("🌿 Material Type: ${result.materialType}") {
                        style { fontSize = 24.px }
                    }
                    label("🔍 Condition: ${result.condition}") {
                        style { fontSize = 24.px }
                    }
                    label("♻ Reuse Idea: ${result.reusingIdea}") {
                        style { fontSize = 24.px }
                    }
                    label("🔄 Recycling Method: ${result.recyclingMethod}") {
                        style { fontSize = 24.px }
                    }
                })
            }
        }
    }
}
