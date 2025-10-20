// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:8.10.0")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.20")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.57")
        classpath ("org.jetbrains.kotlin:compose-compiler-gradle-plugin:2.2.20")
    }
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}