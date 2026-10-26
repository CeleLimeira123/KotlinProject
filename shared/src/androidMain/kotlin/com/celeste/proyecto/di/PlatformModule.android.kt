package com.celeste.proyecto.di

import com.celeste.proyecto.auth.data.datasource.AuthRemoteDataSource
import com.celeste.proyecto.auth.data.datasource.FirebaseAuthDataSource
import com.celeste.proyecto.notes.data.datasource.FirebaseNotesDataSource
import com.celeste.proyecto.notes.data.datasource.NotesRemoteDataSource
import com.celeste.proyecto.remoteconfig.data.datasource.FirebaseRemoteConfigDataSource
import com.celeste.proyecto.remoteconfig.data.datasource.RemoteConfigDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<RemoteConfigDataSource> { FirebaseRemoteConfigDataSource(androidContext()) }
    single<NotesRemoteDataSource> { FirebaseNotesDataSource(androidContext()) }
    single<AuthRemoteDataSource> { FirebaseAuthDataSource(androidContext()) }
}
