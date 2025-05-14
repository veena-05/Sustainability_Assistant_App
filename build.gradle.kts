plugins {
    kotlin("jvm") version "1.8.10"
    application
    id("org.openjfx.javafxplugin") version "0.0.13"
}

repositories {
    mavenCentral()
}

val javafxVersion = "17"

dependencies {
    implementation("org.openjfx:javafx-controls:$javafxVersion")
    implementation("org.openjfx:javafx-fxml:$javafxVersion")
    implementation("no.tornado:tornadofx:1.7.20")
    implementation("org.xerial:sqlite-jdbc:3.42.0.0")
}

javafx {
    version = javafxVersion
    modules = listOf("javafx.controls", "javafx.fxml")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    withJavadocJar()
    withSourcesJar()
}

application {
    mainClass.set("MainKt")
    applicationDefaultJvmArgs = listOf(
        "--module-path", System.getProperty("java.home") + "/lib", // Make sure JavaFX is added to the module path
        "--add-modules=javafx.controls,javafx.fxml", // Explicitly specify required JavaFX modules
        "--add-opens=javafx.graphics/javafx.scene=ALL-UNNAMED",
        "--add-opens=javafx.graphics/javafx.scene.layout=ALL-UNNAMED",
        "--add-opens=javafx.graphics/javafx.scene.control=ALL-UNNAMED",
        "--add-opens=javafx.base/javafx.collections=ALL-UNNAMED",
        "--add-opens=javafx.base/javafx.beans=ALL-UNNAMED",
        "--illegal-access=permit",  // Bypass illegal access checks
        "--add-exports=javafx.graphics/javafx.scene=ALL-UNNAMED",
        "--add-exports=javafx.base/javafx.collections=ALL-UNNAMED"
    )
}

sourceSets {
    main {
        resources.srcDirs("src/main/resources")
    }
    tasks.processResources {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}

