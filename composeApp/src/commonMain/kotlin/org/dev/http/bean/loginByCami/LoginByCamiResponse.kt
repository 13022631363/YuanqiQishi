package org.dev.http.bean.loginByCami

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginByCamiSuccessResponse (
    val code: String,
    val id: String,
    val end_time: String,
    val amount: String,
    val available: Long,
    val token: String,
    val statecode: String,
    val safe_code: String?,
    val time: Long,
    val date: String,
    val imei: String,
    val change: String,
    val core: String
)

@Serializable
data class LoginByCamiFailResponse (
    /**
     * 状态码，成功200，失败201
     */
    val code: String,

    /**
     * 说明，失败说明
     */
    val msg: String,

    /**
     * 服务器时间戳，服务器时间戳
     */
    val time: Long,

    @SerialName ("safe_code")
    val safeCode: String?
)