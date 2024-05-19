package com.example.sportapp.views

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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


@Composable
fun EventItemView(event: Event) {
    val checkedState = remember { mutableStateOf(false) }

    val firstTeam = event.eventName?.substringBefore("-")?: ""
    val secondTeam = event.eventName?.substringAfter("-")?: ""
    val time = event.eventStartTime?: 0

    Column(
        modifier = Modifier
            .background(color = Grey)
            .padding(start = 16.dp, end = 16.dp, top = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "$time",
            maxLines = 1,
            textAlign = TextAlign.Center,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 10.sp, color = White),
        )

        IconToggleButton(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = !checkedState.value
            }
        ) {
            val icon = if (checkedState.value) R.drawable.ic_star_white else R.drawable.ic_star_grey
            val color = if (checkedState.value) Yellow else GreyLighter

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