package com.celeste.proyecto.di

import com.celeste.proyecto.auth.data.datasource.AuthRemoteDataSource
import com.celeste.proyecto.auth.data.datasource.FirebaseAuthDataSource
import com.celeste.proyecto.core.storage.AndroidSettingsStorage
import com.celeste.proyecto.core.storage.SettingsStorage
import com.celeste.proyecto.notes.data.datasource.FirebaseNotesDataSource
import com.celeste.proyecto.notes.data.datasource.NotesRemoteDataSource
import com.celeste.proyecto.remoteconfig.data.datasource.FirebaseRemoteConfigDataSource
import com.celeste.proyecto.remoteconfig.data.datasource.RemoteConfigDataSource
import com.celeste.proyecto.scheduler.SyncScheduler
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<SettingsStorage> { AndroidSettingsStorage(androidContext()) }
    single<RemoteConfigDataSource> { FirebaseRemoteConfigDataSource(androidContext()) }
    single<NotesRemoteDataSource> { FirebaseNotesDataSource(androidContext()) }
    single<AuthRemoteDataSource> { FirebaseAuthDataSource(androidContext()) }
    single { SyncScheduler(androidContext()) }
}
