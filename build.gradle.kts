val kotlinVersion: String = "2.3.10"

plugins {
    kotlin("jvm") version "2.3.10"
    id("com.adarshr.test-logger") version "4.0.0"
    id("com.vanniktech.maven.publish") version "0.36.0"
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
version = calculateVersion("0.7.0")

println("Version: $version")

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

testlogger {
    showStandardStreams = true
    showFullStackTraces = false
}

tasks.test {
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
    connection.set("scm:git:git://https://github.com/aartiPl/typeutils.git")
    developerConnection.set("scm:git:ssh:https://github.com/aartiPl/typeutils.git")
    url.set("https://github.com/aartiPl/typeutils/tree/master")
}

mavenPublishing {
    // You can keep group/version from project; artifactId is set here.
    coordinates(group.toString(), project.name, version.toString())

    // Central Portal (new Sonatype flow) + automatic release for non-SNAPSHOT
    publishToMavenCentral()

    // Uses Gradle Signing under the hood.
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

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    val junitVersion = "5.13.1"
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testImplementation("com.willowtreeapps.assertk:assertk:0.28.1")
    testImplementation("nl.jqno.equalsverifier:equalsverifier:4.0.2")
    testImplementation("io.mockk:mockk:1.14.2")
}
