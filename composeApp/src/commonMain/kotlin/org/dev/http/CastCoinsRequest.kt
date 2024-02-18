package org.dev.http

import org.dev.http.bean.castCoins.CastCoinsRequestBody
import org.dev.http.bean.castCoins.CastCoinsResponse

enum class CastCoinsType (val castCoinsRequestBody: CastCoinsRequestBody, val symbol: String)
{
    CastCoins10w (CastCoinsRequestBody.castCoins10wRequestBody, "10W"),
    CastCoins100w (CastCoinsRequestBody.castCoins100wRequestBody, "100W")
}

/**
 * 刷金币请求
 */
suspend fun castCoins (type: CastCoinsType, success: (CastCoinsResponse) -> Unit)
{
    val response = post<CastCoinsRequestBody, CastCoinsResponse, CastCoinsResponse>(url = "https://api.soulknight-prequel.chillyroom.com/Package/CostGold",
        body = type.castCoinsRequestBody)

    success (response as CastCoinsResponse)
}