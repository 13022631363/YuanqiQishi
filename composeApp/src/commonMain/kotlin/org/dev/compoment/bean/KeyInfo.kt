package org.dev.compoment.bean

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * 卡密验证后的一些条件
 */
data object KeyInfo {
    var cami by  mutableStateOf("")
    var checked by mutableStateOf(false)
    var money by mutableStateOf(false)
    var card by mutableStateOf(false)
    var stone by mutableStateOf(false)
    var equip by mutableStateOf(false)
    var feather by mutableStateOf(false)
}
