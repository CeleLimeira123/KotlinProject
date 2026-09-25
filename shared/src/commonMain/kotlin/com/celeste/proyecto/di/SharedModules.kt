package com.celeste.proyecto.di

import com.celeste.proyecto.moviedetail.data.repository.MovieDetailRepositoryImpl
import com.celeste.proyecto.moviedetail.domain.repository.MovieDetailRepository
import com.celeste.proyecto.moviedetail.domain.usecase.GetMovieDetailUseCase
import com.celeste.proyecto.movies.data.repository.MoviesRepositoryImpl
import com.celeste.proyecto.movies.domain.repository.MoviesRepository
import com.celeste.proyecto.movies.domain.usecase.GetMoviesUseCase
import com.celeste.proyecto.profile.data.repository.ProfileRepositoryImpl
import com.celeste.proyecto.profile.domain.repository.ProfileRepository
import com.celeste.proyecto.profile.domain.usecase.GetProfileUseCase
import com.celeste.proyecto.signin.data.repository.SignInRepositoryImpl
import com.celeste.proyecto.signin.domain.repository.SignInRepository
import com.celeste.proyecto.signin.domain.usecase.SignInUseCase
import com.celeste.proyecto.signup.data.repository.SignUpRepositoryImpl
import com.celeste.proyecto.signup.domain.repository.SignUpRepository
import com.celeste.proyecto.signup.domain.usecase.SignUpUseCase
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
}

val domainModule = module {
    factoryOf(::SignInUseCase)
    factoryOf(::SignUpUseCase)
    factoryOf(::GetMoviesUseCase)
    factoryOf(::GetMovieDetailUseCase)
    factoryOf(::GetProfileUseCase)
    factoryOf(::GetUserInformationUseCase)
    factoryOf(::FindGithubAliasUseCase)
}

val appModules = listOf(
    dataModule,
    domainModule,
    presentationModule,
)