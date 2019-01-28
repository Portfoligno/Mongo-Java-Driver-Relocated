plugins {
  maven
  `java-library`
  id("com.github.johnrengelman.shadow") version "4.0.4"
}
tasks.getByName<Wrapper>("wrapper") {
  gradleVersion = "5.1.1"
}

tasks.withType<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar> {
  // Combine dependencies from the 'shadow' configuration instead!
  configurations = listOf(project.configurations.getByName("shadow"))

  artifacts {
    add("archives", this@withType)
  }
  archiveName = "$baseName-$version.$extension"

  relocate("org.bson", "bson")
  relocate("com.mongodb", "mongodb")
}

repositories {
  jcenter()
}
dependencies {
  shadow("org.mongodb:mongodb-driver-reactivestreams:1.10.0") {
    exclude("org.reactivestreams", "reactive-streams")
  }
  api("org.reactivestreams:reactive-streams:1.0.2")
}
