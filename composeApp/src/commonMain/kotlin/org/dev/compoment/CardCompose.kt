package org.dev.compoment

import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.runBlocking
import org.dev.http.bean.getItem.Item

object CardCompose{
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun CardCompose ()
    {
        var cardTypeExpanded by remember { mutableStateOf(false) }
        var cardExpanded by remember { mutableStateOf(false) }
        var cardType by remember { mutableStateOf("Monster") }
        var card by remember { mutableStateOf("野猪王") }
        var cardInput by remember { mutableStateOf("野猪王") }
        var amount by remember { mutableStateOf(1) }
        var enable by remember { mutableStateOf(false) }

        Box(modifier = Modifier.fillMaxSize().background(Color.White),
            contentAlignment = Alignment.Center){
            Column (horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text("卡片", style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(20.dp))

                Text("请选择卡片类型")

                Spacer(modifier = Modifier.height(20.dp))

                Button (onClick = {
                    cardTypeExpanded = true
                },
                    modifier = Modifier.size(120.dp, 50.dp)){
                   Text(cardType)
                }



                DropdownMenu(
                    expanded = cardTypeExpanded,
                    onDismissRequest = { cardTypeExpanded = false },
                    modifier = Modifier.size(100.dp, 150.dp),
                    offset = DpOffset(12.dp,(-375).dp)
                ) {
                    Item.Card.Type.entries.forEach { type  ->
                        DropdownMenuItem(text = {
                                Text(type.name)
                        } , onClick = {
                            cardType = type.name
                            cardTypeExpanded = false
                        })
                    }
                }


                Spacer(modifier = Modifier.height(20.dp))

                Text("请选择具体卡片")

                Spacer(modifier = Modifier.height(20.dp))

                Button (onClick = {
                    cardExpanded = true
                },
                    modifier = Modifier.size(120.dp, 50.dp)){
                    Text(card)
                }

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = cardInput,
                    onValueChange = {
                        cardInput = it
                    },
                    modifier = Modifier.size(120.dp, 55.dp),
                    singleLine = true)


                DropdownMenu(
                    expanded = cardExpanded,
                    onDismissRequest = { cardExpanded = false },
                    modifier = Modifier.size(100.dp, 150.dp),
                    offset = DpOffset(12.dp,(-262).dp)
                ) {
                    Item.Card.Type.valueOf(cardType).cards.forEach {
                        if (it.cardName.contains (cardInput))
                        {
                            DropdownMenuItem(text = {
                                Text(it.cardName)
                            } , onClick = {
                                card = it.cardName
                                cardExpanded = false
                            })
                        }
                    }
                }


                Spacer(modifier = Modifier.height(20.dp))

                Text("请输入数量")

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = amount.toString(),
                    onValueChange = {
                        runCatching {
                            amount = it.toInt ()
                        }.onFailure {
                            amount = 1
                            enable = true
                        }.onSuccess { enable = (amount != 0)}
                    },
                    modifier = Modifier.size(120.dp, 55.dp),
                    singleLine = true
                )



                Spacer(modifier = Modifier.height(20.dp))

                Button (onClick = {

                },
                    modifier = Modifier.size(120.dp, 50.dp),
                    enabled = enable){
                    Text("添加任务")
                }



            }


        }
    }
}