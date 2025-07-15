plugins {
    alias(libs.plugins.multi.module.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.multi.module.hilt)
    alias(libs.plugins.multi.module.network)
}

android {
    namespace = "com.kkh.multimodule.data"
}

dependencies {

    api(project(":core:datastore")) // core 모듈 내의 dataStore 모듈을 의존성으로 추가
    api(project(":core:network"))   // core 모듈 내의 network 모듈을 의존성으로 추가
    implementation(project(":core:domain"))
//    implementation(project(":core:accessibility"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}