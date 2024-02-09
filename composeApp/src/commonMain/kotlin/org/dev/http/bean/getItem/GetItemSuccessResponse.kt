package org.dev.http.bean.getItem

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 刷物品响应体
 */
@Serializable
data class GetItemSuccessResponse(
    val gold: Double,
    val revision: String
)

/**
 * 刷物品响应体
 */
@Serializable
data class GetItemFailResponse(
    val error: Int,
    val msg: String,
    @SerialName ("errordetails")
    val errorDetail: String?
)
