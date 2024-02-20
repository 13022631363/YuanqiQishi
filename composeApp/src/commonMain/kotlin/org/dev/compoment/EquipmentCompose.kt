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
import kotlinx.coroutines.launch
import org.dev.compoment.bean.EquipmentChooseInfo
import org.dev.compoment.bean.GameUser
import org.dev.compoment.task.EquipTask
import org.dev.compoment.task.TaskManager
import org.dev.http.*
import org.dev.http.bean.getItem.Item
import org.dev.http.bean.sceneEnter.SceneEnterResponse

object EquipmentCompose
{
    @Composable
    fun EquipmentCompose ()
    {
        var sceneAreaTypeExpanded by remember { mutableStateOf(false) }
        var sceneEnterLevelTypeExpanded by remember { mutableStateOf(false) }
        var suitBuffExpanded by remember { mutableStateOf(false) }
        var buff0Expanded by remember { mutableStateOf(false) }
        var buff1Expanded by remember { mutableStateOf(false) }
        var suitBuffInput by remember { mutableStateOf("") }
        var buff0BuffInput by remember { mutableStateOf("") }
        var buff1BuffInput by remember { mutableStateOf("") }
        var enable by  remember { mutableStateOf(false) }

        var difficultyTypeExpanded by remember { mutableStateOf(false) }
        var difficultyType by remember { mutableStateOf(Difficulty.Common) }

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
                        Text(EquipmentChooseInfo.sceneAreaType.areaName, color = Color.White)
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
                                EquipmentChooseInfo.sceneAreaType = type
                                sceneAreaTypeExpanded = false
                            })
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }

                item {
                    Text("请选择难度")

                    Spacer(modifier = Modifier.height(20.dp))

                    Button (onClick = {
                        difficultyTypeExpanded = true
                    },
                        modifier = Modifier.size(120.dp, 50.dp)
                    ){
                        Text(difficultyType.chineseName, color = Color.White)
                    }

                    DropdownMenu(
                        expanded = difficultyTypeExpanded,
                        onDismissRequest = { difficultyTypeExpanded = false },
                        modifier = Modifier.size(100.dp, 150.dp),
                        offset = DpOffset(12.dp,(-375).dp)
                    ) {
                        Difficulty.entries.forEach { type  ->
                            DropdownMenuItem(text = {
                                Text(type.chineseName)
                            } , onClick = {
                                difficultyType = type
                                difficultyTypeExpanded = false
                            })
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button (onClick = {
                        GameUser.coroutineScope.launch {
                            GameDifficultyRequest.setDifficulty(difficultyType.value)
                        }
                    },
                        modifier = Modifier.size(120.dp, 50.dp)
                    ){
                        Text("确定", color = Color.White)
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
                        Text(EquipmentChooseInfo.sceneEnterLevelType.name, color = Color.White)
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
                                EquipmentChooseInfo.sceneEnterLevelType = type
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
                        Text(EquipmentChooseInfo.suitBuff.chineseName, color = Color.White)
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
                        SceneEnterResponse.EquipmentPools.EquipmentInfo.BuffType.Epic.buffs.forEach { buff  ->
                            if (buff.chineseName.contains(suitBuffInput))
                            {
                                DropdownMenuItem(text = {
                                    Text(buff.chineseName)
                                } , onClick = {
                                    EquipmentChooseInfo.suitBuff = buff
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
                        Text(EquipmentChooseInfo.buff0.chineseName, color = Color.White)
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
                        SceneEnterResponse.EquipmentPools.EquipmentInfo.BuffType.Common.buffs.forEach { buff  ->
                            if (buff.chineseName.contains(buff0BuffInput))
                            {
                                DropdownMenuItem(text = {
                                    Text(buff.chineseName)
                                } , onClick = {
                                    EquipmentChooseInfo.buff0 = buff
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
                        Text(EquipmentChooseInfo.buff1.chineseName, color = Color.White)
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
                        SceneEnterResponse.EquipmentPools.EquipmentInfo.BuffType.Common.buffs.forEach { buff  ->
                            if (buff.chineseName.contains(buff1BuffInput))
                            {
                                DropdownMenuItem(text = {
                                    Text(buff.chineseName)
                                } , onClick = {
                                    EquipmentChooseInfo.buff1 = buff
                                    buff1Expanded = false
                                })
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                }

                item {

                    Text("请输入品质")

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        value = EquipmentChooseInfo.quality,
                        onValueChange = {
                            runCatching {
                                EquipmentChooseInfo.quality = it.toInt ().toString()
                            }.onFailure {
                                EquipmentChooseInfo.amount = 1
                                enable = true
                            }.onSuccess {
                                enable = (EquipmentChooseInfo.amount != 0)
                            }
                        },
                        modifier = Modifier.size(120.dp, 55.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text("请输入数量")

                    Spacer(modifier = Modifier.height(20.dp))

                    TextField(
                        value = EquipmentChooseInfo.amount.toString(),
                        onValueChange = {
                            runCatching {
                                EquipmentChooseInfo.amount = it.toInt ()
                            }.onFailure {
                                EquipmentChooseInfo.amount = 1
                                enable = true
                            }.onSuccess {
                                enable = (EquipmentChooseInfo.amount != 0)
                            }
                        },
                        modifier = Modifier.size(120.dp, 55.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button (onClick = {
                        TaskManager.addTask(EquipTask (amount = EquipmentChooseInfo.amount,
                            conditions = SceneEnterResponse.EquipmentPools.EquipmentInfo.EquipMatchConditions(equipBuffSuit = EquipmentChooseInfo.suitBuff,
                                equipBuff0 = EquipmentChooseInfo.buff0,
                                equipBuff1 = EquipmentChooseInfo.buff1,
                                quality = EquipmentChooseInfo.quality),
                            sceneAreaType = EquipmentChooseInfo.sceneAreaType,
                            sceneEnterLevelType = EquipmentChooseInfo.sceneEnterLevelType))
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