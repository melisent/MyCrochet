package com.filimonov.mycrochet

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.filimonov.mycrochet.ui.screens.project.ProjectScreen
import com.filimonov.mycrochet.ui.screens.projects.ProjectsScreen

private const val PROJECTS_SCREEN_PATH = "project_screen"
private const val PROJECT_DETAILS_SCREEN_PATH = "project_details_screen/"
private const val PROJECT_DETAILS_SCREEN_ARGUMENT_PROJECT_ID = "projectId"

@Composable
fun MyCrochetNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = PROJECTS_SCREEN_PATH) {
        composable(PROJECTS_SCREEN_PATH) {
            ProjectsScreen(navController = navController)
        }

        composable(
            route = "$PROJECT_DETAILS_SCREEN_PATH{$PROJECT_DETAILS_SCREEN_ARGUMENT_PROJECT_ID}",
            arguments = listOf(navArgument(PROJECT_DETAILS_SCREEN_ARGUMENT_PROJECT_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val projectId = backStackEntry.arguments?.getInt(PROJECT_DETAILS_SCREEN_ARGUMENT_PROJECT_ID) ?: 0
            ProjectScreen(projectId = projectId, navController = navController)
        }
    }
}

fun NavHostController.navigateToProjectDetails(projectId: Int) {
    navigate(PROJECT_DETAILS_SCREEN_PATH + projectId)
}
