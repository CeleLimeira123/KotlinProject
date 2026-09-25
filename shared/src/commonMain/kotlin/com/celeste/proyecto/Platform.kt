package com.celeste.proyecto

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform