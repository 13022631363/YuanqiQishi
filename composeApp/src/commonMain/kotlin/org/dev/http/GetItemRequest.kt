package org.dev.http


import org.dev.http.bean.getItem.GetItemFailResponse
import org.dev.http.bean.getItem.GetItemRequestBody
import org.dev.http.bean.getItem.GetItemSuccessResponse
import org.dev.http.bean.getItem.Item
import org.dev.http.bean.sceneEnter.SceneEnterResponse
import org.dev.http.util.randomNumber
import kotlin.math.absoluteValue

/**
 * 刷卡片请求
 */
suspend fun getCard (amount: Int, card: Item.Card, success: (GetItemSuccessResponse, Int) -> Unit, fail: (GetItemFailResponse) -> Unit ) {

   sameCount(amount,
       sceneEnterSuccess = (SceneEnterResponse::cardPool),
       itemInfoBuild = { cardUUID ->
           val cardItemInfoCopy = Item.Card.cardItemInfo.copy()
           cardItemInfoCopy.itemId = cardUUID
           cardItemInfoCopy.dictNested = GetItemRequestBody.Settlement.ItemInfo.CardID (card.id)

           cardItemInfoCopy
       },
       everySuccessfulAcquisition = { response, successAmount ->
            success (response, successAmount)
       },
       ifFailure = { response, isEnd ->
           isEnd (true)
           fail (response)
       })

}

/**
 * @param targetAmount 目标数量
 * @param sceneEnterSuccess 如果进入地图请求成功 就应从提供的请求体中指定具体哪种物品的 uuid 集合
 * @param itemInfoBuild 提供单个 itemUUID 构建 ItemInfo 刷物品需要
 * @param everySuccessfulAcquisition 如果成功刷出 提供刷新的数量和响应体来处理后续逻辑
 * @param ifFailure 如果失败 可能出现的情况是卡片失效 提供失败响应体和是否要结束设置函数 来处理后续逻辑
 */
private suspend fun sameCount (targetAmount: Int,
                               sceneEnterSuccess: (SceneEnterResponse) -> List<String>,
                               itemInfoBuild: (String) -> GetItemRequestBody.Settlement.ItemInfo,
                               everySuccessfulAcquisition: (GetItemSuccessResponse, Int) -> Unit,
                               ifFailure: (GetItemFailResponse, (Boolean) -> Unit) -> Unit)
{
    var currentTotalAmount = 0

    while (true)
    {
        //随机地图
        val randomSceneAreaType = SceneAreaType.random()
        val itemUUID = emptyList<String>().toMutableList()


        //发送进入请求并获取卡片集
        sceneEnter(SceneEnterLevelType.Boss,
            randomSceneAreaType,
            success = {
                itemUUID.addAll (sceneEnterSuccess (it))
                currentTotalAmount += itemUUID.size
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

        body.guids.addAll(itemUUID)
        getRoleInfo (success = {
            body.revision = it.revision
        }, fail = {})

        itemUUID.forEach {
            body.settlement.items.add (itemInfoBuild (it))
        }

        var remainAmount = targetAmount - currentTotalAmount

        if (remainAmount < 0) {
            body.settlement.items.subList(0, remainAmount.absoluteValue).clear()
            body.guids.subList(0, remainAmount.absoluteValue).clear()
            remainAmount = 0
        }

        getItem(body,
            success = {
                everySuccessfulAcquisition (it, body.guids.size)
            },
            fail = {
                ifFailure (it){isEnd ->
                    if (isEnd)
                        remainAmount = 0
            }
        })
        if (remainAmount == 0) return
    }
}

suspend fun getStone (amount: Int, stone: Item.Stone,  success: (GetItemSuccessResponse, Int) -> Unit, fail: (GetItemFailResponse) -> Unit)
{
    sameCount(amount,
        sceneEnterSuccess = SceneEnterResponse::genericItems,
        itemInfoBuild = {stoneId ->
            val itemInfoCopy = stone.itemInfo.copy()
            //由于data class ItemInfo未在主构造器中持有 dictNested 字段 所以 copy 并不包含此字段
            itemInfoCopy.itemId = stoneId
            itemInfoCopy.dictNested = stone.itemInfo.dictNested
            itemInfoCopy
        },
        everySuccessfulAcquisition = {response, successAmount ->
            success (response, successAmount)
        },
        ifFailure = { response, isEnd ->
            isEnd (true)
            fail (response)
        })
}

suspend fun getFeather (amount: Int, feather: Item.Feather,  success: (GetItemSuccessResponse, Int) -> Unit, fail: (GetItemFailResponse) -> Unit)
{
    sameCount(amount,
        sceneEnterSuccess = SceneEnterResponse::genericItems,
        itemInfoBuild = {stoneId ->
            val itemInfoCopy = feather.itemInfo.copy()
            //由于data class ItemInfo未在主构造器中持有 dictNested 字段 所以 copy 并不包含此字段
            itemInfoCopy.itemId = stoneId
            itemInfoCopy.dictNested = feather.itemInfo.dictNested
            itemInfoCopy
        },
        everySuccessfulAcquisition = {response, successAmount ->
            success (response, successAmount)
        },
        ifFailure = { response, isEnd ->
            isEnd (true)
            fail (response)
        })
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
