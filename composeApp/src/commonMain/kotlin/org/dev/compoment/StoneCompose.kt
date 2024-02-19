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
import org.dev.compoment.task.StoneTask
import org.dev.compoment.task.TaskManager
import org.dev.http.bean.getItem.Item

object StoneCompose {
    @Composable
    fun StoneCompose ()
    {
        var stoneType by remember { mutableStateOf(Item.Stone.Type.UpgradeStone) }
        var stoneTypeExpanded by remember { mutableStateOf(false) }
        var amount by remember { mutableStateOf(1) }
        var enable by remember { mutableStateOf(false) }

        Box(modifier = Modifier.fillMaxSize().background(Color.White),
            contentAlignment = Alignment.Center){
            Column (horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text("石头", style = MaterialTheme.typography.titleLarge)

                Spacer(modifier = Modifier.height(20.dp))

                Text("请选择石头类型")

                Spacer(modifier = Modifier.height(20.dp))

                Button (onClick = {
                    stoneTypeExpanded = true
                },
                    modifier = Modifier.size(120.dp, 50.dp)){
                    Text(stoneType.chineseName)
                }

                DropdownMenu(
                    expanded = stoneTypeExpanded,
                    onDismissRequest = { stoneTypeExpanded = false },
                    modifier = Modifier.size(100.dp, 150.dp),
                    offset = DpOffset(12.dp,(-375).dp)
                ) {
                    Item.Stone.Type.entries.forEach { type  ->
                        DropdownMenuItem(text = {
                            Text(type.chineseName)
                        } , onClick = {
                            stoneType = type
                            stoneTypeExpanded = false
                        })
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
                        }.onSuccess { enable = (amount != 0) }
                    },
                    modifier = Modifier.size(120.dp, 55.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button (onClick = {
                    TaskManager.addTask(StoneTask (amount = amount, stoneType = stoneType))
                },
                    modifier = Modifier.size(120.dp, 50.dp),
                    enabled = enable){
                    Text("添加任务")
                }
            }
        }
    }
}