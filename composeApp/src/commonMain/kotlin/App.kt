import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import org.dev.http.getRoleList
import org.dev.http.login
import org.dev.http.post
import org.dev.http.publicRevision
import org.jetbrains.compose.resources.ExperimentalResourceApi
import kotlin.coroutines.coroutineContext

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    val scope = rememberCoroutineScope()
    var color by remember { mutableStateOf(Color.White) }
    var loginStatus by remember { mutableStateOf("") }
    MaterialTheme {
        Box (modifier = Modifier.background(if (isSystemInDarkTheme()) Color.Black else Color.Green)
            .fillMaxWidth().fillMaxHeight(0.5f),
            contentAlignment = Alignment.BottomCenter){
            Icon(
                modifier = Modifier.size(120.dp)
                    .clickable {

                        CoroutineScope(Dispatchers.Main).launch {
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
                        }
                },
                imageVector = Icons.Default.Email,
                contentDescription =  null,
                tint = color
            )
            Text (loginStatus, color = Color.White)
        }

    }
}
