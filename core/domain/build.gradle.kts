plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlin {
    jvmToolchain(17)
}


dependencies {
    implementation(libs.kotlinx.coroutines.core)
    api("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")

}