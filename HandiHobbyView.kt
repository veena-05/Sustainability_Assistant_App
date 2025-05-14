package view

import tornadofx.*
import database.HandiHobbyDAO
import database.DatabaseHelper
import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import java.io.InputStream
import javafx.scene.layout.HBox
import javafx.geometry.Insets
import styles.MainStyles

class HandiHobbyView : View("Handi-Hobby Projects") {
    private val dao = HandiHobbyDAO(DatabaseHelper.getConnection())
    private val detailBox = vbox(10)
    private val mainView: MainView by inject()

    override val root = borderpane {
        top = createNavigationPane()

        center = scrollpane {
            isFitToWidth = true
            vbox(20) {
                padding = Insets(20.00)
                alignment = Pos.CENTER
                style {
                    backgroundColor += c("#F5F5F5")
                }

                label("🌿 Handi-Hobby Projects") {
                    style {
                        fontSize = 36.px
                        fontWeight = FontWeight.BOLD
                        textFill = c("#2E8B57")
                        fontFamily = "Arial Rounded MT Bold"
                        padding = box(10.px)
                        backgroundColor += c("#FFFFFF")
                        borderRadius += box(10.px)
                    }
                }

                label("Discover creative DIY projects that repurpose household items into useful and beautiful creations.") {
                    style {
                        fontSize = 18.px
                        textFill = c("#2E8B57")
                        fontFamily = "Arial"
                        fontWeight = FontWeight.BOLD
                        padding = box(10.px)
                        maxWidth = 600.px
                        wrapText = true
                        backgroundColor += c("#FFFFFF")
                        borderRadius += box(10.px)
                    }
                }

                val grid = tilepane {
                    hgap = 20.0
                    vgap = 20.0
                    prefColumns = 3
                    alignment = Pos.CENTER
                    paddingAll = 10
                }

                dao.getAllProjects().forEach { project ->
                    val card = vbox(5) {
                        maxWidth = 200.00
                        alignment = Pos.TOP_CENTER

                        val imageView = loadProjectImage(project)
                        add(imageView)

                        label(project.title) {
                            style {
                                fontSize = 16.px
                                fontWeight = FontWeight.BOLD
                                textFill = c("#2E8B57")
                                maxWidth = 180.px
                                wrapText = true
                                textAlignment = javafx.scene.text.TextAlignment.CENTER
                            }
                        }

                        setOnMouseClicked { showDetails(project) }

                        style {
                            backgroundColor += c("#FFFFFF")
                            padding = box(10.px)
                            borderRadius += box(10.px)
                            borderColor += box(c("#2E8B57", 0.5))
                            borderWidth += box(1.px)
                        }

                        addEventFilter(javafx.scene.input.MouseEvent.MOUSE_ENTERED) {
                            style {
                                backgroundColor += c("#E8F5E9")
                                borderColor += box(c("#2E8B57"))
                                borderWidth += box(2.px)
                            }
                        }

                        addEventFilter(javafx.scene.input.MouseEvent.MOUSE_EXITED) {
                            style {
                                backgroundColor += c("#FFFFFF")
                                borderColor += box(c("#2E8B57", 0.5))
                                borderWidth += box(1.px)
                            }
                        }
                    }
                    grid.children.add(card)
                }

                add(grid)
                separator {}

                scrollpane {
                    prefHeight = 300.0
                    content = detailBox.apply {
                        style {
                            padding = box(20.px)
                            backgroundColor += c("#FFFFFF")
                            borderRadius += box(10.px)
                        }
                    }
                }
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


    private fun loadProjectImage(project: database.HandiHobbyProject): ImageView {
        return try {
            val imagePath = project.imagePath?.let {
                if (it.startsWith("images/")) it else "images/handi-hobby/$it"
            } ?: "images/handi-hobby/${project.title.lowercase().replace(" ", "_")}.jpg"

            println("Attempting to load image from: $imagePath")

            val inputStream: InputStream? = javaClass.getResourceAsStream("/$imagePath")
            if (inputStream != null) {
                ImageView(Image(inputStream, 180.0, 120.0, true, true)).apply {
                    isPreserveRatio = true
                }
            } else {
                println("Image not found at: $imagePath")
                createPlaceholderImageView()
            }
        } catch (e: Exception) {
            println("Error loading image: ${e.message}")
            createPlaceholderImageView()
        }
    }

    private fun createPlaceholderImageView(): ImageView {
        return ImageView().apply {
            fitWidth = 180.0
            fitHeight = 120.0
            style {
                backgroundColor += c("#DDDDDD")
            }
            stackpane {
                label("No Image") {
                    style {
                        textFill = c("#666666")
                    }
                }
            }
        }
    }

    private fun showDetails(project: database.HandiHobbyProject) {
        detailBox.children.setAll(
            vbox(10) {
                style {
                    padding = box(10.px)
                }

                hbox(10) {
                    val detailImage = loadProjectImage(project).apply {
                        fitWidth = 300.0
                        fitHeight = 200.0
                    }
                    add(detailImage)

                    vbox(5) {
                        label("🔧 ${project.title}") {
                            style {
                                fontSize = 24.px
                                fontWeight = FontWeight.BOLD
                                textFill = c("#2E8B57")
                            }
                        }

                        label("📝 Description:") {
                            style {
                                fontWeight = FontWeight.BOLD
                                textFill = c("#2E8B57")
                            }
                        }
                        label(project.description) {
                            style {
                                wrapText = true
                                maxWidth = 400.px
                            }
                        }
                    }
                }

                label("🧰 Materials:") {
                    style {
                        fontWeight = FontWeight.BOLD
                        textFill = c("#2E8B57")
                    }
                }
                label(project.materials) {
                    style {
                        wrapText = true
                    }
                }

                label("📋 Steps:") {
                    style {
                        fontWeight = FontWeight.BOLD
                        textFill = c("#2E8B57")
                    }
                }
                label(project.steps) {
                    style {
                        wrapText = true
                    }
                }
            }
        )
    }
}
