package com.celeste.proyecto.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.celeste.proyecto.catalog.presentation.screen.CatalogScreen
import com.celeste.proyecto.catalog.presentation.viewmodel.CatalogViewModel
import com.celeste.proyecto.home.presentation.screen.HomeScreen
import com.celeste.proyecto.notes.presentation.screen.NotesScreen
import com.celeste.proyecto.notes.presentation.viewmodel.NotesViewModel
import com.celeste.proyecto.profile.presentation.screen.ProfileScreen
import com.celeste.proyecto.profile.presentation.state.ProfileEvents
import com.celeste.proyecto.profile.presentation.state.ProfileState
import com.celeste.proyecto.resources.Res
import com.celeste.proyecto.resources.nav_home
import com.celeste.proyecto.resources.nav_movies
import com.celeste.proyecto.resources.nav_notes
import com.celeste.proyecto.resources.nav_profile
import com.celeste.proyecto.userinformation.presentation.state.UserInformationEvents
import com.celeste.proyecto.userinformation.presentation.state.UserInformationState
import org.jetbrains.compose.resources.stringResource

@Composable
fun MainShellScreen(
    catalogViewModel: CatalogViewModel,
    profileState: ProfileState,
    onProfileEvent: (ProfileEvents) -> Unit,
    userInformationState: UserInformationState,
    onUserInformationEvent: (UserInformationEvents) -> Unit,
    notesViewModel: NotesViewModel,
    onMovieClick: (Int) -> Unit,
) {
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                // Tab 0: Inicio
                NavigationBarItem(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    icon = { Text("🏠") },
                    label = { Text(stringResource(Res.string.nav_home), style = MaterialTheme.typography.labelSmall) },
                )

                // Tab 1: Películas
                NavigationBarItem(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    icon = { Text("🎬") },
                    label = { Text(stringResource(Res.string.nav_movies), style = MaterialTheme.typography.labelSmall) },
                )

                // Tab 2: Mi Perfil / Usuario
                NavigationBarItem(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    icon = { Text("👤") },
                    label = { Text(stringResource(Res.string.nav_profile), style = MaterialTheme.typography.labelSmall) },
                )

                // Tab 3: Notas
                NavigationBarItem(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    icon = { Text("📝") },
                    label = { Text(stringResource(Res.string.nav_notes), style = MaterialTheme.typography.labelSmall) },
                )
            }
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (selectedTab) {
                0 -> HomeScreen(
                    catalogViewModel = catalogViewModel,
                    onMovieClick = onMovieClick,
                    onSeeAllMoviesClick = { selectedTab = 1 },
                )
                1 -> CatalogScreen(
                    viewModel = catalogViewModel,
                    onMovieClick = onMovieClick,
                )
                2 -> ProfileScreen(
                    state = profileState,
                    onEvent = onProfileEvent,
                    userInformationState = userInformationState,
                    onUserInformationEvent = onUserInformationEvent,
                )
                3 -> NotesScreen(
                    viewModel = notesViewModel,
                )
            }
        }
    }
}
