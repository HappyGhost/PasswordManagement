buildscript {
    val kotlin_version by extra("1.5.0")
    val sql_delight_version by extra("1.5.0")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.0")
        classpath("com.android.tools.build:gradle:4.2.1")
        classpath("com.squareup.sqldelight:gradle-plugin:$sql_delight_version")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlin_version")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}