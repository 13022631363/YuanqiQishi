package org.dev.compoment.task

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import org.dev.compoment.bean.RunState
import org.dev.compoment.bean.TaskInfo
import org.dev.http.GetItemRequest
import org.dev.http.SceneAreaType
import org.dev.http.SceneEnterLevelType
import org.dev.http.bean.sceneEnter.SceneEnterResponse

data class EquipTask(
    override val taskName: String = "装备任务",
    override val amount: Int,
    val conditions: SceneEnterResponse.EquipmentPools.EquipmentInfo.EquipMatchConditions,
    override var taskDetail: String = "",
    override var message: MutableState<String> = mutableStateOf(""),
    override var state: MutableState<RunState> = mutableStateOf(RunState.Wait),
    val sceneAreaType: SceneAreaType,
    val sceneEnterLevelType: SceneEnterLevelType,
    override var openDialog: MutableState<Boolean> = mutableStateOf(false)
): TaskInfo{

    init {
        val detail = StringBuilder ()
        detail.appendLine("数量: $amount")
        if (conditions.equipBuffSuit != SceneEnterResponse.EquipmentPools.EquipmentInfo.Buff.emptyBuff)
            detail.appendLine("boss羁绊: ${conditions.equipBuffSuit}")
        if (conditions.equipBuff0 != SceneEnterResponse.EquipmentPools.EquipmentInfo.Buff.emptyBuff)
            detail.appendLine("第一羁绊: ${conditions.equipBuff0}")
        if (conditions.equipBuff1 != SceneEnterResponse.EquipmentPools.EquipmentInfo.Buff.emptyBuff)
            detail.appendLine("第二羁绊: ${conditions.equipBuff1}")
        if (conditions.quality != "")
            detail.appendLine("品质: ${conditions.quality}")
        taskDetail = detail.toString()
    }
    override suspend fun run() {
        var total = 0
        state.value = RunState.Running
        GetItemRequest.getEquipment(amount = amount, delay = 1000, conditions = conditions, sceneAreaType = sceneAreaType, sceneEnterLevelType = sceneEnterLevelType,
            success = {_, successNumber, equip ->
                total += successNumber
                message.value = "成功刷出 $total ${equip.itemId} ${equip.id}"
            },
            fail = {
                state.value = RunState.Error
                message.value = it.msg
            })

        if (state.value != RunState.Error)
            state.value = RunState.Finish
    }
}
