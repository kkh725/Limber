plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.multi.module.library)
    alias(libs.plugins.multi.module.network)
    alias(libs.plugins.multi.module.hilt)
}

android{
    namespace = "com.kkh.multimodule.network"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
}