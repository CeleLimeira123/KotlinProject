package com.celeste.proyecto.di

import com.celeste.proyecto.auth.data.datasource.AuthRemoteDataSource
import com.celeste.proyecto.auth.data.datasource.InMemoryAuthDataSource
import com.celeste.proyecto.core.storage.IosSettingsStorage
import com.celeste.proyecto.core.storage.SettingsStorage
import com.celeste.proyecto.logs.data.repository.IosLogUploader
import com.celeste.proyecto.logs.data.repository.LogUploader
import com.celeste.proyecto.notes.data.datasource.InMemoryNotesDataSource
import com.celeste.proyecto.notes.data.datasource.NotesRemoteDataSource
import com.celeste.proyecto.remoteconfig.data.datasource.DefaultsRemoteConfigDataSource
import com.celeste.proyecto.remoteconfig.data.datasource.RemoteConfigDataSource
import com.celeste.proyecto.scheduler.SyncScheduler
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<SettingsStorage> { IosSettingsStorage() }
    single<RemoteConfigDataSource> { DefaultsRemoteConfigDataSource() }
    single<NotesRemoteDataSource> { InMemoryNotesDataSource() }
    single<AuthRemoteDataSource> { InMemoryAuthDataSource() }
    single<LogUploader> { IosLogUploader() }
    single(named("TMDB_API_KEY")) { "" }
    single { SyncScheduler() }
}
