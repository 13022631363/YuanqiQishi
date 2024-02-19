package org.dev.http.bean.loginAccount

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 登陆请求成功的返回对象
 */
@Serializable
data class SuccessLoginResponse (
    val session: Detail
){
    @Serializable
    data class Detail (
        val uid: String,
        val token: String,
        val expire: String
    )
}

/**
 * 登陆请求密码错误失败的返回对象
 */
@Serializable
data class FailLogin1Response (
    val error: String,
    val msg: String?,
    @SerialName ("errordetails")
    val errorDetails: String?,
)

/**
 * 登陆请求密码错误失败的返回对象
 */
@Serializable
data class FailLogin2Response (
    val ok: Boolean,
    val errorCode: Int,
    val msg: String,
    val message: String,
    @SerialName ("errordetails")
    val errorDetails: ErrorDetails,
){
    @Serializable
    data class ErrorDetails (
        val timeLeft: String,
        val uid: String,
        val localizedDetail: String
    )
}

