plugins {
    java
}

group = "fr.elias"
version = "BETA-2.1"

dependencies {
    // Paper API
    compileOnly("io.papermc.paper:paper-api:1.21.8-R0.1-SNAPSHOT")

    // Annotations
    compileOnly("org.projectlombok:lombok:1.18.38")
    annotationProcessor("org.projectlombok:lombok:1.18.38")
    compileOnly("org.jetbrains:annotations:24.1.0")
}
