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
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.10.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // Mockito for mocking in tests
    testImplementation("org.mockito:mockito-core:4.0.0")

    // Use Gson and Spark for the application
    implementation("com.google.code.gson:gson:2.8.9")
    implementation("com.sparkjava:spark-core:2.9.4")

    // Other application dependencies
    // implementation(libs.guava)
	implementation("com.google.guava:guava:32.0.1-jre")

}
application {
    // Define the main class for the application.
    mainClass = "jp.ac.uryukyu.ie.e235221.Main"
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "jp.ac.uryukyu.ie.e235221.Main"
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
