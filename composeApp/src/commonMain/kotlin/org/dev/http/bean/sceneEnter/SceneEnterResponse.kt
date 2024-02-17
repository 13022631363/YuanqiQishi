package org.dev.http.bean.sceneEnter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.dev.http.util.JsonUtil.decodeFromString

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

            data class EquipMatchConditions (
                val name: String = "",
                val equipBuffSuit: String = "",
                val equipBuff0: String = "",
                val equipBuff1: String = "",
                val quality: String = ""
            ){
                fun isBlank (): Boolean
                {
                    return (name == "" && equipBuff0 == "" && equipBuff1 == "" && equipBuffSuit == "" && quality == "")
                }

                fun filterConditions (): Map<String, String>
                {
                    val result = mutableMapOf<String, String>()
                    val fields = this::class.java.declaredFields
                    fields.forEach {
                        it.isAccessible = true
                        val value = it.get(this)
                        if (value is String)
                            if (value != "")
                                result[it.name] = value
                    }
                    return result
                }
            }

            data class Buff (val chineseName: String, val id: String)

            enum class BuffType (val buffs: List<Buff>)
            {
                Common (commonBuff()), Epic (epicBuff())
            }

            companion object{
                private fun commonBuff (): List<Buff>
                {
                    val commonBuff = "{\"EBF_ARMOR_L\":\"轻甲\",\n" +

                            "\"EBF_CLOSE_FIRE\":\"贴身攻击\",\n" +
                            "\"EBF_ELE_DEF\":\"均衡之体\",\n" +
                            "\"EBF_DEBUFF_DMG\":\"厄运\",\n" +
                            "\"EBF_SOC_TOTEM\":\"法术图腾\",\n" +
                            "\"EBF_SUPERB_CHARGE\":\"超绝武艺\",\n" +

                            "\"EBF_ARMOR_H\":\"重甲\",\n" +
                            "\"EBF_ARMOR_R\":\"法袍\",\n" +
                            "\"EBF_SHIELD\":\"能量护盾\",\n" +
                            "\"EBF_BLESS\":\"神圣守卫\",\n" +
                            "\"EBF_CHARGE\":\"绝对专注\",\n" +
                            "\"EBF_CORE_FIRE\":\"火焰核心\",\n" +
                            "\"EBF_CORE_ICE\":\"冰冷核心\",\n" +
                            "\"EBF_CORE_ELE\":\"电击核心\",\n" +
                            "\"EBF_CORE_POI\":\"毒素核心\",\n" +
                            "\"EBF_CORE_LIG\":\"光明核心\",\n" +
                            "\"EBF_CORE_DARK\":\"黑暗核心\",\n" +
                            "\"EBF_CORE_PHY\":\"狮子核心\",\n" +
                            "\"EBF_CRIT_DMG\":\"弱点猛击\",\n" +
                            "\"EBF_NOVA_POINT\":\"引力新星\",\n" +
                            "\"EBF_MAG_CRIT\":\"属性暴击\",\n" +
                            "\"EBF_W_DEX\":\"敏捷专精\",\n" +
                            "\"EBF_W_MAG\":\"智力专精\",\n" +
                            "\"EBF_W_STR\":\"力量专精\",\n" +
                            "\"EBF_STORM\":\"疾风骤雨\",\n" +
                            "\"EBF_EJECTION\":\"弹射\",\n" +
                            "\"EBF_FOCUS\":\"风暴聚集\",\n" +
                            "\"EBF_LEAD_BULLET\":\"重型弹药\",\n" +
                            "\"EBF_LOCK\":\"锁定目标\",\n" +
                            "\"EBF_RATE_BONUS\":\"幸运星\",\n" +
                            "\"EBF_SWORD_ART\":\"剑术大师\",\n" +
                            "\"EBF_SCATTERING\":\"分裂\",\n" +
                            "\"EBF_SLAMMING\":\"猛击\",\n" +
                            "\"EBF_SPREAD\":\"扩散\",\n" +
                            "\"EBF_F_HIT\":\"强力一击\",\n" +
                            "\"EBF_DOUBLE_SKILL\":\"法术回响\",\n" +
                            "\"EBF_DOUBLE_MELEE\":\"双重打击\",\n" +
                            "\"EBF_ARROW\":\"箭矢改良\",\n" +
                            "\"EBF_DASH\":\"全力突进\",\n" +
                            "\"EBF_DOUBLE_NOVA\":\"双重新星\",\n" +
                            "\"EBF_BF_DURATION\":\"效果增幅\",\n" +
                            "\"EBF_STEALING_HP\":\"生命吸取\",\n" +
                            "\"EBF_STEALING_MP\":\"能量吸取\",\n" +
                            "\"EBF_BRAMBLE\":\"荆棘甲\",\n" +
                            "\"EBF_REFRESH_CD\":\"气定神闲\",\n" +
                            "\"EBF_SERVANT_MAX\":\"统帅力\",\n" +
                            "\"EBF_SERVANT_WEAPON\":\"羁绊召唤\",\n" +
                            "\"EBF_LOCK_ATB\":\"心无旁骛\"}"

                    return commonBuff.decodeFromString<Map<String, String>>().map {
                        Buff(it.value, it.key)
                    }
                }

                private fun epicBuff (): List<Buff>
                {
                    val epicBuff = "{\"EBFS_S_STR\":\"传奇之魂 红\",\n" +

                            "\"EBFS_BOSS_DDJ\":\"大当家之魂\",\n" +
                            "\"EBFS_BOSS_LSD\":\"龙石雕之魂\",\n" +
                            "\"EBFS_BOSS_WSK\":\"武圣铠之魂\",\n" +
                            "\"EBFS_BOSS_DZS\":\"大宗师之魂\",\n" +
                            "\"EBFS_BOSS_HQZ\":\"黑气宗之魂\",\n" +
                            "\"EBFS_BOSS_HSM\":\"黑煞魔之魂\",\n" +

                            "\"EBFS_S_DEX\":\"传奇之魂 绿\",\n" +
                            "\"EBFS_S_MAG\":\"传奇之魂 蓝\",\n" +
                            "\"EBFS_BOSS_YZW\":\"野猪王之魂\",\n" +
                            "\"EBFS_BOSS_EQB\":\"恶犬帮之魂\",\n" +
                            "\"EBFS_BOSS_DTL\":\"大田螺之魂\",\n" +
                            "\"EBFS_BOSS_MTL\":\"曼陀罗之魂\",\n" +
                            "\"EBFS_BOSS_MSS\":\"美杜莎之魂\",\n" +
                            "\"EBFS_BOSS_SJX\":\"水晶蟹之魂\",\n" +
                            "\"EBFS_BOSS_KLW\":\"枯骨王之魂\",\n" +
                            "\"EBFS_BOSS_LSC\":\"流沙虫之魂\",\n" +
                            "\"EBFS_BOSS_DQS\":\"大骑士之魂\",\n" +
                            "\"EBFS_BOSS_DWS\":\"大巫师之魂\",\n" +
                            "\"EBFS_BOSS_YSS\":\" 元素石之魂\",\n" +
                            "\"EBFS_BOSS_GBL\":\"哥布林之魂\",\n" +
                            "\"EBFS_BOSS_HDW\":\"海盗王之魂\",\n" +
                            "\"EBFS_BOSS_HLW\":\"火龙王之魂\",\n" +
                            "\"EBFS_BOSS_XYW\":\"雪猿王之魂\",\n" +
                            "\"EBFS_BOSS_JSX\":\"巨神像之魂\",\n" +
                            "\"EBFS_BOSS_WSS\":\"维生素之魂\",\n" +
                            "\"EBFS_BOSS_WKE\":\"瓦克恩之魂\",\n" +
                            "\"EBFS_BOSS_HQS\":\"黑骑士之魂\",\n" +
                            "\"EBFS_BOSS_SLM\":\"史莱姆之魂\",\n" +
                            "\"EBFS_BOSS_ANB\":\"阿努比之魂\",\n" +
                            "\"EBFS_BOSS_MTH\":\"曼陀花之魂\",\n" +
                            "\"EBFS_BOSS_HJX\":\"黄金蟹之魂\",\n" +
                            "\"EBFS_BOSS_XRW\":\"雪人王之魂\",\n" +
                            "\"EBFS_NPC_XQS\":\"骑士之魂\",\n" +
                            "\"EBFS_NPC_XFS\":\"法师之魂\",\n" +
                            "\"EBFS_NPC_XJL\":\"精灵之魂\",\n" +
                            "\"EBFS_NPC_XLR\":\"狼人之魂\",\n" +
                            "\"EBFS_NPC_KZS\":\"狂战士之魂\",\n" +
                            "\"EBFS_NPC_GCS\":\"工程师之魂\",\n" +
                            "\"EBFS_NPC_XYX\":\"游侠之魂\",\n" +
                            "\"EBFS_NPC_XLJ\":\"炼金之魂\",\n" +
                            "\"EBFS_NPC_XMS\":\"牧师之魂\",\n" +
                            "\"EBFS_NPC_SQS\":\"圣骑士之魂\",\n" +
                            "\"EBFS_NPC_XCK\":\"刺客之魂\",\n" +
                            "\"EBFS_NPC_XXG\":\"血族之魂\",\n" +
                            "\"EBFS_NPC_DLY\":\"德鲁伊之魂\",\n" +
                            "\"EBFS_LEGEND\":\"金色传说\",\n" +
                            "\"EBFS_RED_YQ\":\"强力普通拳\",\n" +
                            "\"EBFS_RED_MY\":\"精准弹幕 影\",\n" +
                            "\"EBFS_RED_HLW\":\"旋风斩 烈\",\n" +
                            "\"EBFS_RED_XRW\":\"冰锥术 凝\"}"

                    return epicBuff.decodeFromString<Map<String, String>>().map {
                        Buff (it.value, it.key)
                    }
                }
            }
        }



    }

}
