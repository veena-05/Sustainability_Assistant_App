package view

import tornadofx.*
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.animation.KeyFrame
import javafx.animation.Timeline
import javafx.util.Duration
import javafx.scene.control.Label
import javafx.geometry.Pos
import java.sql.Connection
import database.DatabaseHelper
import styles.MainStyles
import styles.MainStyles.Companion.welcomeHeading
import styles.MainStyles.Companion.descriptionText
import styles.MainStyles.Companion.slideshowFrame
import styles.MainStyles.Companion.factText
import styles.MainStyles.Companion.navPane
import styles.MainStyles.Companion.highlightedText
import styles.MainStyles.Companion.homepageBackground
import styles.MainStyles.Companion.transparentContainer
import javafx.scene.text.FontWeight
import javafx.scene.layout.HBox
import app.currentUser
import javafx.scene.control.MenuButton
import javafx.scene.control.MenuItem
import javafx.scene.layout.Priority
import app.UserSession
import javafx.scene.layout.VBox

class MainView : View("Sustainability Assistant") {
    private val connection: Connection = DatabaseHelper.getConnection()
    private val facts = mutableListOf<Pair<String, String>>()
    private val factLabel = Label()
    private val imageView = ImageView()
    private var index = 0
    private lateinit var mainContainer: VBox

