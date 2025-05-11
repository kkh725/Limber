plugins {
    id("java-library")
    `kotlin-dsl`             // Kotlin DSL 플러그인
    `java-gradle-plugin`     // Gradle Plugin 개발용 플러그인
    alias(libs.plugins.jetbrains.kotlin.jvm)
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

//org. gradle import를 위한 의존성.
dependencies{
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = libs.plugins.multi.module.android.application.get().pluginId
            implementationClass = "com.kkh.multimodule.convention.AndroidApplicationConventionPlugin"
        }

        register("hilt") {
            id = libs.plugins.multi.module.hilt.get().pluginId
            implementationClass = "com.kkh.multimodule.convention.HiltConventionPlugin"
        }

        register("network") {
            id = libs.plugins.multi.module.network.get().pluginId
            implementationClass = "com.kkh.multimodule.convention.NetWorkConventionPlugin"
        }

        register("androidLibrary") {
            id = libs.plugins.multi.module.library.get().pluginId
            implementationClass = "com.kkh.multimodule.convention.AndroidLibraryConventionPlugin"
        }
    }
}