import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    application
    id("com.gradleup.shadow") version "9.2.2"
}

application.mainClass = "io.github.kaktushose.notruf.Bootstrapper"
group = "io.github.kaktushose.notruf"
version = "2.0.0"

repositories {
    maven("https://central.sonatype.com/repository/maven-snapshots/")
    mavenCentral()
}

dependencies {
    implementation(libs.jda) {
        exclude(module = "opus-java")
    }
    implementation(libs.jdacommands)

    implementation(libs.logback.core)
    implementation(libs.logback.classic)
    implementation(libs.slf4j)

    implementation(libs.postgres)
    implementation(libs.hikari)
    implementation(libs.sadu.datasource)
    implementation(libs.sadu.queries)
    implementation(libs.sadu.mapper)
    implementation(libs.sadu.postgresql)
    implementation(libs.sadu.updater)

    implementation(libs.jspecify)
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.isIncremental = true
    options.compilerArgs.addAll(listOf("-parameters", "--enable-preview"))
    sourceCompatibility = "25"
}

tasks.withType<ShadowJar> {
    archiveFileName = "notrufbot.jar"
}

tasks.withType<JavaExec>() {
    jvmArgs("--enable-preview", "--sun-misc-unsafe-memory-access=allow")
}
