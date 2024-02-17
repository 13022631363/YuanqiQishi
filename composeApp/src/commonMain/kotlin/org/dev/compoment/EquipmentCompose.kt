package org.dev.compoment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import org.dev.http.SceneAreaType
import org.dev.http.SceneEnterLevelType
import org.dev.http.bean.getItem.Item
import org.dev.http.bean.sceneEnter.SceneEnterResponse

object EquipmentCompose
{
    @Composable
    fun EquipmentCompose ()
    {
        var sceneAreaType by remember { mutableStateOf(SceneAreaType.NAN_BU_CAO_YUAN) }
        var sceneAreaTypeExpanded by remember { mutableStateOf(false) }
        var sceneEnterLevelType by remember { mutableStateOf(SceneEnterLevelType.Boss) }
        var sceneEnterLevelTypeExpanded by remember { mutableStateOf(false) }
        var amount by remember { mutableStateOf(1) }
        var enable by remember { mutableStateOf(false)}
        var suitBuff by remember { mutableStateOf(SceneEnterResponse.EquipmentPools.EquipmentInfo.Buff ("", "")) }
        var buff0 by remember { mutableStateOf(SceneEnterResponse.EquipmentPools.EquipmentInfo.Buff ("", "")) }
        var buff1 by remember { mutableStateOf(SceneEnterResponse.EquipmentPools.EquipmentInfo.Buff ("", "")) }
        var suitBuffExpanded by remember { mutableStateOf(false) }
        var buff0Expanded by remember { mutableStateOf(false) }
        var buff1Expanded by remember { mutableStateOf(false) }
        var suitBuffInput by remember { mutableStateOf("") }
        var buff0BuffInput by remember { mutableStateOf("") }
        var buff1BuffInput by remember { mutableStateOf("") }

        Box(modifier = Modifier.fillMaxSize().background(Color.White),
            contentAlignment = Alignment.Center){
            LazyColumn (horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                item {
                    Text("装备", style = MaterialTheme.typography.titleLarge)

                    Spacer(modifier = Modifier.height(20.dp))
                }

                item {
                    Text("请选择地图")

                    Spacer(modifier = Modifier.height(20.dp))

                    Button (onClick = {
                        sceneAreaTypeExpanded = true
                    },
                        modifier = Modifier.size(120.dp, 50.dp)
                    ){
                        Text(sceneAreaType.areaName, color = Color.White)
                    }

                    DropdownMenu(
                        expanded = sceneAreaTypeExpanded,
                        onDismissRequest = { sceneAreaTypeExpanded = false },
                        modifier = Modifier.size(100.dp, 150.dp),
                        offset = DpOffset(12.dp,(-375).dp)
                    ) {
                        SceneAreaType.entries.forEach { type  ->
                            DropdownMenuItem(text = {
                                Text(type.areaName)
                            } , onClick = {
                                sceneAreaType = type
                                sceneAreaTypeExpanded = false
                            })
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }

                item {
                    Text("请选择地图难度")

                    Spacer(modifier = Modifier.height(20.dp))

                    Button (onClick = {
                        sceneEnterLevelTypeExpanded = true
                    },
                        modifier = Modifier.size(120.dp, 50.dp)
                    ){
                        Text(sceneEnterLevelType.name, color = Color.White)
                    }

                    DropdownMenu(
                        expanded = sceneEnterLevelTypeExpanded,
                        onDismissRequest = { sceneEnterLevelTypeExpanded = false },
                        modifier = Modifier.size(100.dp, 150.dp),
                        offset = DpOffset(12.dp,(-375).dp)
                    ) {
                        SceneEnterLevelType.entries.forEach { type  ->
                            DropdownMenuItem(text = {
                                Text(type.name)
                            } , onClick = {
                                sceneEnterLevelType = type
                                sceneEnterLevelTypeExpanded = false
                            })
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }

                item {
                    Text("请选择 boss 羁绊")

                    Spacer(modifier = Modifier.height(20.dp))

                    Button (onClick = {
                        suitBuffExpanded = true
                    },
                        modifier = Modifier.size(120.dp, 50.dp)
                    ){
                        Text(suitBuff.chineseName, color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        value = suitBuffInput,
                        onValueChange = {
                            suitBuffInput = it
                        },
                        modifier = Modifier.size(120.dp, 55.dp),
                        singleLine = true)

                    DropdownMenu(
                        expanded = suitBuffExpanded,
                        onDismissRequest = { suitBuffExpanded = false },
                        modifier = Modifier.size(100.dp, 150.dp),
                        offset = DpOffset(12.dp,(-375).dp)
                    ) {
                        SceneEnterResponse.EquipmentPools.EquipmentInfo.BuffType.entries[0].buffs.forEach { buff  ->
                            if (buff.chineseName.contains(suitBuffInput))
                            {
                                DropdownMenuItem(text = {
                                    Text(buff.chineseName)
                                } , onClick = {
                                    suitBuff = buff
                                    suitBuffExpanded = false
                                })
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }

                item {
                    Text("请选择 1 羁绊")

                    Spacer(modifier = Modifier.height(20.dp))

                    Button (onClick = {
                        buff0Expanded = true
                    },
                        modifier = Modifier.size(120.dp, 50.dp)
                    ){
                        Text(buff0.chineseName, color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        value = buff0BuffInput,
                        onValueChange = {
                            buff0BuffInput = it
                        },
                        modifier = Modifier.size(120.dp, 55.dp),
                        singleLine = true)

                    DropdownMenu(
                        expanded = buff0Expanded,
                        onDismissRequest = { buff0Expanded = false },
                        modifier = Modifier.size(100.dp, 150.dp),
                        offset = DpOffset(12.dp,(-375).dp)
                    ) {
                        SceneEnterResponse.EquipmentPools.EquipmentInfo.BuffType.entries[1].buffs.forEach { buff  ->
                            if (buff.chineseName.contains(buff0BuffInput))
                            {
                                DropdownMenuItem(text = {
                                    Text(buff.chineseName)
                                } , onClick = {
                                    buff0 = buff
                                    buff0Expanded = false
                                })
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }

                item {
                    Text("请选择 2 羁绊")

                    Spacer(modifier = Modifier.height(20.dp))

                    Button (onClick = {
                        buff1Expanded = true
                    },
                        modifier = Modifier.size(120.dp, 50.dp)
                    ){
                        Text(buff1.chineseName, color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        value = buff1BuffInput,
                        onValueChange = {
                            buff1BuffInput = it
                        },
                        modifier = Modifier.size(120.dp, 55.dp),
                        singleLine = true)

                    DropdownMenu(
                        expanded = buff1Expanded,
                        onDismissRequest = { buff1Expanded = false },
                        modifier = Modifier.size(100.dp, 150.dp),
                        offset = DpOffset(12.dp,(-375).dp)
                    ) {
                        SceneEnterResponse.EquipmentPools.EquipmentInfo.BuffType.entries[1].buffs.forEach { buff  ->
                            if (buff.chineseName.contains(buff1BuffInput))
                            {
                                DropdownMenuItem(text = {
                                    Text(buff.chineseName)
                                } , onClick = {
                                    buff1 = buff
                                    buff1Expanded = false
                                })
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }

                item {
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
                            }.onSuccess {
                                enable = (amount != 0)
                            }
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
}