plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.25"
}

android {
    namespace = "com.dag.nexq_app"
    compileSdk = 34
    flavorDimensions += "version"
    testBuildType = "debug"
    productFlavors{
        create("localBuild") {
            dimension = "version"
            applicationIdSuffix = ".lcl"
            versionNameSuffix = "-lcl"

        }
        create("releaseBuild"){
            dimension = "version"
            applicationIdSuffix = ".rls"
            versionNameSuffix = "-rls"
        }
    }
    defaultConfig {
        testInstrumentationRunnerArguments += mapOf()
        applicationId = "com.dag.nexq_app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField(
            "String",
            "WEB_API_KEY",
            "\"${project.findProperty("WEB_API_KEY")}\""
        )
        buildConfigField(
            "String",
            "AD_KEY",
            "\"${project.findProperty("AD_KEY")}\""
        )
        buildConfigField(
            "String",
            "GOOGLE_SEARCH_KEY",
            "\"${project.findProperty("GOOGLE_SEARCH_KEY")}\""
        )
        buildConfigField(
            "String",
            "GOOGLE_API",
            "\"${project.findProperty("GOOGLE_API")}\""
        )
        productFlavors {
            getByName("releaseBuild"){
                buildConfigField(
                    "String",
                    "BASE_URL",
                    "\"${project.findProperty("BASE_URL")}\""
                )
                buildConfigField(
                    "String",
                    "BLOCK_API",
                    "\"${project.findProperty("BLOCK_API")}\""
                )
            }
            getByName("localBuild"){
                buildConfigField(
                    "String",
                    "BASE_URL",
                    "\"http://10.0.2.2:8083\""
                )
                buildConfigField(
                    "String",
                    "BLOCK_API",
                    "\"http://10.0.2.2:3001\""
                )
            }
        }

        testInstrumentationRunner = "com.dag.nexq_app.NexQ_TestRunner"
        testInstrumentationRunnerArguments["testBuildVariant"] = "localBuildDebug"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = false
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
        buildConfig = true
        viewBinding = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    configurations.all {
        resolutionStrategy {
            force("androidx.test.ext:junit:1.1.5")
        }
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.test:runner:1.6.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.4")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.4")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("com.google.android.gms:play-services-location:21.3.0")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    val nav_version = "2.8.3"
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.7.5")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.7.5")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.compose.compiler:compiler:1.5.15")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
    implementation("androidx.activity:activity-compose:1.9.3")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    //Hilt
    implementation("androidx.navigation:navigation-compose:2.8.3")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("com.google.dagger:hilt-android:2.51.1")
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.51.1")
    testImplementation("com.google.dagger:hilt-android-testing:2.51.1")
    kaptTest("com.google.dagger:hilt-android-compiler:2.51.1")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.51.1")
    kapt("com.google.dagger:hilt-android-compiler:2.51.1")
    implementation("androidx.hilt:hilt-work:1.0.0")
    //Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    //Navigation
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("androidx.work:work-runtime-ktx:2.9.1")
    //Multidex
    runtimeOnly("androidx.multidex:multidex:2.0.1")
    implementation("androidx.multidex:multidex-instrumentation:2.0.0")
    //Status bar
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")
    //Datastore
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    //Google Authentication
    implementation("androidx.credentials:credentials:1.3.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")
    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation("com.squareup.okhttp3:okhttp:4.10.0")
    //Solana
    implementation("com.solanamobile:mobile-wallet-adapter-clientlib-ktx:2.0.3")
    //Coroutine test
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.9.0")
    //Google Add
    implementation("com.google.android.gms:play-services-ads:23.6.0")
    //Coroutine
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.9.0")
    //Permission
    implementation("com.google.accompanist:accompanist-permissions:0.34.0")




}