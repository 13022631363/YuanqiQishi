package org.dev.compoment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import org.dev.compoment.bean.CardChooseInfo
import org.dev.compoment.task.CardTask
import org.dev.compoment.task.TaskManager
import org.dev.http.bean.getItem.Item


object CardCompose{
    @Composable
    fun CardCompose ()
    {
        var cardTypeExpanded by remember { mutableStateOf(false) }
        var cardExpanded by remember { mutableStateOf(false) }
        var cardInput by remember { mutableStateOf("") }
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
                   Text(CardChooseInfo.cardType.name)
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
                            CardChooseInfo.cardType = type
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
                    Text(CardChooseInfo.card.cardName)
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
                    Item.Card.Type.valueOf(CardChooseInfo.cardType.name).cards.forEach {
                        if (it.cardName.contains (cardInput))
                        {
                            DropdownMenuItem(text = {
                                Text(it.cardName)
                            } , onClick = {
                                CardChooseInfo.card = it
                                cardExpanded = false
                            })
                        }
                    }
                }


                Spacer(modifier = Modifier.height(20.dp))

                Text("请输入数量")

                Spacer(modifier = Modifier.height(20.dp))

                TextField(
                    value = CardChooseInfo.amount.toString(),
                    onValueChange = {
                        runCatching {
                            CardChooseInfo.amount = it.toInt ()
                        }.onFailure {
                            CardChooseInfo.amount = 1
                            enable = true
                        }.onSuccess { enable = (CardChooseInfo.amount != 0)}
                    },
                    modifier = Modifier.size(120.dp, 55.dp),
                    singleLine = true
                )



                Spacer(modifier = Modifier.height(20.dp))

                Button (
                    onClick = {
                        TaskManager.addTask(CardTask (amount = CardChooseInfo.amount, card = CardChooseInfo.card))
                    },
                    modifier = Modifier.size(120.dp, 50.dp),
                    enabled = enable){
                    Text("添加任务")
                }



            }


        }
    }
}