package org.dev.http.bean.heartBeat

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HeartBeatResponse(
    val code: String,
    val msg: String,
    val time: String,
    @SerialName ("safe_code")
    val safeCode: String?
)