    override val root = stackpane {
        addClass(homepageBackground)

        mainContainer = vbox {
            addClass(transparentContainer)

            // Navigation pane
            add(createNavigationPane())

            // Main content
            scrollpane {
                isFitToWidth = true
                addClass(transparentContainer)
                stackpane {
                    addClass(transparentContainer)
                    vbox {
                        alignment = Pos.CENTER
                        addClass(transparentContainer)
                        spacing = 20.0

                        stackpane {
                            label("Welcome to Sustainability Assistant") {
                                addClass(welcomeHeading)
                                addClass(highlightedText)
                            }
                        }

                        stackpane {
                            label("Discover eco-friendly practices, learn waste management techniques, and explore creative DIY projects to live a more sustainable life.") {
                                addClass(descriptionText)
                                addClass(highlightedText)
                            }
                        }

                        hbox(20) {
                            alignment = Pos.CENTER

                            // Left side images container
                            vbox(15) {
                                alignment = Pos.CENTER
                                minWidth = 200.0
                                maxWidth = 200.0

                                add(createImageView("left1.jpg", 180.0, 180.0))
                                add(createImageView("left2.jpg", 180.0, 180.0))
                            }

                            // Slideshow container
                            stackpane {
                                addClass(slideshowFrame)
                                minWidth = 800.0
                                maxWidth = 800.0
                                minHeight = 400.0
                                maxHeight = 400.0
                                hbox(20) {
                                    alignment = Pos.CENTER
                                    minWidth = 800.0
                                    maxWidth = 800.0

                                    vbox(10) {
                                        alignment = Pos.CENTER_LEFT
                                        minWidth = 400.0
                                        maxWidth = 400.0
                                        add(factLabel.apply {
                                            addClass(factText)
                                            addClass(highlightedText)
                                        })
                                    }

                                    add(imageView.apply {
                                        fitWidth = 300.0
                                        fitHeight = 300.0
                                        isPreserveRatio = true
                                    })
                                }
                            }

                            // Right side images container
                            vbox(15) {
                                alignment = Pos.CENTER
                                minWidth = 200.0
                                maxWidth = 200.0

                                add(createImageView("right1.jpg", 180.0, 180.0))
                                add(createImageView("right2.jpg", 180.0, 180.0))
                            }
                        }

                        stackpane {
                            addClass(slideshowFrame)
                            vbox(10) {
                                alignment = Pos.CENTER_LEFT
                                padding = insets(20.0)

                                label("Interesting Products/Services:") {
                                    style {
                                        fontSize = 24.px
                                        fontWeight = FontWeight.BOLD
                                        textFill = c("#2E8B57")
                                    }
                                }

                                vbox(15) {
                                    label("• Bioenzyme: Natural cleaning solution made from citrus peels, jaggery and water. Effective, eco-friendly alternative to chemical cleaners.") {
                                        style {
                                            fontSize = 16.px
                                            textFill = c("#333333")
                                            wrapText = true
                                        }
                                    }

                                    label("• Reusable Tissues: Washable fabric tissues that reduce paper waste. Can be used like regular tissues but washed and reused multiple times.") {
                                        style {
                                            fontSize = 16.px
                                            textFill = c("#333333")
                                            wrapText = true
                                        }
                                    }

                                    label("• Ecosia: Search engine that plants trees with its ad revenue. Uses renewable energy and focuses on environmental sustainability.") {
                                        style {
                                            fontSize = 16.px
                                            textFill = c("#333333")
                                            wrapText = true
                                        }
                                    }

                                    label("• BrownLiving: E-commerce platform offering sustainable alternatives to everyday products. Focuses on plastic-free, ethical and eco-friendly options.") {
                                        style {
                                            fontSize = 16.px
                                            textFill = c("#333333")
                                            wrapText = true
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onDock() {
        super.onDock()
        refreshNavigation()
    }

    fun refreshNavigation() {
        mainContainer.children[0] = createNavigationPane()
    }

    override fun onBeforeShow() {
        super.onBeforeShow()
        primaryStage.width = 1200.0
        primaryStage.height = 800.0
    }

    init {
        loadFacts()
        if (facts.isNotEmpty()) {
            runSlideshow()
        }
    }

    private fun createImageView(imagePath: String, width: Double, height: Double): ImageView {
        return try {
            val resource = javaClass.getResource("/images/$imagePath")
            if (resource != null) {
                ImageView(Image(resource.toExternalForm(), width, height, true, true)).apply {
                    fitWidth = width
                    fitHeight = height
                    isPreserveRatio = true
                }
            } else {
                ImageView().apply {
                    fitWidth = width
                    fitHeight = height
                    style {
                        backgroundColor += c("#E8F5E9")
                    }
                    label("Image not found") {
                        style {
                            textFill = c("#2E8B57")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            ImageView().apply {
                fitWidth = width
                fitHeight = height
                style {
                    backgroundColor += c("#E8F5E9")
                }
                label("Image error") {
                    style {
                        textFill = c("#2E8B57")
                    }
                }
            }
        }
    }

    private fun loadFacts() {
        try {
            val stmt = connection.prepareStatement("SELECT fact_title, fact_image FROM homepage_facts")
            val rs = stmt.executeQuery()
            while (rs.next()) {
                facts.add(Pair(rs.getString("fact_title"), rs.getString("fact_image")))
            }
            if (facts.isNotEmpty()) {
                updateSlide()
            }
        } catch (e: Exception) {
            println("Error loading facts from database: ${e.message}")
            facts.add(Pair("Polyester clothing leaches microplastics into our skin due to its plastic content", "fact1.jpg"))
            facts.add(Pair("Fast fashion creates tonnes of waste, choking water bodies and peoples lives", "fact2.jpg"))
        }
    }

    private fun runSlideshow() {
        val timeline = Timeline(
            KeyFrame(
                Duration.seconds(5.0),
                { _ ->
                    index = (index + 1) % facts.size
                    updateSlide()
                }
            )
        )
        timeline.cycleCount = Timeline.INDEFINITE
        timeline.play()
    }

    private fun updateSlide() {
        val (text, imagePath) = facts[index]
        factLabel.text = text

        try {
            val resourceUrl = javaClass.getResource("/images/$imagePath")
            if (resourceUrl != null) {
                imageView.image = Image(resourceUrl.toExternalForm(), 300.0, 300.0, true, true)
            } else {
                imageView.image = Image("file:src/main/resources/images/$imagePath", 300.0, 300.0, true, true)
            }
        } catch (e: Exception) {
            println("Error loading image: ${e.message}")
        }
    }

    private fun createNavigationPane(): HBox = hbox(10) {
        addClass(navPane)
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

        region {
            hgrow = Priority.ALWAYS
        }

        if (UserSession.isLoggedIn) {
            val profileMenu = MenuButton(currentUser.username.value ?: "Profile").apply {
                style {
                    backgroundColor += c("transparent")
                    textFill = c("#FFFFFF")
                    fontSize = 16.px
                    fontWeight = FontWeight.BOLD
                }

                items.addAll(
                    MenuItem("My Profile").apply {
                        setOnAction {
                            // Add profile view functionality here
                        }
                    },
                    MenuItem("Logout").apply {
                        setOnAction {
                            UserSession.logout()
                            refreshNavigation()
                            replaceWith<MainView>()
                        }
                    }
                )
            }
            add(profileMenu)
        } else {
            button("Login / Register") {
                style {
                    backgroundColor += c("transparent")
                    textFill = c("#FFFFFF")
                    fontSize = 16.px
                    fontWeight = FontWeight.BOLD
                }
                action {
                    find<LoginView>().openModal()
                }
            }
        }
    }
}
