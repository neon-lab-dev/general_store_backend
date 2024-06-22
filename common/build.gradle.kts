plugins {
	java
	id("org.springframework.boot") version "3.2.3" apply false
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.neonlab"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

dependencyManagement{
	imports {
		mavenBom ("org.springframework.boot:spring-boot-dependencies:3.2.3")
	}
}

repositories {
	mavenCentral()
	maven("https://jitpack.io")
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	compileOnly("org.projectlombok:lombok:1.18.30")
	annotationProcessor("org.projectlombok:lombok:1.18.30")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")

	//added
	implementation("org.apache.commons:commons-lang3:3.14.0")
	implementation ("org.modelmapper:modelmapper:3.1.1")
	implementation("org.springframework.boot:spring-boot-starter-security:3.1.0")
	implementation("org.springframework.security:spring-security-config:6.0.0")
	implementation("com.github.imagekit-developer:imagekit-java:2.0.0")
	implementation("jakarta.validation:jakarta.validation-api:3.0.1")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
