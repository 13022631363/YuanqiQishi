package org.dev.http.bean.getItem

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetItemRequestBody(
    @SerialName("guidsv465")
    val guids: MutableList<String>,
    @SerialName ("settlementv789")
    val settlement: Settlement,
    var revision: String,
    val secretKey: Int,
    val secretKey2: Double
){
    @Serializable
    data class Settlement (
        val sceneAreaType: Int,
        val stageType: Int,
        val items: MutableList<ItemInfo>,
    ){

        @Serializable
         class ItemInfo (
            val id: String,
            val itemId: String,
            val owner: String,
            val type: Int,
            val rate: Int,
            @SerialName ("pay_method")
            val payMethod: Int,
            val price: Int,
            val season: Int,
            @SerialName ("dict_nested")
            val dictNested: ItemId
        ){
             @Serializable
            sealed interface ItemId
            @Serializable
            data class CardID (
                @SerialName ("card_id")
                val cardId: String
            ): ItemId

            @Serializable
            data class OtherID (
                @SerialName("_jewelType")
                val jewelType: String
            ): ItemId
        }

    }
}


