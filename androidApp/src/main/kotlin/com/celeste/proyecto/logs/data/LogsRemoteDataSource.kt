package com.celeste.proyecto.logs.data

import android.content.Context
import android.provider.Settings
import com.celeste.proyecto.logs.data.repository.LogUploader
import com.develoop.logs.LogApi
import com.develoop.logs.LogServiceGrpcKt
import com.google.protobuf.ByteString
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.UUID

/*
 * Configuraciones gRPC soportadas:
 *
 * 1. Emulador sin ngrok (desarrollo local directo):
 *    GRPC_HOST = 10.0.2.2
 *    GRPC_PORT = 9090 (o el puerto configurado del servidor gRPC local)
 *    GRPC_USE_PLAINTEXT = true
 *
 * 2. Con ngrok (túnel seguro HTTPS/TLS):
 *    GRPC_HOST = subdominio.ngrok-free.app (el host del dominio ngrok sin "https://")
 *    GRPC_PORT = 443
 *    GRPC_USE_PLAINTEXT = false
 */
class LogsRemoteDataSource(
    private val context: Context,
    private val host: String,
    private val port: Int,
    private val usePlaintext: Boolean,
) : LogUploader {

    private val instanceId: String by lazy {
        UUID.randomUUID().toString()
    }

    private val channel: ManagedChannel by lazy {
        ManagedChannelBuilder
            .forAddress(host, port)
            .apply {
                if (usePlaintext) {
                    usePlaintext()
                }
            }
            .build()
    }

    private val stub by lazy {
        LogServiceGrpcKt.LogServiceCoroutineStub(channel)
    }

    override suspend fun upload(message: String, stackTrace: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val androidIdStr = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID) ?: "unknown"
            val androidIdBytes = ByteString.copyFromUtf8(androidIdStr)
            val appInstanceBytes = ByteString.copyFromUtf8(instanceId)

            val packageInfo = try {
                context.packageManager.getPackageInfo(context.packageName, 0)
            } catch (_: Exception) {
                null
            }
            @Suppress("DEPRECATION")
            val versionCode = packageInfo?.versionCode ?: 1

            val now = System.currentTimeMillis()

            val logData = LogApi.LogData.newBuilder()
                .setAndroidId(androidIdBytes)
                .setAppInstanceId(appInstanceBytes)
                .setLogLevel(LogApi.ELogLevel.LEVEL_INFO)
                .setMessage(message)
                .setStackTrace(stackTrace)
                .setServerTimeStamp(now)
                .setMobileTimeStamp(now)
                .setVersionCode(versionCode)
                .setUserId("celeste")
                .build()

            val request = LogApi.LogRequest.newBuilder()
                .addLogs(logData)
                .build()

            val response = stub.send(request)
            response.resultCode == LogApi.ELogResultCode.LOG_RESULT_OK
        } catch (e: Exception) {
            false
        }
    }
}
