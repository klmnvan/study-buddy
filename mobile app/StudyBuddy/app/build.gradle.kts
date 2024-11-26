plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("org.jetbrains.kotlin.plugin.serialization") version "1.8.20"
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.studybuddy"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.studybuddy"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    kapt {
        correctErrorTypes = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.protolite.well.known.types)
    implementation(libs.core)
    testImplementation(libs.junit)
    testImplementation(libs.hilt.android.testing)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // навигация
    val nav_version = "2.7.7"
    implementation("androidx.navigation:navigation-compose:$nav_version")

    // ktor client
    val ktor_version = "2.3.10"
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-android:$ktor_version")
    implementation("io.ktor:ktor-client-logging:$ktor_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("ch.qos.logback:logback-classic:1.2.3")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")

    implementation("eu.bambooapps:compose-material3-pullrefresh:1.0.0")

    // hilt
    val hilt_android_version = "2.48"
    implementation("com.google.dagger:hilt-android:$hilt_android_version")
    kapt("com.google.dagger:hilt-compiler:$hilt_android_version")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    //тестирование
    //androidTestImplementation("org.robolectric:robolectric:4.14")
    val compose_version = "1.7.5"
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")
    // For instrumented tests.
    androidTestImplementation("com.google.dagger:hilt-android-testing:$hilt_android_version")
    // ...with Kotlin.
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.51.1")

    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")

    // coil
    implementation("io.coil-kt:coil-compose:2.2.2")
    implementation("io.coil-kt:coil-svg:2.2.2")

    // coroutines
    // implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")

    //уведомления
    implementation("com.google.accompanist:accompanist-permissions:0.31.1-alpha")

    //Room
    val room_version = "2.4.0"
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation ("androidx.room:room-ktx:$room_version")
    implementation(libs.hilt.navigation.compose)

    //jwt token decode
    implementation ("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.12.6")
    implementation ("com.auth0.android:jwtdecode:2.0.2")
    runtimeOnly ("io.jsonwebtoken:jjwt-gson:0.12.6")

}