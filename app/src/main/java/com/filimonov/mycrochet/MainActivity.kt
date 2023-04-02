package com.filimonov.mycrochet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.filimonov.mycrochet.ui.screens.project.ProjectScreen
import com.filimonov.mycrochet.ui.screens.projects.ProjectsScreen
import com.filimonov.mycrochet.ui.theme.MyCrochetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyCrochetTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ProjectsScreen()
                }
            }
        }
    }
}
