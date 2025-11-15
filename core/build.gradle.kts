plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt)
}


android {
    namespace = "com.tech.core"
    compileSdk = 36


    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    kotlin {
        jvmToolchain(11)
    }
}


dependencies {
    // Coroutines
    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // DataStore (si lo quieres en core)
    implementation(libs.datastore.preferences)

    // Hilt (para inject DispatchersProvider)
    implementation(libs.hilt.android)
    implementation(libs.play.services.tasks)
    ksp(libs.hilt.compiler)
}