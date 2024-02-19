package org.dev.compoment.task

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import org.dev.compoment.bean.RunState
import org.dev.compoment.bean.TaskInfo
import org.dev.http.GetItemRequest
import org.dev.http.bean.getItem.Item

data class StoneTask(
    val stoneType: Item.Stone.Type,
    override val taskName: String = "石头任务",
    override val amount: Int,
    override val taskDetail: String = "数量: $amount\n详情: ${stoneType.chineseName}",
    override var state: MutableState<RunState> = mutableStateOf(RunState.Wait),
    override var message: MutableState<String> = mutableStateOf(""),
    override var openDialog: MutableState<Boolean> = mutableStateOf(false)
): TaskInfo{

    override suspend fun run() {
        var totalAmount = 0
        state.value = RunState.Running
        GetItemRequest.getItemByType(amount,
            stoneType.stone,
            1000L,
            success = {response, count ->
                totalAmount += count
                message.value = "成功获取 $totalAmount [${stoneType.chineseName}]"
            },
            fail = {
                state.value = RunState.Error
                message.value = it.msg
            })

        if (state.value != RunState.Error)
            state.value = RunState.Finish
    }
}
