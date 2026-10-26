# Celeste App — Kotlin Multiplatform

**Celeste** es una aplicación moderna de entretenimiento desarrollada con **Kotlin Multiplatform (KMP)** y **Compose Multiplatform**, dirigida a plataformas **Android** e **iOS**.

---

## 📱 Características Principales

1. **Dashboard de Inicio (Inicio)**:
   - Saludo dinámico y mensaje de bienvenida mediante **Firebase Remote Config**.
   - Muestra las **5 películas más destacadas** del día consumiendo la API de **The Movie Database (TMDB)**.
   - Categorías destacadas y banners promocionales administrables remotamente.

2. **Catálogo Completo de Películas (Películas)**:
   - Listado completo de películas consumiendo TMDB.
   - Detalle de cada película con calificación y sinopsis.

3. **Autenticación e Identidad Real (Mi Perfil)**:
   - Inicio de sesión y Registro de usuarios con **Firebase Authentication** (Correo / Contraseña).
   - Perfil de usuario con UID real, nombre y correo electrónico.
   - Búsqueda de usuarios GitHub consumiendo la API de GitHub vía Ktor Client.
   - Cierre de sesión seguro.

4. **Notas y Reseñas en Tiempo Real (Notas)**:
   - Sincronización instantánea con **Firebase Realtime Database**.
   - Aislamiento total de datos por usuario (`/notes/$uid/$noteKey`).
   - Creación, lectura en flujo continuo (`Flow`) y eliminación de notas.

5. **Notificaciones Push y Locales**:
   - Soporte para **Firebase Cloud Messaging (FCM)** y notificaciones locales.
   - Solicitud dinámica de permisos en tiempo de ejecución (`POST_NOTIFICATIONS`) en Android 13+ (API 33+).
   - Canal de notificación configurado automáticamente en Android 8+.

6. **Internacionalización Dinámica (i18n)**:
   - Soporte para **Español** e **Inglés** mediante **Compose Multiplatform Resources**.
   - Selector de idioma en tiempo real dentro de Perfil con preferencia persistente.

7. **Sistema de Diseño & Temas Accesibles**:
   - Soporte para **Tema del Sistema, Claro, Oscuro y Alto Contraste**.
   - Control de **Escala de Fuente** (Pequeña, Normal, Grande, Extra Grande).
   - Componentes compartidos M3 y tokens de accesibilidad.

8. **Sincronización en Segundo Plano & Logs gRPC**:
   - Tarea periódica de refresco en segundo plano con **Android WorkManager** (`SyncWorker`).
   - Esquema gRPC (`LogApi.proto`) y cargador de logs (`GrpcLogUploader`).

---

## 🚀 Requisitos y Configuración de Archivos Locales

Para ejecutar el proyecto en tu entorno local, asegúrate de configurar los siguientes dos archivos (ambos protegidos en `.gitignore` para no subir secretos al repositorio):

### 1. `androidApp/google-services.json`
Descarga tu archivo `google-services.json` desde Firebase Console (Proyecto **`celeste12a`**) y colócalo en la carpeta `androidApp/`:

```text
KotlinProject/
└── androidApp/
    └── google-services.json
```

### 2. `local.properties`
En la raíz del proyecto, crea o edita el archivo `local.properties` agregando las siguientes claves:

```properties
sdk.dir=C\:\\Users\\TU_USUARIO\\AppData\\Local\\Android\\Sdk
TMDB_API_KEY=tu_tmdb_api_key_aqui
GRPC_HOST=10.0.2.2
GRPC_PORT=50051
```

---

## 🏃 Cómo Ejecutar la Aplicación

### Desde Android Studio
1. Selecciona la configuración de ejecución **`androidApp`** en la barra superior.
2. Selecciona tu variante preferida (`dev` o `prod`).
3. Presiona **Run ▶**.

### Desde Línea de Comandos (Gradle)
- Compilar variante de desarrollo:
  ```bash
  ./gradlew androidApp:assembleDevDebug
  ```
- Compilar variante de producción:
  ```bash
  ./gradlew androidApp:assembleProdDebug
  ```
- Ejecutar pruebas unitarias compartidas:
  ```bash
  ./gradlew shared:testAndroidHostTest
  ```

---

## 🧪 Usuario de Prueba y Verificación Manual

### Datos para Login / Registro
- **Correo de prueba**: `usuario@celeste.com`
- **Contraseña**: `123456`

### Pasos para Probar Notificaciones Push (FCM)
1. Inicia la aplicación en un dispositivo con Android 13+.
2. Acepta el permiso del sistema cuando la app solicite **Permitir notificaciones**.
3. Ve a **Firebase Console > Participación > Messaging**.
4. Haz clic en **Nueva campaña > Mensaje de Firebase Notification**.
5. Ingresa un título y cuerpo de mensaje y presiona **Enviar mensaje de prueba**.

---

## 🛠️ Arquitectura y Tecnologías
- **Languaje**: Kotlin 2.4.20
- **Build System**: AGP 9.1.1, Gradle 8.x
- **UI Framework**: Compose Multiplatform 1.12.1
- **Inyección de Dependencias**: Koin 4.0.2
- **Backend Services**: Firebase Auth, Realtime Database, Remote Config, FCM
- **Red / HTTP**: Ktor 3.4.1 + Coil 3.5.0
- **Segundo Plano**: Android WorkManager 2.11.0
