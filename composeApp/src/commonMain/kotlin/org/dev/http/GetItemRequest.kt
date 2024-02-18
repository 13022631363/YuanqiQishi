package org.dev.http

import org.dev.http.bean.getItem.*
import org.dev.http.bean.sceneEnter.SceneEnterResponse
import org.dev.http.util.randomNumber
import kotlin.math.absoluteValue

object GetItemRequest
{
    /**
     * @param targetAmount 目标数量
     * @param sceneEnterSuccess 如果进入地图请求成功 回调函数提供 1.进入地图响应体 需返回一个 uuid 集合
     * @param itemInfoBuild 回调函数提供 1.上面 uuid 集合的每个 uuid  需返回一个构建好的 itemInfo
     * @param everySuccessfulAcquisition 如果成功刷出 回调函数提供 1.响应体来处理后续逻辑 2.刷新的数量
     * @param ifFailure 如果失败 可能出现的情况是卡片失效 回调函数提供 1.失败响应体 来处理后续逻辑
     */
    private suspend fun sameCount (targetAmount: Int,
                                   delay: Long,
                                   sceneEnterSuccess: (SceneEnterResponse) -> List<String>,
                                   itemInfoBuild: (String) -> GetItemRequestBody.Settlement.ItemInfo,
                                   everySuccessfulAcquisition: (GetItemSuccessResponse, Int) -> Unit,
                                   ifFailure: (GetItemFailResponse) -> Unit)
    {
        var currentTotalAmount = 0

        while (true)
        {
            //随机地图
            val randomSceneAreaType = SceneAreaType.random()
            val itemUUID = emptyList<String>().toMutableList()


            //发送进入请求并获取卡片集
            sceneEnter(sceneLevelType = SceneEnterLevelType.Boss,
                sceneAreaType = randomSceneAreaType,
                delay = delay,
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
                    ifFailure (it)
                    remainAmount = 0
                }
            )
            if (remainAmount == 0) return
        }
    }

    /**
     * @param amount 数量
     * @param item 指定的物品
     * @param delay 延迟
     * @param success 如果成功 回调函数持有 1.物品获取成功的响应体 2.成功获取的数量
     * @param fail 如果失败 回调函数持有 1.物品获取失败响应体
     */
    suspend fun getItemByType  (amount: Int, item: Item, delay: Long, success: (GetItemSuccessResponse, Int) -> Unit, fail: (GetItemFailResponse) -> Unit)
    {
        sameCount(amount, delay,
            sceneEnterSuccess = {
                when (item)
                {
                    is Item.Card -> it.cardPool
                    is Item.Feather -> it.genericItems
                    is Item.Stone -> it.genericItems
                }
            },
            itemInfoBuild = {id ->
                val itemInfoCopy = item.itemInfo.copy()
                //由于data class ItemInfo未在主构造器中持有 dictNested 字段 所以 copy 并不包含此字段
                itemInfoCopy.itemId = id
                itemInfoCopy.dictNested  = if (item is Item.Card)
                    GetItemRequestBody.Settlement.ItemInfo.CardID (item.id)
                else
                    item.itemInfo.dictNested

                itemInfoCopy
            },
            everySuccessfulAcquisition = {response, successAmount ->
                success (response, successAmount)
            },
            ifFailure = { response ->
                fail (response)
            })
    }

    /**
     * 根据条件获取对应满足条件的装备
     * @param amount 数量
     * @param delay 延迟
     * @param conditions 条件
     * @param success 如果成功获取装备 回调函数持有 1.物品获取成功的响应体 2.成功刷出装备数 3.刷出的装备信息
     * @param fail 如果失败 回调函数持有 1.物品获取失败的响应体
     * @param sceneAreaType 地图选择
     * @param sceneEnterLevelType 地图等级选择
     */
    suspend fun getEquipment (amount: Int, delay: Long, conditions: SceneEnterResponse.EquipmentPools.EquipmentInfo.EquipMatchConditions, sceneAreaType: SceneAreaType, sceneEnterLevelType: SceneEnterLevelType,  success: (response: GetItemSuccessResponse, successNumber:Int, matchedEquip: SceneEnterResponse.EquipmentPools.EquipmentInfo) -> Unit, fail: (GetItemFailResponse) -> Unit)
    {
        var currentAmount = 0
        while (true)
        {

            if (currentAmount == amount) break
            var matchedEquip: SceneEnterResponse.EquipmentPools.EquipmentInfo? = null

            val equipmentInfos = mutableListOf<SceneEnterResponse.EquipmentPools.EquipmentInfo>()
            sceneEnter(sceneEnterLevelType, sceneAreaType, 5, delay){
                equipmentInfos.addAll(it.equipmentPools.specialDrop.values)
                equipmentInfos.addAll(it.equipmentPools.low)
                equipmentInfos.addAll(it.equipmentPools.medium)
                equipmentInfos.addAll(it.equipmentPools.high)
            }

            for (info in equipmentInfos){
                var sameNum = 0
                var isSecondExchange = false
                val filterConditions = conditions.filterConditions()
                val dictNested = info.dictNested
                for (condition in filterConditions){
                    if (condition.key == "name" )
                    {
                        if (info.id == condition.value)
                            sameNum++
                        continue
                    }

                    val field = dictNested::class.java.getDeclaredField(condition.key)
                    field.isAccessible = true
                    if (field.get(dictNested) == condition.value)
                        sameNum++
                    else {

                        if (condition.key == "equipBuff0")
                        {
                            val field1 = dictNested::class.java.getDeclaredField("equipBuff1")
                            field1.isAccessible = true
                            if (field1.get(dictNested) == condition.value)
                            {
                                sameNum++
                                isSecondExchange = true
                            }
                        }
                        if (condition.key == "equipBuff1" && isSecondExchange)
                        {
                            val field0 = dictNested::class.java.getDeclaredField("equipBuff0")
                            field0.isAccessible = true
                            if (field0.get(dictNested) == condition.value)
                                sameNum++
                        }
                    }
                }
                if (filterConditions.size == sameNum)
                {
                    matchedEquip = info
                    break
                }
            }

            matchedEquip?.let {info ->
                currentAmount++
                //获取装备
                var revision = ""
                getRoleInfo(success = {
                    revision = it.revision
                }, fail = {})
                getEquip(
                    GetEquipRequestBody(
                        guids = listOf(info.itemId),
                        revision = revision,
                        currencies = emptyList(),
                        secretKey = randomNumber,
                        secretKey2 = randomNumber / 2.2
                    ),
                    success = {response ->
                        success (response, 1, info) },
                    fail = {
                        fail (it)
                    }
                )
            }
        }
    }

    /**
     * 获取物品请求
     */
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

    /**
     * 获取装备请求
     */
    suspend fun getEquip (body: GetEquipRequestBody, success: (GetItemSuccessResponse) -> Unit, fail: (GetItemFailResponse) -> Unit)
    {
        val response = post<GetEquipRequestBody, GetItemFailResponse, GetItemSuccessResponse>(url = "https://api.soulknight-prequel.chillyroom.com/Package/AddV8868", body)

        when (response)
        {
            is GetItemSuccessResponse -> response.apply (success)
            is GetItemFailResponse -> response.apply(fail)
        }
    }

}