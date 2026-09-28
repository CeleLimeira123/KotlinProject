package com.celeste.proyecto.core.navigation

sealed class NavRoute(val route: String) {
    data object SignIn : NavRoute("signin")
    data object SignUp : NavRoute("signup")
    data object Catalog : NavRoute("catalog")
    data object Movies : NavRoute("movies")

    data object Crossref : NavRoute("crossref")
    data object MovieDetail : NavRoute("moviedetail/{movieId}") {
        fun createRoute(movieId: String) = "moviedetail/$movieId"
    }
    data object Profile : NavRoute("profile")
    data object UserInformation : NavRoute("userinformation")
}