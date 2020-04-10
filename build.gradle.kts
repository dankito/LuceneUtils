
buildscript {

    apply(from = "versions.gradle")

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:3.5.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.61")
    }
}


plugins {
        // So after executing publish staged repository can be closed and released by executing closeAndReleaseRepository
        id("io.codearte.nexus-staging") version "0.21.2"
}


val commonScriptsFile = File(File(project.gradle.gradleUserHomeDir, "scripts"), "commonScripts.gradle")
if (commonScriptsFile.exists()) {
    apply(from = commonScriptsFile)
}



subprojects {
    this.group = rootProject.group

    repositories {
        mavenCentral()
        jcenter()
    }
}