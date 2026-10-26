package com.celeste.proyecto.di

import com.celeste.proyecto.auth.data.datasource.AuthRemoteDataSource
import com.celeste.proyecto.auth.data.datasource.InMemoryAuthDataSource
import com.celeste.proyecto.notes.data.datasource.InMemoryNotesDataSource
import com.celeste.proyecto.notes.data.datasource.NotesRemoteDataSource
import com.celeste.proyecto.remoteconfig.data.datasource.DefaultsRemoteConfigDataSource
import com.celeste.proyecto.remoteconfig.data.datasource.RemoteConfigDataSource
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<RemoteConfigDataSource> { DefaultsRemoteConfigDataSource() }
    single<NotesRemoteDataSource> { InMemoryNotesDataSource() }
    single<AuthRemoteDataSource> { InMemoryAuthDataSource() }
}
