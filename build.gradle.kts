import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.30"
	kotlin("plugin.spring") version "1.4.30"
}

group = "com.redeyemedia"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-jersey")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	runtimeOnly("mysql:mysql-connector-java")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	//implementation group: 'org.springframework.security', name: 'spring-security-crypto', version: '3.1.0.RELEASE'
	implementation("org.springframework.security:spring-security-crypto")
	//implementation group: 'org.springframework.security', name: 'spring-security-core', version: '5.4.5'
	implementation("org.springframework.security:spring-security-core:5.4.5")
	// implementation group: 'org.mindrot.bcrypt', name: 'bcrypt', version: '0.3'
	// implementation("org.mindrot.bcrypt:bcrypt:0.3")
	implementation("org.mindrot:jbcrypt:0.4")
	// implementation group: 'io.jsonwebtoken', name: 'jjwt', version: '0.2'
	implementation("io.jsonwebtoken:jjwt:0.2")
	// https://mvnrepository.com/artifact/org.springframework.security/spring-security-web
	implementation("org.springframework.security:spring-security-web")
	implementation("io.jsonwebtoken:jjwt-api:0.10.5")
	// https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
	implementation("org.apache.commons:commons-lang3:3.0")
	// https://mvnrepository.com/artifact/org.springframework.security/spring-security-config
	implementation("org.springframework.security:spring-security-config:5.4.5")




}



tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
