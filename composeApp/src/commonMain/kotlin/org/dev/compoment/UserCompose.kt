package org.dev.compoment

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.dev.compoment.bean.*
import org.dev.compoment.task.TaskManager
import org.dev.http.ServerType
import org.dev.http.bean.getItem.Item
import org.dev.http.bean.getRoleList.GetRoleListSuccessResponse
import org.dev.http.getRoleList
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource




object UserCompose
{
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun UserCompose ()
    {
        var usernameInput by remember { mutableStateOf("") }
        var passwordInput by remember { mutableStateOf("") }
        var serverTypeExpand by remember { mutableStateOf(false) }
        var serverType by remember { mutableStateOf(ServerType.TAP_TAP) }
        var canLogin by remember { mutableStateOf(false) }
        var roleExpand by remember { mutableStateOf(false) }
        var canConfirmRole by remember { mutableStateOf(false) }
        var openDialog by remember { mutableStateOf(false) }


        Surface (modifier = Modifier.fillMaxSize(), color = Color (229, 229, 229)){
            Column (horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource("16.png"),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp).clip(Shapes().small)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(User.id)
                Spacer(modifier = Modifier.height(20.dp))
                Text(User.endTime)
                Spacer(modifier = Modifier.height(20.dp))
                Text(User.amount)


                Crossfade(targetState = GameUser.confirmRole, animationSpec = tween(700)) { confirm ->

                    Column (horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center) {

                        if (confirm)
                        {
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(GameUser.role.name)
                            Spacer(modifier = Modifier.height(20.dp))

                            Button (onClick = {
                                GameUser.confirmRole = false
                                GameUser.character = ""
                                GameUser.roleList.clear()
                            },
                                modifier = Modifier.size(120.dp, 50.dp)){
                                Text("重新登陆")
                            }

                        }else
                        {
                            Text("请选择服务器渠道")

                            Spacer(modifier = Modifier.height(20.dp))

                            Button (onClick = {
                                serverTypeExpand = true
                            },
                                modifier = Modifier.size(120.dp, 50.dp)){
                                Text(serverType.serverName)
                            }

                            DropdownMenu(
                                expanded = serverTypeExpand,
                                onDismissRequest = { serverTypeExpand = false },
                                modifier = Modifier.size(100.dp, 150.dp),
                                offset = DpOffset(12.dp,(-262).dp)
                            ) {
                                ServerType.entries.forEach {

                                    DropdownMenuItem(text = {
                                        Text(it.serverName)
                                    } , onClick = {
                                        serverType = it
                                        serverTypeExpand = false
                                    })
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Text("请选择游戏账号")

                            Spacer(modifier = Modifier.height(20.dp))

                            TextField(
                                value = usernameInput,
                                onValueChange = {
                                    usernameInput = it
                                    canLogin = usernameInput.length >= 5

                                },
                                modifier = Modifier.size(120.dp, 55.dp),
                                singleLine = true)
                            if (openDialog) {

                                AlertDialog(
                                    onDismissRequest = {
                                        openDialog = false
                                    },
                                    title = {
                                        Text(text = "登陆错误")
                                    },
                                    text = {
                                       Text(GameUser.loginFailMessage)
                                    },
                                    confirmButton = {
                                        Button(
                                            onClick = {
                                                openDialog = false
                                            }) {
                                            Text("确认")
                                        }
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Text("请选择游戏密码")

                            Spacer(modifier = Modifier.height(20.dp))

                            TextField(
                                value = passwordInput,
                                onValueChange = {
                                    passwordInput = it
                                    canLogin = passwordInput.length >= 5
                                },
                                modifier = Modifier.size(120.dp, 55.dp),
                                singleLine = true)

                            Spacer(modifier = Modifier.height(20.dp))

                            Button (onClick = {
                                GameUser.coroutineScope.launch {
                                    GameUser.login(usernameInput, passwordInput, serverType)
                                    openDialog = !GameUser.isLogin
                                }
                            },
                                enabled = canLogin,
                                modifier = Modifier.size(120.dp, 50.dp)){
                                Text("登陆账号")
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Text("请选择角色")

                            Spacer(modifier = Modifier.height(20.dp))

                            Button (
                                enabled = GameUser.isLogin,
                                onClick = {
                                roleExpand = true
                                GameUser.coroutineScope.launch {
                                    GameUser.selectRole()
                                }
                            },
                                modifier = Modifier.size(120.dp, 50.dp)){
                                Text(GameUser.role.name)
                            }

                            Spacer(modifier = Modifier.height(20.dp))

                            Button (onClick = {
                                GameUser.character = GameUser.role.id
                                GameUser.confirmRole = true
                            },
                                enabled = canConfirmRole,
                                modifier = Modifier.size(120.dp, 50.dp)){
                                Text("确定角色")
                            }

                            DropdownMenu(
                                expanded = roleExpand,
                                onDismissRequest = {
                                    roleExpand = false
                                    canConfirmRole = GameUser.role.id != ""
                                },
                                modifier = Modifier.size(100.dp, 150.dp),
                                offset = DpOffset(12.dp,(-262).dp)
                            ) {
                                GameUser.roleList.forEach {
                                    DropdownMenuItem(text = {
                                        Text(it.basic.name)
                                    } , onClick = {
                                        GameUser.role = it.basic
                                        canConfirmRole = GameUser.role.id.isNotBlank()
                                        roleExpand = false
                                    })
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))
                        }
                    }
                }
            }
        }
    }
}