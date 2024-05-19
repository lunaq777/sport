package com.example.sportapp.views

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportapp.R
import com.example.sportapp.networking.Event
import com.example.sportapp.ui.theme.Grey
import com.example.sportapp.ui.theme.GreyLighter
import com.example.sportapp.ui.theme.Orange
import com.example.sportapp.ui.theme.White
import com.example.sportapp.ui.theme.Yellow
import com.example.sportapp.util.FavouriteEventKeeper
import com.example.sportapp.util.FavouriteEventKeeper.Companion.EVENTS_KEY
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit


@Composable
fun EventItemView(
    favouriteEventKeeper: FavouriteEventKeeper,
    event: Event
) {
    val context = LocalContext.current
    val events = favouriteEventKeeper.getSavedSportsOrEvents(context, EVENTS_KEY)
    val checkedState = remember { mutableStateOf(events?.contains("${event.eventId}-${event.eventSportId}")) }

    val firstTeam = event.eventName?.substringBefore("-")?: ""
    val secondTeam = event.eventName?.substringAfter("-")?: ""
    val time = event.eventStartTime?: 0

    val timerConverter = remember {
        mutableStateOf("")
    }
    val lastConnection = remember {
        mutableLongStateOf(System.currentTimeMillis())
    }

    //TODO Need improvement!
    LaunchedEffect(key1 = timerConverter.value) {
        delay(1000)
        timerConverter.value =
            converting(1000 * 60 * 60 * 24L - (System.currentTimeMillis() - lastConnection.longValue))
    }

    Column(
        modifier = Modifier
            .background(color = Grey)
            .padding(start = 16.dp, end = 16.dp, top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = timerConverter.value,
            maxLines = 1,
            textAlign = TextAlign.Center,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 10.sp, color = White),
        )

        IconToggleButton(
            checked = checkedState.value?: false,
            onCheckedChange = {
                event.eventId?.let { id ->
                    favouriteEventKeeper.saveSportOrEvent(
                        context,
                        EVENTS_KEY,
                        "$id-${event.eventSportId?:""}",
                        it
                    ) }
                checkedState.value = it
            }
        ) {
            val icon = if (checkedState.value == true) R.drawable.ic_star_white else R.drawable.ic_star_grey
            val color = if (checkedState.value == true) Yellow else GreyLighter

            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = color,
                modifier = Modifier
                    .size(32.dp),
            )
        }

        Text(
            modifier = Modifier.width(60.dp),
            text = firstTeam,
            maxLines = 2,
            textAlign = TextAlign.Center,
            style = TextStyle(fontWeight = FontWeight.Normal, fontSize = 10.sp, color = White),
        )
        Text(
            text = stringResource(id = R.string.vs),
            textAlign = TextAlign.Center,
            style = TextStyle(fontWeight = FontWeight.Normal, fontSize = 8.sp, color = Orange),
        )
        Text(
            modifier = Modifier.width(60.dp),
            text = secondTeam,
            maxLines = 2,
            textAlign = TextAlign.Center,
            style = TextStyle(fontWeight = FontWeight.Normal, fontSize = 10.sp, color = White),
        )

    }
}

@SuppressLint("DefaultLocale")
fun converting(millis: Long): String =
    String.format(
        "%02d : %02d : %02d",
        TimeUnit.MILLISECONDS.toHours(millis),
        TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(
            TimeUnit.MILLISECONDS.toHours(millis)
        ),
        TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(
            TimeUnit.MILLISECONDS.toMinutes(millis)
        )
    )