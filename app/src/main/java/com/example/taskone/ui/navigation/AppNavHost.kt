package com.example.taskone.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.taskone.ui.screens.home.HomeScreen
import com.example.taskone.ui.screens.onboarding.OnboardingGoalScreen
import com.example.taskone.ui.screens.onboarding.OnboardingReviewScreen
import com.example.taskone.ui.screens.onboarding.OnboardingScreen
import com.example.taskone.ui.screens.onboarding.OnboardingTopicsScreen
import com.example.taskone.ui.screens.onboarding.OnboardingViewModel

@Composable
fun AppNavHost(
    navController: NavHostController = rememberNavController()
) {
    val onboardingViewModel: OnboardingViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = AppDestination.Home.route
    ) {
        composable(route = AppDestination.Home.route) {
            HomeScreen(
                onStartOnboarding = {
                    navController.navigate(AppDestination.Onboarding.route)
                }
            )
        }
        composable(route = AppDestination.Onboarding.route) {
            OnboardingScreen(
                viewModel = onboardingViewModel,
                onContinue = {
                    navController.navigate(AppDestination.OnboardingGoal.route)
                }
            )
        }
        composable(route = AppDestination.OnboardingGoal.route) {
            OnboardingGoalScreen(
                viewModel = onboardingViewModel,
                onBack = { navController.popBackStack() },
                onContinue = {
                    navController.navigate(AppDestination.OnboardingSummary.route)
                }
            )
        }
        composable(route = AppDestination.OnboardingSummary.route) {
            OnboardingReviewScreen(
                viewModel = onboardingViewModel,
                onBack = { navController.popBackStack() },
                onContinue = {
                    navController.navigate(AppDestination.OnboardingTopics.route)
                }
            )
        }
        composable(route = AppDestination.OnboardingTopics.route) {
            OnboardingTopicsScreen(
                viewModel = onboardingViewModel,
                onBack = { navController.popBackStack() },
                onContinue = {
                    onboardingViewModel.setOnboardingCompleted()
                    navController.navigate(AppDestination.Home.route) {
                        popUpTo(AppDestination.Onboarding.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
