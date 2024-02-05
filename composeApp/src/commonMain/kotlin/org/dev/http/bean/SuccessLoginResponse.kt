package org.dev.http.bean

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
 * 登陆请求失败的返回对象
 */
@Serializable
data class FailLoginResponse (
    val error: String,
    val msg: String?,
    @SerialName ("errordetails")
    val errorDetails: String?,
)