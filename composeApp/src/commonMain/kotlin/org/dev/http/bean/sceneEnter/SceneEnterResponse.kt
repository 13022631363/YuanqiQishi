package org.dev.http.bean.sceneEnter

import com.google.gson.internal.LinkedTreeMap
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 进入地图的响应体
 */
@Serializable
data class SceneEnterResponse(
    val cardPool: List<String>, //对应刷卡片
    val equipmentPools : EquipmentPools, //对应刷装备
    val genericItems: List<String>, //对应刷石头
    val subGenericItems: RedPacket //春节物品 红包
){

    @Serializable
    data class RedPacket (
        @SerialName ("red_packet_s")
        val redPackets: List<String>
    )

    /**
     * 装备池
     */
    @Serializable
    data class EquipmentPools (
        val high: List<EquipmentInfo>,
        val medium: List<EquipmentInfo>,
        val low: List<EquipmentInfo>,
        val specialDrop: Map<String, EquipmentInfo>
    ){
        /**
         * 每件装备的信息
         */
        @Serializable
        data class EquipmentInfo (
            val id: String,
            val itemId: String,
            val owner: String,
            val type: Double,
            val rate: Double,
            val season: Double,
            val serverTag: String,
            val clientTag: String,
            @SerialName ("dict_nested")
            val dictNested: PublicDictNested
        ){
            /**
             * 装备信息里的羁绊词条相关信息 (部分省略)
             */
            @Serializable
            data class PublicDictNested (
                val style: String,
                @SerialName ("item_level") //物品等级
                val itemLevel: String,
                @SerialName ("equip_buff_suit") //boss羁绊
                val equipBuffSuit: String,
                @SerialName ("equip_buff_0")//普通羁绊 1
                val equipBuff0: String,
                @SerialName ("equip_buff_1")//普通羁绊 2
                val equipBuff1: String,
                val strengthen: String,//强化次数
                val quality: String,//品质
                val operation: String,
                @SerialName ("enchant_list")
                val enchantList: String, //词条列表
                @SerialName ("enchant_list_X")
                val enchantListX : String,
                val prd: String, //周期 (不知道有啥用)
                @SerialName ("cached_remake_enchantment")
                val cachedRemakeEnchantment: String,
                @SerialName ("remake_equip_buffs")
                val remakeEquipBuffs: String,
            )
        }

    }

}
