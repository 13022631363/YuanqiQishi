package org.dev.http.bean

import kotlinx.serialization.SerialInfo
import kotlinx.serialization.Serializable

@Serializable
data class CommonLoginBody(
    var loginType: String = "",
    var account: String = "",
    var password: String = ""
)