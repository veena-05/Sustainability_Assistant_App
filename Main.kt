import tornadofx.*
import view.MainView
import styles.MainStyles

class SustainabilityAssistantApp : App(MainView::class, MainStyles::class)

fun main() {
    launch<SustainabilityAssistantApp>()
}
