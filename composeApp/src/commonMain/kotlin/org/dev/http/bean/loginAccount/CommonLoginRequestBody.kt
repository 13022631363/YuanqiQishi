package org.dev.http.bean.loginAccount

import kotlinx.serialization.Serializable

/**
 * 普通登陆的请求体
 * 默认参数 loginType: phone
 */
@Serializable
data class CommonLoginRequestBody(
    var loginType: String = "",
    var account: String = "",
    var password: String = ""
)
