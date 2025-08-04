package com.kkh.multimodule.convention

// AndroidApplicationConventionPlugin.kt
import com.android.build.api.dsl.ApplicationExtension
import com.kkh.multimodule.convention.extensions.configureKotlinAndroid
import com.kkh.multimodule.convention.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.io.File
import java.util.Properties

//그래들에서 클래스를 플러그인으로 설정할수 있게끔 함.
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.application")
            pluginManager.apply("org.jetbrains.kotlin.android")

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)

                defaultConfig {
                    namespace = libs.findVersion("projectApplicationId").get().toString()
                    applicationId = libs.findVersion("projectApplicationId").get().toString()
                    targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()
                    versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
                    versionName = libs.findVersion("projectVersionName").get().toString()
                }

                // 🔒 Signing 설정
                signingConfigs {
                    create("release") {
//                        val keystoreProps = Properties().apply {
//                            val propsFile = File(rootDir, "local.properties")
//                            if (propsFile.exists()) {
//                                load(propsFile.inputStream())
//                            }
//                        }
//
//                        storeFile = file(keystoreProps["KEYSTORE_FILE"] as String)
//                        storePassword = keystoreProps["KEYSTORE_PASSWORD"] as String
//                        keyAlias = keystoreProps["KEY_ALIAS"] as String
//                        keyPassword = keystoreProps["KEY_PASSWORD"] as String
                    }
                }

                buildTypes {
                    getByName("release") {
                        isMinifyEnabled = false
                        signingConfig = signingConfigs.getByName("release")
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }
            }
        }
    }
}
