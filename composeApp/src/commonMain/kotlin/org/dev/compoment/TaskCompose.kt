package org.dev.compoment

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.dev.compoment.bean.RunState.*
import org.dev.compoment.task.TaskManager
import org.jetbrains.compose.resources.ExperimentalResourceApi

object TaskCompose
{
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun TaskCompose ()
    {

        Box(modifier = Modifier.fillMaxSize().background(Color.White),
            contentAlignment = Alignment.Center){
            LazyColumn (horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                item {
                    Text("任务", style = MaterialTheme.typography.titleLarge)

                    Spacer(modifier = Modifier.height(20.dp))
                }

                item {
                    if (TaskManager.displayTaskSize != 0)
                    {
                        TaskManager.displayTask.forEach{
                            Card(modifier = Modifier.size(350.dp, 50.dp).clickable {
                                it.openDialog.value = true
                            },
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                border = BorderStroke(1.dp, Color.Black),
                            )
                            {
                                Row {
                                    Box (modifier = Modifier.fillMaxHeight().fillMaxWidth(.8f),
                                        contentAlignment = Alignment.CenterStart
                                    ){
                                        Row {
                                            val picturePath = when (it.state.value)
                                            {
                                                Running -> "loding.png"
                                                Finish -> "success.png"
                                                Error -> "error.png"
                                                Wait -> "wait.png"
                                            }
                                            Image(
                                                painter = org.jetbrains.compose.resources.painterResource(picturePath),
                                                contentDescription = null,
                                                modifier = Modifier.size(40.dp).padding(start = 20.dp)
                                            )

                                            Spacer(modifier = Modifier.width (20.dp))

                                            Text(
                                                it.taskName,
                                                modifier = Modifier.align(Alignment.CenterVertically),
                                                overflow = TextOverflow.Ellipsis)
                                        }
                                    }

                                    if (it.openDialog.value) {

                                        AlertDialog(
                                            onDismissRequest = {
                                                it.openDialog.value = false
                                            },
                                            title = {
                                                Text(text = "任务详情")
                                            },
                                            text = {
                                                LazyColumn {
                                                    item {
                                                        Text(text ="任务览概", style = MaterialTheme.typography.titleLarge)
                                                        Spacer(modifier = Modifier.height(5.dp))
                                                        Text(it.taskDetail)
                                                        Spacer(modifier = Modifier.height(20.dp))
                                                        Text(text ="任务结果", style = MaterialTheme.typography.titleLarge)
                                                        Text(it.message.value)
                                                    }
                                                }
                                            },
                                            confirmButton = {
                                                Button(
                                                    onClick = {
                                                        it.openDialog.value = false
                                                    }) {
                                                    Text("确认")
                                                }
                                            },
                                            dismissButton = {
                                                Button(
                                                    onClick = {
                                                        TaskManager.releaseTask(it)
                                                        it.openDialog.value = false
                                                    },
                                                    enabled = it.state.value != Running,) {
                                                    Text("确认删除")
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }


                }

                item {


                    Button (onClick = {
                        TaskManager.startTask()
                    },
                        enabled = !TaskManager.taskSizeIsZero,
                        modifier = Modifier.size(120.dp, 50.dp)){
                        Text("开始任务")
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                }


            }
        }
    }
}