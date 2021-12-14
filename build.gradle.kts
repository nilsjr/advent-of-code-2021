import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("jvm") version "1.6.10"
}

tasks.withType<KotlinCompile>().configureEach {
  kotlinOptions.jvmTarget = "11"
}