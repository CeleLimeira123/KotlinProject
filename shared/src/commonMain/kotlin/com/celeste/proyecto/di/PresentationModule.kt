package com.celeste.proyecto.di

import com.celeste.proyecto.catalog.presentation.viewmodel.CatalogViewModel
import com.celeste.proyecto.moviedetail.presentation.state.MovieDetailVM
import com.celeste.proyecto.movies.presentation.state.MoviesVM
import com.celeste.proyecto.profile.presentation.state.ProfileVM
import com.celeste.proyecto.signin.presentation.state.SignInVM
import com.celeste.proyecto.signup.presentation.state.SignUpVM
import com.celeste.proyecto.userinformation.presentation.state.UserInformationVM
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {
    viewModelOf(::SignInVM)
    viewModelOf(::SignUpVM)
    viewModelOf(::MoviesVM)
    viewModelOf(::CatalogViewModel)
    viewModelOf(::MovieDetailVM)
    viewModelOf(::ProfileVM)
    viewModelOf(::UserInformationVM)
}