package com.example.guessing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.guessing.ui.screens.GameViewModel
import com.example.guessing.ui.screens.HomeScreen
import com.example.guessing.ui.theme.GuessingTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: GameViewModel = viewModel<GameViewModel>()
            var isDarkMode by remember {
                mutableStateOf(false)
            }
            val switchModes = { isDarkMode = !isDarkMode }
            GuessingTheme(darkTheme = isDarkMode) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            isDarkMode = isDarkMode, switchModes
                        )
                    }
                ) {
                    HomeScreen(it, viewModel = viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    isDarkMode: Boolean,
    onToggleDarkMode: () -> Unit
) {
    TopAppBar(
        title = { Text(text = "Cringing Game") },
        navigationIcon = {
            IconButton(onClick = onToggleDarkMode) {
                Icon(
                    painter = if (isSystemInDarkTheme())
                        painterResource(R.drawable.outline_dark_mode_24)
                    else painterResource(R.drawable.outline_light_mode_24),
                    contentDescription = null
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth(),
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}
