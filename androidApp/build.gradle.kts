import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties
import com.google.protobuf.gradle.id

val localProps = Properties()
val localFile = rootProject.file("local.properties")
if (localFile.exists()) {
    localProps.load(localFile.inputStream())
}
val tmdbApiKey = localProps.getProperty("TMDB_API_KEY") ?: ""
val grpcHost = localProps.getProperty("GRPC_HOST") ?: "10.0.2.2"
val grpcPort = localProps.getProperty("GRPC_PORT") ?: "9090"
val grpcUsePlaintext = (localProps.getProperty("GRPC_USE_PLAINTEXT") ?: "true").toBoolean()

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.google.protobuf)
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_11
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.firebase.messaging)
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.koinAndroid)

    // gRPC y Protobuf
    implementation(libs.grpc.protobuf.lite)
    implementation(libs.protobuf.kotlin.lite)
    implementation(libs.grpc.kotlin.stub)
    runtimeOnly(libs.grpc.okhttp)

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
        buildConfigField("boolean", "GRPC_USE_PLAINTEXT", "$grpcUsePlaintext")
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

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${libs.versions.protobuf.get()}"
    }
    plugins {
        id("java") {
            artifact = libs.protoc.gen.grpc.java.get().toString()
        }
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${libs.versions.grpc.get()}"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:${libs.versions.grpcKotlin.get()}:jdk8@jar"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                create("java") { option("lite") }
                create("grpc") { option("lite") }
                create("grpckt") { option("lite") }
            }
            it.builtins {
                create("kotlin") { option("lite") }
            }
        }
    }
}

// Firebase: el plugin lee androidApp/google-services.json (archivo local, no se sube a GitHub).
if (file("google-services.json").exists()) {
    apply(plugin = "com.google.gms.google-services")
}
