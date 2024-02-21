package org.dev.compoment

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.dev.compoment.bean.HeartbeatTask
import org.dev.compoment.bean.KeyInfo
import org.dev.compoment.bean.User
import org.dev.http.HeartBeatRequest
import org.dev.http.cami.LoginByCami.loginByCami




object CamiCompose
{
    @Composable
    fun CamiCompose (
        sizeWidth: Dp,
        sizeHeight: Dp,
    )
    {

        val scope = rememberCoroutineScope()
        Surface (modifier = Modifier.fillMaxSize(), color = Color (229, 229, 229)) {
            Column (horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text("验证",
                    style = MaterialTheme.typography.titleLarge,
//                modifier = Modifier.padding(top = 100.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                Card (modifier = Modifier.size(sizeWidth, sizeHeight), colors = CardDefaults.cardColors(containerColor = Color.White)){
                    Text("卡密",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 20.dp, bottom = 10.dp),
                    )

                    Divider(modifier = Modifier.width(250.dp).align(Alignment.CenterHorizontally),thickness = 1.dp, color = Color.Black)

                    Text("请输入卡密 获取根据卡密对应的使用权限",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 20.dp),
                    )
                    TextField(KeyInfo.cami,
                        onValueChange = {
                            KeyInfo.cami = it},
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 30.dp).border(1.dp, Color.Black, shape = Shapes ().medium),
                        singleLine = true,
                        colors = TextFieldDefaults.colors(focusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            errorContainerColor = Color.Transparent),
                        isError = true,
                    )
                    Text(KeyInfo.cami,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.align(Alignment.CenterHorizontally).padding(top = 30.dp),
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))

                Button(onClick = {
                    HeartbeatTask.cami = KeyInfo.cami
                    scope.launch {
                        loginByCami (KeyInfo.cami,
                            success = {
                                KeyInfo.checked = true
                                KeyInfo.money = true
                                KeyInfo.card = true
                                KeyInfo.stone = true
                                KeyInfo.feather = true
                                KeyInfo.equip = true
                                KeyInfo.task = true
                                User.id = it.id
                                User.endTime = it.end_time
                                User.amount = it.amount
                                HeartbeatTask.stateCode = it.statecode
                            },
                            fail = {
                                KeyInfo.checked = false
                                KeyInfo.money = false
                                KeyInfo.card = false
                                KeyInfo.stone = false
                                KeyInfo.feather = false
                                KeyInfo.equip = false
                                KeyInfo.task = false
                            })

                        Thread (HeartbeatTask).start()
                    }
                },
                    modifier = Modifier.size (sizeWidth, 50.dp),
                    enabled = true){
                    Text("验证",
                        style = MaterialTheme.typography.titleLarge)
                }
            }
        }
    }

}
