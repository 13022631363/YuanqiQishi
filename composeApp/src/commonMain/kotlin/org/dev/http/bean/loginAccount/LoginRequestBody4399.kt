package org.dev.http.bean.loginAccount

import kotlinx.serialization.Serializable

/**
 *  4399登陆请求的请求体
 *
 *  //todo 这里不能赋值默认值 需要后面赋值
 */
@Serializable
data class LoginRequestBody4399 (
    val loginType: String = "HeZi4399",
    val credential: Credential = Credential ()
){
    /**
     * 凭证对象
     */
    @Serializable
    data class Credential (
        var idCardEditable: Boolean = false,
        var idCardState: Int = -1,
        val idCheckedReal: Boolean = true,
        var name: String = "zzz",
        val phone: String = "",
        var state: String = "",
        var uid: Int = -1
    )
}

