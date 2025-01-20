plugins {
    // Apply the application plugin to add support for building a CLI application in Java.
    application
}

java {
    sourceCompatibility = JavaVersion.VERSION_17

    // Apply a specific Java toolchain to ease working on different environments.
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // This dependency is used by the application.
    implementation(libs.guava)
}

application {
    // Define the main class for the application.
    mainClass = "jp.ac.uryukyu.ie.e235221.WeatherSolver"
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "jp.ac.uryukyu.ie.e235221.WeatherSolver"
    }
}

tasks.test {
    useJUnitPlatform()
}

sourceSets {
    main {
        java {
            setSrcDirs(setOf(file("src/main/java")))
        }
    }
}
