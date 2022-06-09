plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.allopen") version "1.6.21"
    id("io.quarkus")
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project
val jaicfVersion: String = "1.2.4"

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-picocli")
    implementation("io.quarkus:quarkus-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-arc")
    // DM: For quarkus native-image build, we need to exclude the transitive logging lib (eg log4j, log4j2, slf4j)
    // and use the corresponding logging adapter.
    // see: https://quarkus.io/guides/logging#logging-adapters
    implementation("com.just-ai.jaicf:core:$jaicfVersion") {
        exclude(group="org.slf4j", module="slf4j-api")
    }
    implementation("org.jboss.slf4j:slf4j-jboss-logmanager")

    //implementation("com.just-ai.jaicf:mapdb:$jaicfVersion")
    //implementation("com.just-ai.jaicf:jaicp:$jaicfVersion")
    testImplementation("io.quarkus:quarkus-junit5")
}

group = "com.example"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

allOpen {
    annotation("javax.ws.rs.Path")
    annotation("javax.enterprise.context.ApplicationScoped")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    kotlinOptions.javaParameters = true
}
