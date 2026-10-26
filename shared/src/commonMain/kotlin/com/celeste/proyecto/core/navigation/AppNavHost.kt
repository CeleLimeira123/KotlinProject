package com.celeste.proyecto.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.celeste.proyecto.catalog.presentation.viewmodel.CatalogViewModel
import com.celeste.proyecto.main.MainShellScreen
import com.celeste.proyecto.moviedetail.presentation.effects.MovieDetailEffects
import com.celeste.proyecto.moviedetail.presentation.screen.MovieDetailScreen
import com.celeste.proyecto.moviedetail.presentation.state.MovieDetailEvents
import com.celeste.proyecto.moviedetail.presentation.state.MovieDetailVM
import com.celeste.proyecto.notes.presentation.viewmodel.NotesViewModel
import com.celeste.proyecto.profile.presentation.effects.ProfileEffects
import com.celeste.proyecto.profile.presentation.state.ProfileVM
import com.celeste.proyecto.signin.presentation.screen.SignInScreen
import com.celeste.proyecto.signin.presentation.state.SignInVM
import com.celeste.proyecto.signup.presentation.effects.SignUpEffects
import com.celeste.proyecto.signup.presentation.screen.SignUpScreen
import com.celeste.proyecto.signup.presentation.state.SignUpVM
import com.celeste.proyecto.userinformation.presentation.state.UserInformationVM
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: String = NavRoute.SignIn.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(NavRoute.SignIn.route) {
            val viewModel: SignInVM = koinViewModel()
            val state by viewModel.uiState.collectAsState()

            SignInScreen(
                state = state,
                effect = viewModel.effect,
                onEvent = viewModel::onEvent,
                onNavigateHome = {
                    navController.navigate(NavRoute.Catalog.route) {
                        popUpTo(NavRoute.SignIn.route) { inclusive = true }
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(NavRoute.SignUp.route)
                },
            )
        }

        composable(NavRoute.SignUp.route) {
            val viewModel: SignUpVM = koinViewModel()
            val state by viewModel.uiState.collectAsState()

            LaunchedEffect(Unit) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        is SignUpEffects.NavigateToHome -> {
                            navController.navigate(NavRoute.Catalog.route) {
                                popUpTo(NavRoute.SignIn.route) { inclusive = true }
                            }
                        }
                        is SignUpEffects.ShowToast -> {}
                    }
                }
            }

            SignUpScreen(
                state = state,
                onEvent = viewModel::onEvent,
                onNavigateToSignIn = {
                    navController.popBackStack()
                },
            )
        }

        // Pantalla Principal (Shell con Bottom Navigation de 4 secciones: Inicio, Películas, Perfil, Notas)
        composable(NavRoute.Catalog.route) {
            val catalogViewModel: CatalogViewModel = koinViewModel()
            val profileViewModel: ProfileVM = koinViewModel()
            val profileState by profileViewModel.uiState.collectAsState()

            val userInformationViewModel: UserInformationVM = koinViewModel()
            val userInformationState by userInformationViewModel.uiState.collectAsState()

            val notesViewModel: NotesViewModel = koinViewModel()

            LaunchedEffect(Unit) {
                profileViewModel.effect.collect { effect ->
                    when (effect) {
                        is ProfileEffects.NavigateToSignIn -> {
                            navController.navigate(NavRoute.SignIn.route) {
                                popUpTo(0) { inclusive = true }
                            }
                        }
                        else -> {}
                    }
                }
            }

            MainShellScreen(
                catalogViewModel = catalogViewModel,
                profileState = profileState,
                onProfileEvent = profileViewModel::onEvent,
                userInformationState = userInformationState,
                onUserInformationEvent = userInformationViewModel::onEvent,
                notesViewModel = notesViewModel,
                onMovieClick = { movieId ->
                    navController.navigate(NavRoute.MovieDetail.createRoute(movieId.toString()))
                },
            )
        }

        composable(NavRoute.MovieDetail.route) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId") ?: ""
            val viewModel: MovieDetailVM = koinViewModel()
            val state by viewModel.uiState.collectAsState()

            LaunchedEffect(movieId) {
                viewModel.onEvent(MovieDetailEvents.LoadMovieDetail(movieId))
            }

            LaunchedEffect(Unit) {
                viewModel.effect.collect { effect ->
                    when (effect) {
                        is MovieDetailEffects.NavigateBack -> {
                            navController.popBackStack()
                        }
                    }
                }
            }

            MovieDetailScreen(
                state = state,
                onEvent = viewModel::onEvent,
            )
        }
    }
}
