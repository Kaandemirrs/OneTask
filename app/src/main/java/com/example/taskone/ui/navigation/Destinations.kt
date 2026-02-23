package com.example.taskone.ui.navigation

sealed class AppDestination(val route: String) {
    data object Onboarding : AppDestination("onboarding")
    data object OnboardingGoal : AppDestination("onboarding_goal")
    data object OnboardingSummary : AppDestination("onboarding_summary")
    data object OnboardingTopics : AppDestination("onboarding_topics")
    data object Home : AppDestination("home")
}
