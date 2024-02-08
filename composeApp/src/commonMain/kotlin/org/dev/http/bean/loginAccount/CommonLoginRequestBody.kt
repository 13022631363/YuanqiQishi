package org.dev.http.bean.loginAccount

import kotlinx.serialization.Serializable

/**
 * 普通登陆的请求体
 * 默认参数 loginType: phone
 */
@Serializable
data class CommonLoginRequestBody(
    val loginType: String = "",
    val account: String = "",
    val password: String = ""
)
