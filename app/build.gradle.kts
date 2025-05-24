plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    id("kotlin-parcelize")
}

android {
    namespace = "com.ecoactivity.caica"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ecoactivity.caica"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        //CREDENCIAIS
        val ACCESS_KEY = project.findProperty("AWS_ACCESS_KEY")?.toString() ?: ""
        val SECRET_KEY = project.findProperty("AWS_SECRET_KEY")?.toString() ?: ""

        buildConfigField("String", "AWS_ACCESS_KEY", "\"$ACCESS_KEY\"")
        buildConfigField("String", "AWS_SECRET_KEY", "\"$SECRET_KEY\"")
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // AndroidX Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.constraintlayout)

    // CameraX
    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.extensions)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Retrofit & Networking
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp3.logging.interceptor)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)

    // Glide (usando a versão do catalog)
    implementation(libs.glide)
    kapt(libs.glide.compiler)

    // AWS SDK direto no formato Gradle
    implementation("com.amazonaws:aws-android-sdk-s3:2.62.0")
    implementation("com.amazonaws:aws-android-sdk-core:2.62.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.google.code.gson:gson:2.10.1")


}

