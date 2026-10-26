package com.celeste.proyecto.scheduler

expect class SyncScheduler {
    fun schedulePeriodicalSync()
    fun syncNow()
}
