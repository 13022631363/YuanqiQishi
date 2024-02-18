package org.dev.compoment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.dev.compoment.bean.MoneyTask
import org.dev.compoment.task.TaskManager
import org.dev.http.CastCoinsType

object MoneyCompose{
    @Composable
    fun MoneyCompose (){
        Surface (modifier = Modifier.fillMaxSize(), color = Color (229, 229, 229) ) {

            Box (modifier = Modifier.fillMaxSize ().background(Color.White), contentAlignment = Alignment.Center) {
                Column (horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {

                    Text("金币", style = MaterialTheme.typography.titleLarge)

                    Spacer(modifier = Modifier.height(20.dp))

                    Button (modifier = Modifier.size(150.dp, 50.dp),
                        onClick = {
                            TaskManager.addTask(MoneyTask (1, CastCoinsType.CastCoins10w))
                        }
                    ){
                        Text("10W",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button (modifier = Modifier.size(150.dp, 50.dp),
                        onClick = {
                            TaskManager.addTask(MoneyTask (1, CastCoinsType.CastCoins100w))
                        }
                    ){
                        Text("100W",
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Text("* 按一次代表向任务列表添加一次此任务",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                }
            }

        }
    }
}