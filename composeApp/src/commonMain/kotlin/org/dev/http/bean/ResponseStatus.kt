package org.dev.http.bean

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseStatus (
    val code: String,
    @SerialName("result")
    var textContent: String,
    var message: String,
    val backup: String)