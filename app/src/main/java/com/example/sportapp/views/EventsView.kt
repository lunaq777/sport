package com.example.sportapp.views

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapp.EventsViewModel
import com.example.sportapp.networking.SportResponse
import com.example.sportapp.ui.theme.Grey

data class TestText(
    val name: String,
)
@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun EventsView(viewModel: EventsViewModel = hiltViewModel(), modifier: Modifier) {
    val context = LocalContext.current
    val sportsList: ArrayList<SportResponse> = ArrayList()

    LaunchedEffect(Unit) {
        viewModel.init(context)
    }
    viewModel.errorMessage.observeAsState().value?.let {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }
    viewModel.data.observeAsState().value?.let {
        sportsList.addAll(it)
    }
    LazyColumn(
        contentPadding = PaddingValues(top = 12.dp)
    ) {
        items(sportsList.size) { index ->
            ExpandableSectionView(modifier = modifier.padding(bottom = 24.dp), title = sportsList[index].sportName?: "") {
                val activeEvents = sportsList[index].activeEvents
                if (activeEvents?.isNotEmpty() == true) {
                    val size = activeEvents.size
                    val x = size / 4
                    val amountLeft = size % 4
                    val col = if (x * 4 < size) x + 1 else x
                    val isAddColumn = col * 4 > size
                    var itemIndex = -1
                    Column {
                        for (i in 0 until col) {
                            val case = if (isAddColumn && i == col - 1) amountLeft else 4
                            Row {
                                for (j in 0 until case) {
                                    itemIndex++
                                    Box(
                                        modifier = Modifier
                                            .scale(1f)
                                            .background(Grey)
                                    ) {
                                        EventItemView(activeEvents[itemIndex])
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}