package com.example.sportapp.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sportapp.R
import com.example.sportapp.ui.theme.Black
import com.example.sportapp.ui.theme.Blue
import com.example.sportapp.ui.theme.Grey
import com.example.sportapp.ui.theme.GreyLighter
import com.example.sportapp.ui.theme.GreySmoke
import com.example.sportapp.ui.theme.White

@Composable
fun ExpandableSectionView(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var isChecked by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = modifier
            .background(color = White)
            .fillMaxWidth()
    ) {
        ExpandableSectionTitle(isExpanded = isExpanded, isChecked = isChecked, title = title, onExpandCollapse = {
            isExpanded = !isExpanded
        }, onCheckedChange = {
            isChecked = it
        })

        AnimatedVisibility(
            modifier = Modifier
                .background(Grey)
                .fillMaxWidth(),
            visible = isExpanded
        ) {
            content()
        }
    }
}

@Composable
private fun ExpandableSectionTitle(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    isChecked: Boolean,
    title: String,
    onExpandCollapse: () -> Unit,
    onCheckedChange: ((Boolean) -> Unit)
) {
    val icon = if (isExpanded) R.drawable.ic_arrow_up else  R.drawable.ic_arrow_down

    Row(
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = Modifier.size(32.dp),
            painter = painterResource(id = R.drawable.ic_oval_orange),
            contentDescription = null
        )
        Text(
            text = title,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Black)
        )
        Spacer(modifier = Modifier.weight(1f))
        Switch(
            modifier = Modifier.padding(end = 8.dp),
            checked = isChecked,
            onCheckedChange = {
                onCheckedChange(it)
            },
            colors  = SwitchDefaults.colors(
                checkedThumbColor = Blue,
                checkedTrackColor = Grey,
                uncheckedThumbColor = GreyLighter,
                uncheckedTrackColor= GreySmoke,
            ),
            thumbContent =
            {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star_white),
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier
                        .size(SwitchDefaults.IconSize),
                )
            }
        )
        Image(
            modifier = Modifier
                .size(32.dp)
                .clickable { onExpandCollapse() },
            painter = painterResource(id = icon),
            contentDescription = null,
        )
    }
}