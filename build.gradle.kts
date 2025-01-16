plugins {
	java
	id("org.springframework.boot") version "3.4.1"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.gravitee"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2024.0.0"

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

	// logs
	implementation("org.springframework.boot:spring-boot-starter-logging")
	implementation("net.logstash.logback:logstash-logback-encoder:7.0.1")

	// consul
	implementation("org.springframework.cloud:spring-cloud-starter-bootstrap")
	implementation("org.springframework.cloud:spring-cloud-starter-consul-config")
	implementation("org.springframework.cloud:spring-cloud-starter-consul-discovery")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

// not to build plain jar
tasks.getByName<Jar>("jar") {
	enabled = false
}