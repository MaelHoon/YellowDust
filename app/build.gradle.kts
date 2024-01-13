plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "mael.hoon.yellowdust"
    compileSdk = 34

    buildFeatures{
        buildConfig = true
    }

    defaultConfig {
        applicationId = "mael.hoon.yellowdust"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String","KAKAO_API_KEY", project.properties["KAKAO_API_KEY"].toString())
        buildConfigField("String","AIR_KOREA_SERVICE_KEY", project.properties["AIR_KOREA_SERVICE_KEY"].toString())
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
    viewBinding{
        enable = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-location:18.0.0")
    //API데이터를 받을때는 비동기로 처리되어야 하기 때문에 코루틴 의존성을 추가한다.
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.1")
    //API데이터를 retrofit을 사용하여 처리한다.
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    //retrofit으로 받은 API데이터를 gson으로 변환하여 사용하기 위한 의존성
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //retrofit으로 받은 API데이터의 logging을 위한 의존성
    implementation("com.squareup.okhttp3:logging-interceptor:4.6.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}