package org.dev.compoment.task

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import org.dev.compoment.bean.RunState
import org.dev.compoment.bean.TaskInfo
import org.dev.http.GetItemRequest
import org.dev.http.bean.getItem.Item

data class FeatherTask(
    val feather: Item.Feather,
    override val taskName: String = "羽毛任务",
    override val amount: Int,
    override val taskDetail: String = "数量: $amount\n 详细: ${feather.name}",
    override var state: MutableState<RunState> = mutableStateOf(RunState.Wait),
    override var message: MutableState<String> = mutableStateOf(""),
    override var openDialog: MutableState<Boolean> = mutableStateOf(false)
): TaskInfo {

    override suspend fun run() {
        var totalAmount = 0
        state.value = RunState.Running
        GetItemRequest.getItemByType(amount,
            feather,
            1000L,
            success = {response, uuids ->
                totalAmount += uuids.size
                message.value = "成功获取 $totalAmount [${feather.name}]"
            },
            fail = {
                state.value = RunState.Error
                message.value = it.msg
            })

        if (state.value != RunState.Error)
            state.value = RunState.Finish
    }
}