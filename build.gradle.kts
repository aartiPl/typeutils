val kotlinVersion: String = "1.8.22"

plugins {
    kotlin("jvm") version "1.8.22"
    id("com.adarshr.test-logger") version "3.2.0"
    `maven-publish`
    signing
    idea
}

group = "net.igsoft"
version = "0.5.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(11)
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

publishing {
    publications {
        create<MavenPublication>("typeutils") {
            artifactId = "typeutils"
            from(components["java"])

            pom {
                name.set("typeutils")
                description.set("TypeUtils - library for type safe utilities")
                url.set("https://github.com/aartiPl/typeutils")

                licenses(licencesSpec)
                developers(developersSpec)
                scm(scmSpec)
            }
        }
    }

    repositories {
        maven {
            val releasesRepoUrl = "https://oss.sonatype.org/service/local/staging/deploy/maven2/"
            val snapshotsRepoUrl = "https://oss.sonatype.org/content/repositories/snapshots/"
            url = uri(if (project.version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl)

            credentials {
                username = project.findProperty("sonatype.user") as String? ?: System.getenv("SONATYPE_USER")
                password = project.findProperty("sonatype.password") as String? ?: System.getenv("SONATYPE_PASSWORD")
            }
        }
    }
}

signing {
    sign(publishing.publications["typeutils"])
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    implementation("org.apache.commons:commons-lang3:3.12.0")

    testImplementation("org.junit.platform:junit-platform-suite-engine:1.9.0")
    testImplementation("org.junit.platform:junit-platform-suite-api:1.9.0")
    testImplementation("org.junit.platform:junit-platform-suite-commons:1.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.0")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.25")
    testImplementation("io.mockk:mockk:1.13.2")
}
