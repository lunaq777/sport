package com.example.sportapp.views

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.sportapp.EventsViewModel
import com.example.sportapp.networking.Event
import com.example.sportapp.networking.SportResponse
import com.example.sportapp.ui.theme.Blue
import com.example.sportapp.ui.theme.Grey
import com.example.sportapp.util.FavouriteEventKeeper
import com.example.sportapp.util.FavouriteEventKeeper.Companion.EVENTS_KEY
import com.example.sportapp.util.FavouriteEventKeeper.Companion.SPORTS_KEY

data class TestText(
    val name: String,
)
@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun EventsView(
    viewModel: EventsViewModel = hiltViewModel(),
    keeper: FavouriteEventKeeper = FavouriteEventKeeper(),
    modifier: Modifier) {
    val context = LocalContext.current
    val sportsList: ArrayList<SportResponse> = ArrayList()

    val activeEvents = remember { mutableStateListOf<Event>()}

    LaunchedEffect(Unit) {
        viewModel.init(context)
    }
    viewModel.errorMessage.observeAsState().value?.let {
        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
    }
    viewModel.eventsData.observeAsState().value?.let {
        sportsList.addAll(it)
    }

    viewModel.isShowProgress.observeAsState().value?.let {
        if (it) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(color = Blue)
            }
        }
    }
    LazyColumn(
        contentPadding = PaddingValues(top = 12.dp)
    ) {
        items(sportsList.size) { index ->
            val sportId = sportsList[index].sportId?: ""
            ExpandableSectionView(
                modifier = modifier.padding(bottom = 24.dp),
                favouriteEventKeeper = keeper,
                title = sportsList[index].sportName?: "",
                sportId = sportId) {
                activeEvents.clear()
                sportsList[index].activeEvents?.let { activeEvents.addAll(it) }

                val favouriteSports = keeper.getSavedSportsOrEvents(context, SPORTS_KEY)
                val favouriteEvents = keeper.getSavedSportsOrEvents(context, EVENTS_KEY)
                if (favouriteSports?.contains(sportId) == true) {
                    val output = activeEvents.filter { event -> favouriteEvents?.any { s -> s.contains(
                        event.eventId.toString()
                    ) } == true}
                    activeEvents.clear()
                    activeEvents.addAll(output)
                }

                if (activeEvents.isNotEmpty()) {
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
                                        EventItemView(favouriteEventKeeper = keeper, event = activeEvents[itemIndex])
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