plugins {
    id("com.github.johnrengelman.shadow") version "7.0.0"
    id("io.micronaut.application") version "1.5.4"
}

version = "0.1"
group = "de.goldmensch"

repositories {
    mavenCentral()
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("de.goldmensch.*")
    }
}

dependencies {
    annotationProcessor("io.micronaut.data:micronaut-data-processor")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut.data:micronaut-data-jdbc")
    implementation("io.micronaut.flyway:micronaut-flyway")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("javax.annotation:javax.annotation-api")
    runtimeOnly("ch.qos.logback:logback-classic")
    compileOnly("jakarta.persistence:jakarta.persistence-api:2.2.2")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
    implementation("io.micronaut:micronaut-validation")
    implementation("com.jsoniter:jsoniter:0.9.19")
}


application {
    mainClass.set("de.goldmensch.Application")
}
java {
    sourceCompatibility = JavaVersion.toVersion("16")
    targetCompatibility = JavaVersion.toVersion("16")
}



