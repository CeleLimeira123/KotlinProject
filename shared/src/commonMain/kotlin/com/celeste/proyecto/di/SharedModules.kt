package com.celeste.proyecto.di

import com.celeste.proyecto.auth.domain.usecase.SignOutUseCase
import com.celeste.proyecto.catalog.data.datasource.CatalogRemoteDataSource
import com.celeste.proyecto.catalog.data.repository.CatalogRepositoryImpl
import com.celeste.proyecto.catalog.data.service.CatalogService
import com.celeste.proyecto.catalog.domain.repository.CatalogRepository
import com.celeste.proyecto.catalog.domain.usecase.GetCatalogMoviesUseCase
import com.celeste.proyecto.core.localization.LanguageManager
import com.celeste.proyecto.core.theme.ThemeManager
import com.celeste.proyecto.logs.data.repository.LogRepositoryImpl
import com.celeste.proyecto.logs.domain.repository.LogRepository
import com.celeste.proyecto.moviedetail.data.repository.MovieDetailRepositoryImpl
import com.celeste.proyecto.moviedetail.domain.repository.MovieDetailRepository
import com.celeste.proyecto.moviedetail.domain.usecase.GetMovieDetailUseCase
import com.celeste.proyecto.movies.data.repository.MoviesRepositoryImpl
import com.celeste.proyecto.movies.domain.repository.MoviesRepository
import com.celeste.proyecto.movies.domain.usecase.GetMoviesUseCase
import com.celeste.proyecto.notes.data.repository.NotesRepositoryImpl
import com.celeste.proyecto.notes.domain.repository.NotesRepository
import com.celeste.proyecto.notes.domain.usecase.AddNoteUseCase
import com.celeste.proyecto.notes.domain.usecase.DeleteNoteUseCase
import com.celeste.proyecto.notes.domain.usecase.ObserveNotesUseCase
import com.celeste.proyecto.profile.data.repository.ProfileRepositoryImpl
import com.celeste.proyecto.profile.domain.repository.ProfileRepository
import com.celeste.proyecto.profile.domain.usecase.GetProfileUseCase
import com.celeste.proyecto.signin.data.repository.SignInRepositoryImpl
import com.celeste.proyecto.signin.domain.repository.SignInRepository
import com.celeste.proyecto.signin.domain.usecase.SignInUseCase
import com.celeste.proyecto.signup.data.repository.SignUpRepositoryImpl
import com.celeste.proyecto.signup.domain.repository.SignUpRepository
import com.celeste.proyecto.signup.domain.usecase.SignUpUseCase
import com.celeste.proyecto.remoteconfig.data.repository.AppConfigRepositoryImpl
import com.celeste.proyecto.remoteconfig.domain.repository.AppConfigRepository
import com.celeste.proyecto.remoteconfig.domain.usecase.GetAppConfigUseCase
import com.celeste.proyecto.userinformation.data.datasource.GithubRemoteDataSource
import com.celeste.proyecto.userinformation.data.repository.GithubRepositoryImpl
import com.celeste.proyecto.userinformation.data.repository.UserInformationRepositoryImpl
import com.celeste.proyecto.userinformation.data.service.GitHubApiService
import com.celeste.proyecto.userinformation.domain.repository.GithubRepository
import com.celeste.proyecto.userinformation.domain.repository.UserInformationRepository
import com.celeste.proyecto.userinformation.domain.usecase.FindGithubAliasUseCase
import com.celeste.proyecto.userinformation.domain.usecase.GetUserInformationUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    singleOf(::SignInRepositoryImpl) bind SignInRepository::class
    singleOf(::SignUpRepositoryImpl) bind SignUpRepository::class
    singleOf(::MoviesRepositoryImpl) bind MoviesRepository::class
    singleOf(::MovieDetailRepositoryImpl) bind MovieDetailRepository::class
    singleOf(::ProfileRepositoryImpl) bind ProfileRepository::class
    singleOf(::UserInformationRepositoryImpl) bind UserInformationRepository::class
    single<GithubRemoteDataSource> { GitHubApiService() }
    singleOf(::GithubRepositoryImpl) bind GithubRepository::class
    single { CatalogService() }
    single { CatalogRemoteDataSource(get()) }
    single<CatalogRepository> { CatalogRepositoryImpl(get()) }
    singleOf(::AppConfigRepositoryImpl) bind AppConfigRepository::class
    singleOf(::NotesRepositoryImpl) bind NotesRepository::class
    singleOf(::LanguageManager)
    singleOf(::ThemeManager)
    singleOf(::LogRepositoryImpl) bind LogRepository::class
}

val domainModule = module {
    factoryOf(::SignInUseCase)
    factoryOf(::SignUpUseCase)
    factoryOf(::SignOutUseCase)
    factoryOf(::GetMoviesUseCase)
    factoryOf(::GetMovieDetailUseCase)
    factoryOf(::GetProfileUseCase)
    factoryOf(::GetUserInformationUseCase)
    factoryOf(::FindGithubAliasUseCase)
    factoryOf(::GetCatalogMoviesUseCase)
    factoryOf(::GetAppConfigUseCase)
    factoryOf(::ObserveNotesUseCase)
    factoryOf(::AddNoteUseCase)
    factoryOf(::DeleteNoteUseCase)
}

val appModules = listOf(
    platformModule(),
    dataModule,
    domainModule,
    presentationModule,
)