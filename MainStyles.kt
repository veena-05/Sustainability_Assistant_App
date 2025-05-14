package styles

import javafx.scene.effect.BoxBlur
import javafx.scene.layout.BackgroundRepeat
import javafx.scene.layout.BackgroundSize
import tornadofx.*
import javafx.scene.text.FontWeight

class MainStyles : Stylesheet() {
    companion object {
        val mainBackground by cssclass()
        val homepageBackground by cssclass()
        val welcomeHeading by cssclass()
        val descriptionText by cssclass()
        val slideshowFrame by cssclass()
        val factText by cssclass()
        val navPane by cssclass()
        val journalSearch by cssclass()
        val moduleIntro by cssclass()
        val journalEntry by cssclass()
        val highlightedText by cssclass()
        val journalTitle by cssclass()
        val transparentContainer by cssclass()
        val chatBubbleUser by cssclass()
        val chatBubbleSystem by cssclass()
        val wasteCard by cssclass()
        val quickActionButton by cssclass()
        val activityCard by cssclass()
        val activityGrid by cssclass()
        val ecoButton by cssclass()
        val ecoTextField by cssclass()
        val ecoResultBox by cssclass()
        val loginForm by cssclass()
        val registerForm by cssclass()
        val authButton by cssclass()

    }

    init {
        val noEffect = BoxBlur(0.0, 0.0, 0)

        mainBackground {
            backgroundImage += javaClass.getResource("/images/backdrop.jpg")!!.toURI()
            backgroundRepeat += Pair(BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT)
            backgroundSize += BackgroundSize(100.0, 100.0, true, true, false, true)
            effect = noEffect
        }


        transparentContainer {
            backgroundColor += c("transparent")
        }

        welcomeHeading {
            fontSize = 36.px
            fontWeight = FontWeight.BOLD
            textFill = c("#2E8B57")
            fontFamily = "Arial Rounded MT Bold"
            padding = box(10.px)
            backgroundColor += c("#FFFFFF", 0.7)
            borderRadius += box(10.px)
            effect = noEffect
        }

        descriptionText {
            fontSize = 16.px
            textFill = c("#333333")
            fontFamily = "Arial"
            padding = box(0.px, 10.px, 20.px, 10.px)
            maxWidth = 600.px
            wrapText = true
            backgroundColor += c("#FFFFFF", 0.7)
            borderRadius += box(10.px)
            effect = noEffect
        }

        slideshowFrame {
            backgroundColor += c("#FFFFFF", 0.8)
            padding = box(20.px)
            borderRadius += box(10.px)
            borderColor += box(c("#2E8B57"))
            borderWidth += box(2.px)
            effect = noEffect
        }

        factText {
            fontSize = 32.px
            fontWeight = FontWeight.BOLD
            textFill = c("#000000")
            fontFamily = "Times New Roman"
            padding = box(10.px)
            maxWidth = 400.px
            wrapText = true
            effect = noEffect
        }

        navPane {
            backgroundColor += c("#2E8B57", 0.9)
            padding = box(10.px)
            spacing = 20.px
            effect = noEffect
        }

        journalSearch {
            backgroundColor += c("#FFFFFF", 0.9)
            borderRadius += box(15.px)
            borderColor += box(c("#2E8B57"))
            borderWidth += box(1.px)
            padding = box(15.px)
        }

        moduleIntro {
            fontSize = 18.px
            textFill = c("#2E8B57")
            fontFamily = "Arial"
            fontWeight = FontWeight.BOLD
            padding = box(10.px)
            maxWidth = 600.px
            wrapText = true
            backgroundColor += c("#FFFFFF", 0.7)
            borderRadius += box(10.px)
        }

        journalEntry {
            backgroundColor += c("#F8F8F8")
            borderRadius += box(5.px)
            borderColor += box(c("#2E8B57", 0.3))
            borderWidth += box(1.px)
            padding = box(15.px)
        }

        highlightedText {
            backgroundColor += c("#FFFFFF", 0.8)
            padding = box(5.px)
            borderRadius += box(5.px)
            effect = noEffect
        }

        journalTitle {
            fontSize = 16.px
            fontWeight = FontWeight.BOLD
            textFill = c("#2E8B57")
            fontFamily = "Georgia"
            padding = box(0.px, 0.px, 5.px, 0.px)
        }

        chatBubbleUser {
            backgroundColor += c("#e1f5e1")
            borderRadius += box(10.px)
            padding = box(10.px)
            maxWidth = 300.px
            wrapText = true
        }

        chatBubbleSystem {
            backgroundColor += c("#f0f0f0")
            borderRadius += box(10.px)
            padding = box(10.px)
            maxWidth = 300.px
            wrapText = true
        }

        wasteCard {
            backgroundColor += c("#f0f8ff")
            borderRadius += box(10.px)
            padding = box(10.px)
        }

        quickActionButton {
            fontSize = 14.px
            padding = box(8.px)
            borderRadius += box(5.px)
        }
        ecoButton {
            fontSize = 28.px
            backgroundColor += c("#2E8B57")
            textFill = c("#FFFFFF")
            padding = box(15.px, 30.px)
            borderRadius += box(10.px)
            and(hover) {
                backgroundColor += c("#1B5E20")
            }
        }

        ecoTextField {
            fontSize = 28.px
            minWidth = 600.px
            backgroundColor += c("#FFFFFF")
            borderRadius += box(10.px)
            padding = box(15.px)
            borderColor += box(c("#2E8B57"))
            borderWidth += box(2.px)
        }

        ecoResultBox {
            padding = box(20.px)
            backgroundColor += c("#FFFFFF")
            borderRadius += box(15.px)
            minWidth = 600.px
            borderColor += box(c("#2E8B57"))
            borderWidth += box(2.px)
        }

        navPane {
            backgroundColor += c("#2E8B57")
            padding = box(10.px)
            spacing = 20.px
            minHeight = 50.px
            borderColor += box(c("#1B5E20"))
            borderWidth += box(0.px, 0.px, 2.px, 0.px)
        }
        loginForm {
            backgroundColor += c("#FFFFFF", 0.9)
            borderRadius += box(10.px)
            padding = box(20.px)
        }

        registerForm {
            backgroundColor += c("#FFFFFF", 0.9)
            borderRadius += box(10.px)
            padding = box(20.px)
        }

        authButton {
            backgroundColor += c("#2E8B57")
            textFill = c("#FFFFFF")
            and(hover) {
                backgroundColor += c("#1B5E20")
            }
        }
    }
}
