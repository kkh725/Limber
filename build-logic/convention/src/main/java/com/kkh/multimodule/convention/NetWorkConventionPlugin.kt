package com.kkh.multimodule.convention

import com.kkh.multimodule.convention.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.dependencies

class NetWorkConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            apply(plugin = "com.google.devtools.ksp")

            dependencies {
                "implementation"(libs.findLibrary("retrofit").get())
                "implementation"(libs.findLibrary("retrofit-kotlin-serialization").get())
                "implementation"(libs.findLibrary("retrofit-gson").get())

                "implementation"(libs.findLibrary("okhttp").get())
                "implementation"(libs.findLibrary("okhttp-logging").get())
                "implementation"(platform(libs.findLibrary("okhttp-bom").get()))
            }
        }
    }
}