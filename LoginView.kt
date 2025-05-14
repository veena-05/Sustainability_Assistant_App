package view

import tornadofx.*
import database.UserDAO
import database.DatabaseHelper
import javafx.geometry.Pos
import javafx.scene.control.PasswordField
import javafx.scene.text.FontWeight
import styles.MainStyles
import app.currentUser
import app.UserSession

class LoginView : View("Login") {
    private val userDao = UserDAO(DatabaseHelper.getConnection())
    private val usernameField = textfield()
    private val passwordField = PasswordField()

    override val root = form {
        style {
            padding = box(20.px)
            spacing = 10.px
            backgroundColor += c("#FFFFFF", 0.9)
            borderRadius += box(10.px)
            borderColor += box(c("#2E8B57"))
            borderWidth += box(2.px)
            minWidth = 300.px
        }

        fieldset {
            label("Login") {
                style {
                    fontSize = 24.px
                    textFill = c("#2E8B57")
                    fontWeight = FontWeight.BOLD
                }
            }

            field("Username") {
                add(usernameField)
            }

            field("Password") {
                add(passwordField)
            }

            hbox(10) {
                alignment = Pos.CENTER_RIGHT
                button("Login") {
                    // In LoginView.kt, modify the login action:
                    action {
                        if (userDao.validateUser(usernameField.text, passwordField.text)) {
                            val user = userDao.getUserByUsername(usernameField.text)
                            if (user != null) {
                                currentUser.item = user
                                UserSession.login(user)
                                close()
                                find<MainView>().refreshNavigation()
                            }
                        }
                    }
                }

                button("Register") {
                    action {
                        find<RegisterView>().openModal()
                    }
                }
            }
        }
    }
}
