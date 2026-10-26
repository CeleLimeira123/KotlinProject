import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

val localProps = Properties()
val localFile = rootProject.file("local.properties")
if (localFile.exists()) {
    localProps.load(localFile.inputStream())
}
val tmdbApiKey = localProps.getProperty("TMDB_API_KEY") ?: ""
val grpcHost = localProps.getProperty("GRPC_HOST") ?: "10.0.2.2"
val grpcPort = localProps.getProperty("GRPC_PORT") ?: "50051"

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)          // NUEVO: NotificationCompat
    implementation(libs.firebase.messaging)         // NUEVO: notificaciones push (FCM)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.koinAndroid)
    implementation(libs.compose.ui)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.uiToolingPreview)
    debugImplementation(libs.compose.uiTooling)
}

android {
    namespace = "com.celeste.proyecto"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.celeste.proyecto"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        buildConfigField("String", "GLOBAL_PROVIDER", "\"CalyrSoft\"")
        buildConfigField("String", "TMDB_API_KEY", "\"$tmdbApiKey\"")
        buildConfigField("String", "GRPC_HOST", "\"$grpcHost\"")
        buildConfigField("String", "GRPC_PORT", "\"$grpcPort\"")
    }

    flavorDimensions += "environment"

    productFlavors {
        create("dev") {
            dimension = "environment"
            val appName = "\"app-dev\""
            val baseUrl = "\"https://dev.api.calyrsoft.com\""
            applicationIdSuffix = ".dev"

            buildConfigField("String", "APP_NAME", appName)
            buildConfigField("String", "BASE_URL", baseUrl)
        }

        create("prod") {
            dimension = "environment"
            val appName = "\"app-prod\""
            applicationIdSuffix = ""

            buildConfigField("String", "APP_NAME", appName)
            buildConfigField("String", "BASE_URL", "\"http://prod.api.calyrsoft.com\"")
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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

    buildFeatures {
        compose = true
        buildConfig = true
    }
}

// Firebase: el plugin lee androidApp/google-services.json (archivo local, no se sube a GitHub).
if (file("google-services.json").exists()) {
    apply(plugin = "com.google.gms.google-services")
}