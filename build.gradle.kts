import org.gradle.api.tasks.testing.Test
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("multiplatform") version "2.4.0"
    id("com.android.kotlin.multiplatform.library") version "9.1.1"
    id("com.adarshr.test-logger") version "4.0.0"
    id("com.vanniktech.maven.publish") version "0.37.0"
    idea
}

fun calculateVersion(baseVersion: String): String {
    return try {
        val branch = ProcessBuilder("git", "rev-parse", "--abbrev-ref", "HEAD")
            .redirectErrorStream(true)
            .start()
            .inputStream
            .bufferedReader()
            .readText()
            .trim()

        if (branch == "master" || branch == "main") {
            baseVersion
        } else {
            "$baseVersion-SNAPSHOT"
        }
    } catch (_: Exception) {
        baseVersion
    }
}

group = "net.igsoft"
version = calculateVersion("0.8.0")

println("Version: $version")

repositories {
    mavenCentral()
    google()
}

kotlin {
    jvm()
    android {
        namespace = "net.igsoft.typeutils"
        compileSdk = 37
        minSdk = 23
        withHostTestBuilder {
            sourceSetTreeName = "test"
        }
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }
    iosArm64()
    iosSimulatorArm64()
    jvmToolchain(17)

    sourceSets {
        commonMain.dependencies {
            implementation("org.jetbrains.kotlinx:atomicfu:0.33.0")
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation("com.willowtreeapps.assertk:assertk:0.28.1")
        }

        jvmTest.dependencies {
            implementation(kotlin("test-junit5"))
            implementation("nl.jqno.equalsverifier:equalsverifier:4.5")
            runtimeOnly("org.junit.platform:junit-platform-launcher")
        }
    }
}

testlogger {
    showStandardStreams = true
    showFullStackTraces = false
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

val licencesSpec = Action<MavenPomLicenseSpec> {
    license {
        name.set("MIT License")
        url.set("https://opensource.org/licenses/MIT")
    }
}

val developersSpec = Action<MavenPomDeveloperSpec> {
    developer {
        id.set("aartiPl")
        name.set("Marcin Kuszczak")
        email.set("aarti@interia.pl")
    }
}

val scmSpec = Action<MavenPomScm> {
    connection.set("scm:git:git://github.com/aartiPl/typeutils.git")
    developerConnection.set("scm:git:ssh:https://github.com/aartiPl/typeutils.git")
    url.set("https://github.com/aartiPl/typeutils/tree/master")
}

mavenPublishing {
    coordinates(group.toString(), project.name, version.toString())
    publishToMavenCentral()
    signAllPublications()

    pom {
        name.set(project.name)
        description.set("TypeUtils - library for type safe utilities")
        url.set("https://github.com/aartiPl/typeutils")

        licenses(licencesSpec)
        developers(developersSpec)
        scm(scmSpec)
    }
}
