package com.example.greychallenge.mvvm.models

enum class StopState {
    LOCKED,
    IN_PROGRESS,
    COMPLETED
}

enum class ConnectDirection {
    LEFT,
    RIGHT
}

enum class BadgeState {
    EARNED,
    NOT_EARNED
}

data class PathStop(
    val id: Int,
    val title: String,
    val subtitle: String,
    val progressText: String,
    val progress: Float = 0f,
) {
    val state: StopState
        get() = when {
            progress <= 0f -> StopState.LOCKED
            progress >= 1f -> StopState.COMPLETED
            progress > 0f && progress < 1f -> StopState.IN_PROGRESS
            else -> StopState.LOCKED
        }

    val badgeState: BadgeState
        get() = when (state) {
            StopState.COMPLETED -> BadgeState.EARNED
            else -> BadgeState.NOT_EARNED
        }
}