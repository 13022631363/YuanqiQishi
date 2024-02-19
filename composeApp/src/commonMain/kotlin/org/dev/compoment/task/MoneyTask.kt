package org.dev.compoment.task

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import org.dev.compoment.bean.RunState
import org.dev.compoment.bean.TaskInfo
import org.dev.http.CastCoinsType
import org.dev.http.castCoins

data class MoneyTask(
    override val amount: Int,
    val coinsType: CastCoinsType,
    override val taskDetail: String = "数量: $amount\n详情: ${coinsType.symbol}",
    override val taskName: String = "金币任务",
    override var state: MutableState<RunState> = mutableStateOf(RunState.Wait),
    override var message: MutableState<String> = mutableStateOf(""),
    override var openDialog: MutableState<Boolean> = mutableStateOf(false)
): TaskInfo {
    override suspend fun run() {
        var totalAmount = 0
        state.value = RunState.Running
        while (amount != totalAmount)
        {
            castCoins(coinsType){
                totalAmount++
            }
            message.value = "成功获取 $totalAmount ${coinsType.symbol}"
        }

        if (state.value == RunState.Running)
            state.value = RunState.Finish
    }
}
