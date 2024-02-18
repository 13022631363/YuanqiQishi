package org.dev.compoment.bean

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.dev.http.GetItemRequest
import org.dev.http.bean.getItem.Item


object CardChooseInfo
{
    var amount by mutableStateOf(0)
    var cardType by mutableStateOf(Item.Card.Type.Boss)
    var card by mutableStateOf(Item.Card ("野猪王", "E01_B02"))
}

data class CardTask (
    val card: Item.Card,
    override val taskName: String = "卡片任务",
    override val amount: Int,
    override val taskDetail: String = "$amount ${card.cardName}",
    override var state: MutableState<RunState> = mutableStateOf(RunState.Wait),
    override var message: MutableState<String> = mutableStateOf("")
): TaskInfo{


    override suspend fun run() {
        var totalAmount = 0
        GetItemRequest.getItemByType(amount,
            card,
            1000L,
            success = {response, count ->
                state.value = RunState.Running
                totalAmount += count
                message.value = "成功获取 $totalAmount [${card.cardName}]"
            },
            fail = {
                state.value = RunState.Error
                message.value = it.msg
            })

        if (state.value == RunState.Running)
            state.value = RunState.Finish
    }
}