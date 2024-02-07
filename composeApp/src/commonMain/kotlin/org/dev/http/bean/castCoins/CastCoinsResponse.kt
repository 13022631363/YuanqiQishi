package org.dev.http.bean.castCoins

import kotlinx.serialization.Serializable

/**
 * 刷金币返回的相应体
 */
@Serializable
data class CastCoinsResponse(
    val gold: Int
)
