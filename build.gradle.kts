val kotlinVersion: String = "2.1.21"

plugins {
    kotlin("jvm") version "2.1.21"
    id("com.adarshr.test-logger") version "4.0.0"
    `maven-publish`
    signing
    idea
}

group = "net.igsoft"
version = "0.7.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }

    withJavadocJar()
    withSourcesJar()
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
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")

    val junitVersion = "5.13.1"
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testImplementation("com.willowtreeapps.assertk:assertk:0.28.1")
    testImplementation("nl.jqno.equalsverifier:equalsverifier:4.0.2")
    testImplementation("io.mockk:mockk:1.14.2")
}
