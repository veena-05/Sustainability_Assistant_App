package view

import tornadofx.*
import database.UserDAO
import database.DatabaseHelper
import javafx.geometry.Pos
import javafx.scene.control.PasswordField
import javafx.scene.text.FontWeight  // Add this import

class RegisterView : View("Register") {
    private val userDao = UserDAO(DatabaseHelper.getConnection())
    private val usernameField = textfield()
    private val emailField = textfield()
    private val passwordField = PasswordField()
    private val confirmPasswordField = PasswordField()

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
            label("Register") {
                style {
                    fontSize = 24.px
                    textFill = c("#2E8B57")
                    fontWeight = FontWeight.BOLD
                }
            }

            field("Username") {
                add(usernameField)
            }

            field("Email") {
                add(emailField)
            }

            field("Password") {
                add(passwordField)
            }

            field("Confirm Password") {
                add(confirmPasswordField)
            }

            hbox(10) {
                alignment = Pos.CENTER_RIGHT
                button("Cancel") {
                    action {
                        close()
                    }
                }

                button("Register") {
                    action {
                        if (passwordField.text != confirmPasswordField.text) {
                            error("Registration Failed", "Passwords do not match")
                            return@action
                        }

                        if (userDao.createUser(usernameField.text, emailField.text, passwordField.text)) {
                            information("Registration Successful", "Account created successfully")
                            close()
                        } else {
                            error("Registration Failed", "Username or email already exists")
                        }
                    }
                }
            }
        }
    }
}
