package org.dev.http.bean.loginAccount

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BeforeLogin4399Response (
    val code: String,
    @SerialName("result")
    val textContent: String,
    val message: String,
    val backup: String)