plugins {
    java
    antlr
}

group = "asttransformation"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:1.7.21")
    implementation("org.slf4j:slf4j-log4j12:1.7.21")

    antlr("org.antlr:antlr4:4.5")
    testCompile("junit", "junit", "4.12")
}

sourceSets.create("source") {
    java.srcDir("src/main/java")
    java.srcDir("build/generated-src/antlr/main")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}