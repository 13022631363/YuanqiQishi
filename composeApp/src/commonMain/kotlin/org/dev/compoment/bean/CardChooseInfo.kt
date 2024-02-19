package org.dev.compoment.bean

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.dev.http.bean.getItem.Item

object CardChooseInfo
{
    var amount by mutableStateOf(0)
    var cardType by mutableStateOf(Item.Card.Type.Boss)
    var card by mutableStateOf(Item.Card ("野猪王", "E01_B02"))
}
