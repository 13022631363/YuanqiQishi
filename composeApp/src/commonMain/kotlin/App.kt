import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import org.dev.http.*
import org.jetbrains.compose.resources.ExperimentalResourceApi
import kotlin.coroutines.coroutineContext

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    val scope = rememberCoroutineScope()
    var color by remember { mutableStateOf(Color.Black) }
    var loginStatus by remember { mutableStateOf("") }
    MaterialTheme {
        Box (modifier = Modifier.background(if (isSystemInDarkTheme()) Color.Black else Color.White)
            .fillMaxSize(),
            contentAlignment = Alignment.Center){
            Icon(
                modifier = Modifier.size(120.dp)
                    .clickable {

                        CoroutineScope(Dispatchers.Default).launch {
                            login(success = {
                                println("成功")

                            }, fail = {
                                println("失败...")
                            })

                            getRoleList(success =  {response ->
                                response.characters.forEach {
                                    println("${it.basic.id} ${it.basic.name}")
                                }
                            }, fail = {
                                println("2314123")
                                println(it.message)

                            })

                            getRoleInfo(success = {
                                println(it.revision)
                            }, fail = {
                                println(it.message)
                                println(it.errorCode)
                                println(it.ok)
                                println(authorization)
                            } )
                        }
                },
                imageVector = Icons.Default.Email,
                contentDescription =  null,
                tint = color
            )

            Icon(
                modifier = Modifier.size(120.dp).padding(top = 100.dp)
                    .clickable {
                            //刷金币
//                        CoroutineScope(Dispatchers.Default).launch {
//                            castCoins(CastCoinsType.CastCoins10w) {
//                                println(it.gold)
//                            }
//                        }
                          CoroutineScope (Dispatchers.Main).launch {
                              sceneEnter(SceneEnterLevelType.Common, SceneAreaType.LU_WEI_SHI_DI,
                                  success = {
                                      it
                                  },
                                  fail = ::println)
                          }
                    },
                imageVector = Icons.Default.Add,
                contentDescription =  null,
                tint = color
            )

        }

    }
}
