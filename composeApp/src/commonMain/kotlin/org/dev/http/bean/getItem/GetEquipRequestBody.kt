package org.dev.http.bean.getItem

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetEquipRequestBody(
    @SerialName ("guidsv465")
    val guids: List<String>,
    val revision: String,
    @SerialName ("currenciesv123")
    val currencies: List<String>,
    val secretKey: Int,
    val secretKey2: Double,
)
