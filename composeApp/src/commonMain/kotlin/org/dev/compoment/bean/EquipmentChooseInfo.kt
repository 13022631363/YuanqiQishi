package org.dev.compoment.bean

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import org.dev.http.SceneAreaType
import org.dev.http.SceneEnterLevelType
import org.dev.http.bean.sceneEnter.SceneEnterResponse

object EquipmentChooseInfo {
    var sceneAreaType by mutableStateOf(SceneAreaType.NAN_BU_CAO_YUAN)
    var sceneEnterLevelType by mutableStateOf(SceneEnterLevelType.Boss)
    var amount by  mutableStateOf(1)
    var suitBuff by  mutableStateOf(SceneEnterResponse.EquipmentPools.EquipmentInfo.Buff.emptyBuff)
    var buff0 by mutableStateOf(SceneEnterResponse.EquipmentPools.EquipmentInfo.Buff.emptyBuff)
    var buff1 by  mutableStateOf(SceneEnterResponse.EquipmentPools.EquipmentInfo.Buff.emptyBuff)
    var quality by  mutableStateOf("")
}