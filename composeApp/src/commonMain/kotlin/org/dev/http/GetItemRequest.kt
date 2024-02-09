package org.dev.http

import org.dev.http.bean.getItem.Card
import org.dev.http.bean.getItem.GetItemFailResponse
import org.dev.http.bean.getItem.GetItemRequestBody
import org.dev.http.bean.getItem.GetItemSuccessResponse
import org.dev.http.util.randomNumber
import kotlin.math.absoluteValue

/**
 * 刷卡片请求
 */
suspend fun getCard (amount: Int, card: Card) {

    var currentTotalAmount = 0

    while (true)
    {
        //随机地图
        val randomSceneAreaType = SceneAreaType.random()
        val cardIds = emptyList<String>().toMutableList()


        //发送进入请求并获取卡片集
        sceneEnter(SceneEnterLevelType.Boss,
            randomSceneAreaType,
            success = {
                cardIds.addAll (it.cardPool)
                currentTotalAmount += cardIds.size
            })


        val body = GetItemRequestBody (
            guids = emptyList<String>().toMutableList(),
            settlement = GetItemRequestBody.Settlement(randomSceneAreaType.areaValue,
                SceneEnterLevelType.Boss.level,
                emptyList<GetItemRequestBody.Settlement.ItemInfo>().toMutableList()),
            revision = "",
            secretKey = randomNumber,
            secretKey2 = randomNumber / 2.2
        )

        body.guids.addAll(cardIds)
        getRoleInfo (success = {
            body.revision = it.revision
        }, fail = {})

        cardIds.forEach {
            body.settlement.items.add (GetItemRequestBody.Settlement.ItemInfo (
                id = "CARD",
                itemId = it,
                owner = "00000000-0000-0000-0000-000000000000",
                type = 128,
                rate = 0,
                payMethod = 3,
                price = 0,
                season = 0,
                dictNested = GetItemRequestBody.Settlement.ItemInfo.CardID(card.id)
            ))
        }

        var remainAmount = amount - currentTotalAmount

        if (remainAmount < 0) {
            body.settlement.items.subList(0, remainAmount.absoluteValue).clear()
            body.guids.subList(0, remainAmount.absoluteValue).clear()
            remainAmount = 0
        }

        getItem(body, success= {
            //todo 存仓库
            println(it.gold)
            println("刷了 ${body.guids.size}")
        }, fail = {
            println(it.msg)
        })
        if (remainAmount == 0) return
    }

}

suspend fun getStone ()
{

}

suspend fun getFeather ()
{

}

suspend fun getEquipment ()
{

}

suspend fun getItem (body: GetItemRequestBody, success: (GetItemSuccessResponse) -> Unit, fail: (GetItemFailResponse) -> Unit)
{
    val response = post<GetItemRequestBody, GetItemFailResponse, GetItemSuccessResponse>(url = "https://api.soulknight-prequel.chillyroom.com/Package/AddV8868",
        body = body)

    when (response)
    {
        is GetItemSuccessResponse -> response.apply (success)
        is GetItemFailResponse -> response.apply(fail)
    }
}
