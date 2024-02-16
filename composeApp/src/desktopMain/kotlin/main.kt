import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import org.dev.App

import org.dev.compoment.NavigationRailSample

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "YuanqiQishi",
        state = WindowState(width = 900.dp, height = 900.dp )) {
        MaterialTheme {
//            Cami(650.dp, 350.dp)
            NavigationRailSample()
        }
    }
}

@Preview
@Composable
fun AppDesktopPreview(){
    App()
}

