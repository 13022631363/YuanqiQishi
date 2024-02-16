package org.dev.compoment

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.*
import org.dev.compoment.Page.*
import org.dev.compoment.bean.KeyInfo
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


enum class Page(val title:String){
    HOME("home"),
    Money("金币"),
    Card("卡片"),
    Stone ("石头"),
    Feather ("羽毛"),
    Equipment ("装备"),
    Task ("任务")
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun NavigationRailSample() {
    var selectedItem by remember { mutableStateOf(0) }
    val pages = Page.entries.toTypedArray()
    Row {
        NavigationRail {
            pages.forEachIndexed { index, item ->
                when(item){
                    HOME -> {
                        NavigationRailItem(
                            icon = {  Image(
                                painter = painterResource("key.png"),
                                contentDescription =  null,
                                modifier = Modifier.size(30.dp),
                            ) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index }
                        )
                    }
                    Money -> {
                        NavigationRailItem(
                            label = { Text(item.title) },
                            icon = { Image(
                                painter = painterResource("jinbi.png"),
                                contentDescription =  null,
                                modifier = Modifier.size(30.dp),
                            ) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index },
                            enabled = KeyInfo.money
                        )
                    }
                    Card -> {
                        NavigationRailItem(
                            label = { Text(item.title) },
                            icon = { Image(
                                painter = painterResource("card.png"),
                                contentDescription =  null,
                                modifier = Modifier.size(30.dp),
                            ) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index },
                            enabled = KeyInfo.card
                        )
                    }
                    Feather -> {
                        NavigationRailItem(
                            label = { Text(item.title) },
                            icon = { Image(
                                painter = painterResource("feather.png"),
                                contentDescription =  null,
                                modifier = Modifier.size(30.dp),
                            ) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index },
                            enabled = KeyInfo.feather
                        )
                    }
                    Stone -> {
                        NavigationRailItem(
                            label = { Text(item.title) },
                            icon = { Image(
                                painter = painterResource("stone.png"),
                                contentDescription =  null,
                                modifier = Modifier.size(30.dp),
                            ) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index },
                            enabled = KeyInfo.stone
                        )
                    }
                    Equipment -> {
                        NavigationRailItem(
                            label = { Text(item.title) },
                            icon = { Image(
                                painter = painterResource("equip.png"),
                                contentDescription =  null,
                                modifier = Modifier.size(30.dp),
                            ) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index },
                            enabled = KeyInfo.equip
                        )
                    }
                    Task -> {
                        NavigationRailItem(
                            label = { Text(item.title) },
                            icon = { Image(
                                painter = painterResource("task.png"),
                                contentDescription =  null,
                                modifier = Modifier.size(30.dp),
                            ) },
                            selected = selectedItem == index,
                            onClick = { selectedItem = index }
                        )
                    }

                }
            }
        }
        Crossfade(targetState = selectedItem, animationSpec = tween(700)) { selectedItem ->
            when (pages[selectedItem])
            {
                HOME -> {
                    Crossfade(targetState = KeyInfo.checked, animationSpec = tween(700)) { checked ->
                        if (checked)
                        {
                            User()
                        }
                        else Cami(300.dp, 200.dp)
                    }
                }
                Money -> {
                    Text("金币")
                }
                Card -> TODO()
                Stone -> TODO()
                Feather -> TODO()
                Equipment -> TODO()
                Task -> TODO()
            }
        }


    }
}


