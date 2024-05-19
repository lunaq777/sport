package com.example.sportapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sportapp.ui.theme.Blue
import com.example.sportapp.ui.theme.Grey
import com.example.sportapp.ui.theme.SportAppTheme
import com.example.sportapp.ui.theme.White
import com.example.sportapp.views.EventsView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SportAppTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = getString(R.string.app_name))
                            },
                            backgroundColor = Blue,
                            contentColor = White,
                            elevation = 12.dp
                        )
                    }, content = {
                        EventsView(
                            modifier = Modifier.padding(it)
                    )
                    }, backgroundColor = Grey)
            }
        }
    }
}