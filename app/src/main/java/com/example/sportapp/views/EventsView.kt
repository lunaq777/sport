package com.example.sportapp.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.example.sportapp.ui.theme.Grey

data class TestText(
    val name: String,
)
@Composable
fun EventsView(modifier: Modifier) {
    val list = listOf(TestText("Tenis"), TestText("FootBall"), TestText("Golf"))
    val list2 = listOf(
        TestText("Tenis"), TestText("FootBall"), TestText("Golf"), TestText("Golf"),
        TestText("Golf"), TestText("FootBall"))
    LazyColumn(
        contentPadding = PaddingValues(top = 12.dp)
    ) {
        items(list.size) { index ->
            ExpandableSectionView(modifier = modifier.padding(bottom = 24.dp), title = list[index].name) {
                val x = list2.size / 4
                val amountLeft = list2.size % 4
                val col = if (x*4 < list2.size) x + 1 else x
                val isAddColumn = col*4 > list2.size
                Column {
                    for (i in 0 until col) {
                        val case = if (isAddColumn && i == col - 1) amountLeft else 4
                        Row {
                            for (j in 0 until case) {
                                Box(
                                    modifier = Modifier
                                        .scale(1f)
                                        .background(Grey)
                                ) {
                                    EventItemView()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}