package com.example.greychallenge.helpers.data_helpers

import com.example.greychallenge.mvvm.models.PathStop
import com.example.greychallenge.mvvm.models.StopState

val fallbackStops = mutableListOf<PathStop>(
    PathStop(
        id = 0,
        title = "Programming Basics",
        subtitle = "Solid Principles",
        progressText = "Understanding Solid principles",
        progress = 1f
    ),
    PathStop(
        id = 1,
        title = "Git and Version Control",
        subtitle = "git pull and git merge",
        progressText = "Create a git repository",
        progress = 1f
    ),
    PathStop(
        id = 2,
        title = "Learn React",
        subtitle = "Component lifecycle",
        progressText = "Build a login screen in react",
        progress = 1f
    ),
    PathStop(
        id = 3,
        title = "Core Mobile UI Build",
        subtitle = "Jetpack Compose",
        progressText = "Convert Figma to Jetpack compose",
        progress = 1f
    ),
    PathStop(
        id = 4,
        title = "Access Device Features",
        subtitle = "Camera and Audio",
        progressText = "Access device camera and audio",
        progress = 1f
    ),
    PathStop(
        id = 5,
        title = "Navigation And Forms",
        subtitle = "Navigation Graphs",
        progressText = "Construct a navigation graph",
        progress = 0.3f
    ),
    PathStop(
        id = 6,
        title = "Backend Architecture",
        subtitle = "Ktor",
        progressText = "Introduction to Ktor",
        progress = 0f
    ),
    PathStop(
        id = 7,
        title = "Node.js and Express",
        subtitle = "Backend Architecture",
        progressText = "Introduction to Node.js",
        progress = 0f
    ),
    PathStop(
        id = 8,
        title = "Authentication and Authorization",
        subtitle = "JWAuth and OAuth2",
        progressText = "Introduction to OAuth2",
        progress = 0f
    ),
    PathStop(
        id = 9,
        title = "Write and run tests",
        subtitle = "Unit tests",
        progressText = "Understanding how to write unit tests",
        progress = 0f
    ),
    PathStop(
        id = 10,
        title = "Publish your mobile app",
        subtitle = "Android Playstore",
        progressText = "Signing your app",
        progress = 0f
    )
)