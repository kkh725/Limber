plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.multi.module.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.multi.module.hilt)
    alias(libs.plugins.multi.module.network)
}

dependencies {

    implementation(project(":feature:home"))
    implementation(project(":feature:test"))

    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:datastore"))
    implementation(project(":core:network"))
    implementation(project(":core:accessibility"))
    implementation(project(":core:ui"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}