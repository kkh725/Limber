plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.multi.module.library)
    alias(libs.plugins.multi.module.network)
    alias(libs.plugins.multi.module.hilt)
}

android{
    namespace = "com.kkh.multimodule.accessibility"
}

dependencies {

    implementation(project(":core:domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit.jupiter)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}