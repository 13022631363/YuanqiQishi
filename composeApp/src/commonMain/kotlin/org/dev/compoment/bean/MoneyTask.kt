package org.dev.compoment.bean

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import org.dev.http.CastCoinsType
import org.dev.http.bean.getItem.Item
import org.dev.http.castCoins

data class MoneyTask(
    override val amount: Int,
    val coinsType: CastCoinsType,
    override val taskDetail: String = "$amount ${coinsType.symbol}",
    override val taskName: String = "金币任务",
    override var state: MutableState<RunState> = mutableStateOf(RunState.Wait),
    override var message: MutableState<String> = mutableStateOf("")
): TaskInfo {
    override suspend fun run() {
        var totalAmount = 0
        while (amount != totalAmount)
        {
            castCoins(coinsType){
                state.value = RunState.Running
                totalAmount++
            }
            message.value = "成功获取 $totalAmount ${coinsType.symbol}"
        }

        if (state.value == RunState.Running)
            state.value = RunState.Finish
    }
}
