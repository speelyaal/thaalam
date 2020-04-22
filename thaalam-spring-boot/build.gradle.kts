import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.3.0.M2"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	kotlin("jvm") version "1.3.61"
	kotlin("plugin.spring") version "1.3.61"
}

group = "com.speelyaal"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux") {
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
	}
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	//Logging
	implementation("org.springframework.boot:spring-boot-starter-log4j2")



	//JSON Helpers
	//Jackson (for serializations)
	implementation(group="com.fasterxml.jackson.dataformat", name="jackson-dataformat-yaml", version= "2.11.0.rc1")
	implementation(group="com.fasterxml.jackson.core", name="jackson-databind", version="2.11.0.rc1")
	//Jayway JsonPath (to construct Thaalam fieds/prpoerties from Json Responses)
	implementation(group="com.jayway.jsonpath", name="json-path", version = "2.4.0")



	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
		exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
	}
	testImplementation("io.projectreactor:reactor-test")


}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}



