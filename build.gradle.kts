import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    application
}

group = "hg.divine.invoice"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("net.sf.jasperreports:jasperreports:6.20.0")
    implementation("com.lowagie:itext:2.1.7")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}