package org.dev.http.bean.castCoins

import kotlinx.serialization.Serializable

/**
 * 刷金币所需的请求体
 */
@Serializable
data class CastCoinsRequestBody(
    val golds: List<Int>,
    val cost: Int
)

private fun castCoinsCountList (single: Int, count: Int): List<Int>
{
    val result = arrayListOf<Int>()
    for (i in 0 until count)
        result.add(single)
    return result
}

val castCoins10wRequestBody = CastCoinsRequestBody (castCoinsCountList(999, 118), 25)
val castCoins100wRequestBody = CastCoinsRequestBody (castCoinsCountList(20000, 41), 25)