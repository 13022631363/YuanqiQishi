package org.dev.compoment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.dev.compoment.bean.User
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource




object UserComponse
{
    @OptIn(ExperimentalResourceApi::class)
    @Composable
    fun UserComponse ()
    {
        Surface (modifier = Modifier.fillMaxSize(), color = Color (229, 229, 229)){
            Column (horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource("16.png"),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp).clip(Shapes().small)
                )
                Text(User.id)
                Text(User.endTime)
                Text(User.amount)
            }
        }
    }
}