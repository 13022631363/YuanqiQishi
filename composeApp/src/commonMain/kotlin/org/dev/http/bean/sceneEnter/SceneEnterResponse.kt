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
                val equipBuffSuit: Buff = Buff.emptyBuff,
                val equipBuff0: Buff = Buff.emptyBuff,
                val equipBuff1: Buff = Buff.emptyBuff,
                val quality: String = ""
            ){
                fun isBlank (): Boolean
                {
                    return (name == "" && equipBuff0 == Buff.emptyBuff && equipBuff1 == Buff.emptyBuff && equipBuffSuit == Buff.emptyBuff && quality == "")
                }

                fun filterConditions (): Map<String, String>
                {
                    val result = mutableMapOf<String, String>()
                    val fields = this::class.java.declaredFields
                    fields.forEach {
                        it.isAccessible = true
                        val value = it.get(this)
                        if (value is String)
                        {
                            if (value != "")
                                result[it.name] = value
                        }else if (value is Buff)
                        {
                            if (value.id != "")
                                result[it.name] = value.id
                        }

                    }
                    return result
                }
            }

            data class Buff (val chineseName: String, val id: String)
            {
                companion object{
                    val emptyBuff = Buff ("无视", "")
                }
            }

            enum class BuffType (val buffs: List<Buff>)
            {
                Common (commonBuff()), Epic (epicBuff())
            }

            companion object{
                private fun commonBuff (): List<Buff>
                {
                    val commonBuff =  """{ "EBF_ARMOR_L": "轻甲",
 "EBF_ARMOR_H": "重甲",
 "EBF_ARMOR_R": "法袍",
 "EBF_SHIELD": "能量护盾",
 "EBF_BLESS": "神圣守卫",
 "EBF_CHARGE": "绝对专注",
 "EBF_CORE_FIRE": "火焰核心",
 "EBF_CORE_ICE": "冰冷核心",
 "EBF_CORE_ELE": "电击核心",
 "EBF_CORE_POI": "毒素核心",
 "EBF_CORE_LIG": "光明核心",
 "EBF_CORE_DARK": "黑暗核心",
 "EBF_CORE_PHY": "狮子核心",
 "EBF_CRIT_DMG": "弱点猛击",
 "EBF_NOVA_POINT": "引力新星",
 "EBF_MAG_CRIT": "属性暴击",
 "EBF_W_DEX": "敏捷专精",
 "EBF_W_MAG": "智力专精",
 "EBF_W_STR": "力量专精",
 "EBF_STORM": "疾风骤雨",
 "EBF_EJECTION": "弹射",
 "EBF_FOCUS": "风暴聚集",
 "EBF_LEAD_BULLET": "重型弹药",
 "EBF_LOCK": "锁定目标",
 "EBF_RATE_BONUS": "幸运星",
 "EBF_SWORD_ART": "剑术大师",
 "EBF_SCATTERING": "分裂",
 "EBF_SLAMMING": "猛击",
 "EBF_SPREAD": "扩散",
 "EBF_F_HIT": "强力一击",
 "EBF_DOUBLE_SKILL": "法术回响",
 "EBF_DOUBLE_MELEE": "双重打击",
 "EBF_ARROW": "箭矢改良",
 "EBF_DASH": "全力突进",
 "EBF_DOUBLE_NOVA": "双重新星",
 "EBF_BF_DURATION": "效果增幅",
 "EBF_STEALING_HP": "生命吸取",
 "EBF_STEALING_MP": "能量吸取",
 "EBF_BRAMBLE": "荆棘甲",
 "EBF_REFRESH_CD": "气定神闲",
 "EBF_SERVANT_MAX": "统帅力",
 "EBF_SERVANT_WEAPON": "羁绊召唤",
 "EBF_LOCK_ATB": "心无旁骛",
 "EBF_SUPERB_CHARGE": "超绝武艺",
 "EBF_SOC_TOTEM": "法术图腾",
 "EBF_DEBUFF_DMG": "厄运",
 "EBF_CLOSE_FIRE": "贴身射击",
 "EBF_ELE_DEF": "均衡之体",
 "EBF_ARMOR_L_HEISHA": "轻甲（黑煞）",
 "EBF_ARMOR_H_HEISHA": "重甲（黑煞）",
 "EBF_ARMOR_R_HEISHA": "法袍（黑煞）",
 "EBF_SHIELD_HEISHA": "能量护盾（黑煞）",
 "EBF_BLESS_HEISHA": "神圣守卫（黑煞）",
 "EBF_CHARGE_HEISHA": "绝对专注（黑煞）",
 "EBF_CORE_FIRE_HEISHA": "火焰核心（黑煞）",
 "EBF_CORE_ICE_HEISHA": "冰冷核心（黑煞）",
 "EBF_CORE_ELE_HEISHA": "电击核心（黑煞）",
 "EBF_CORE_POI_HEISHA": "毒素核心（黑煞）",
 "EBF_CORE_LIG_HEISHA": "光明核心（黑煞）",
 "EBF_CORE_DARK_HEISHA": "黑暗核心（黑煞）",
 "EBF_CORE_PHY_HEISHA": "狮子核心（黑煞）",
 "EBF_CRIT_DMG_HEISHA": "弱点猛击（黑煞）",
 "EBF_NOVA_POINT_HEISHA": "引力新星（黑煞）",
 "EBF_MAG_CRIT_HEISHA": "属性暴击（黑煞）",
 "EBF_W_DEX_HEISHA": "敏捷专精（黑煞）",
 "EBF_W_MAG_HEISHA": "智力专精（黑煞）",
 "EBF_W_STR_HEISHA": "力量专精（黑煞）",
 "EBF_STORM_HEISHA": "疾风骤雨（黑煞）",
 "EBF_EJECTION_HEISHA": "弹射（黑煞）",
 "EBF_FOCUS_HEISHA": "风暴聚集（黑煞）",
 "EBF_LEAD_BULLET_HEISHA": "重型弹药（黑煞）",
 "EBF_LOCK_HEISHA": "锁定目标（黑煞）",
 "EBF_RATE_BONUS_HEISHA": "幸运星（黑煞）",
 "EBF_SWORD_ART_HEISHA": "剑术大师（黑煞）",
 "EBF_SCATTERING_HEISHA": "分裂（黑煞）",
 "EBF_SLAMMING_HEISHA": "猛击（黑煞）",
 "EBF_SPREAD_HEISHA": "扩散（黑煞）",
 "EBF_F_HIT_HEISHA": "强力一击（黑煞）",
 "EBF_DOUBLE_SKILL_HEISHA": "法术回响（黑煞）",
 "EBF_DOUBLE_MELEE_HEISHA": "双重打击（黑煞）",
 "EBF_ARROW_HEISHA": "箭矢改良（黑煞）",
 "EBF_DASH_HEISHA": "全力突进（黑煞）",
 "EBF_DOUBLE_NOVA_HEISHA": "双重新星（黑煞）",
 "EBF_BF_DURATION_HEISHA": "效果增幅（黑煞）",
 "EBF_STEALING_HP_HEISHA": "生命吸取（黑煞）",
 "EBF_STEALING_MP_HEISHA": "能量吸取（黑煞）",
 "EBF_BRAMBLE_HEISHA": "荆棘甲（黑煞）",
 "EBF_REFRESH_CD_HEISHA": "气定神闲（黑煞）",
 "EBF_SUPERB_CHARGE_HEISHA": "超绝武艺（黑煞）",
 "EBF_SOC_TOTEM_HEISHA": "法术图腾（黑煞）",
 "EBF_CLOSE_FIRE_HEISHA": "贴身射击（黑煞）",
 "EBF_ELE_DEF_HEISHA": "均衡之体（黑煞）",
 "EBF_SERVANT_MAX_HEISHA": "统帅力（黑煞）",
 "EBF_SERVANT_WEAPON_HEISHA": "羁绊召唤（黑煞）",
 "EBF_LOCK_ATB_HEISHA": "心无旁骛（黑煞）",
 "EBF_DEBUFF_DMG_HEISHA": "厄运（黑煞）",}"""

                    return commonBuff.decodeFromString<Map<String, String>>().map {
                        Buff(it.value, it.key)
                    }.toMutableList().apply { add(Buff.emptyBuff) }
                }

                private fun epicBuff (): List<Buff>
                {
                    val epicBuff = """{ "EBFS_S_STR": "传奇之魂 红",
 "EBFS_S_DEX": "传奇之魂 绿",
 "EBFS_S_MAG": "传奇之魂 蓝",
 "EBFS_BOSS_YZW": "野猪王之魂",
 "EBFS_BOSS_EQB": "恶犬帮之魂",
 "EBFS_BOSS_DTL": "大田螺之魂",
 "EBFS_BOSS_MTL": "曼陀罗之魂",
 "EBFS_BOSS_MSS": "美杜莎之魂",
 "EBFS_BOSS_SJX": "水晶蟹之魂",
 "EBFS_BOSS_KLW": "枯骨王之魂",
 "EBFS_BOSS_LSC": "流沙虫之魂",
 "EBFS_BOSS_DQS": "大骑士之魂",
 "EBFS_BOSS_DWS": "大巫师之魂",
 "EBFS_BOSS_YSS": "元素石之魂",
 "EBFS_BOSS_GBL": "哥布林之魂",
 "EBFS_BOSS_HDW": "海盗王之魂",
 "EBFS_BOSS_HLW": "火龙王之魂",
 "EBFS_BOSS_XYW": "雪猿王之魂",
 "EBFS_BOSS_JSX": "巨神像之魂",
 "EBFS_BOSS_WSS": "维生素之魂",
 "EBFS_BOSS_WKE": "瓦克恩之魂",
 "EBFS_BOSS_HQS": "黑骑士之魂",
 "EBFS_BOSS_SLM": "史莱姆之魂",
 "EBFS_BOSS_ANB": "阿努比之魂",
 "EBFS_BOSS_MTH": "曼陀花之魂",
 "EBFS_BOSS_HJX": "黄金蟹之魂",
 "EBFS_BOSS_XRW": "雪人王之魂",
 "EBFS_BOSS_DDJ": "大当家之魂",
 "EBFS_BOSS_LSD": "龙石雕之魂",
 "EBFS_BOSS_DZS": "大宗师之魂",
 "EBFS_BOSS_HQZ": "黑气宗之魂",
 "EBFS_BOSS_WSK": "武圣铠之魂",
 "EBFS_BOSS_HSM": "黑煞魔之魂",
 "EBFS_BOSS_NS": "年兽之魂",
 "EBFS_NPC_XQS": "骑士之魂",
 "EBFS_NPC_XFS": "法师之魂",
 "EBFS_NPC_XJL": "精灵之魂",
 "EBFS_NPC_XLR": "狼人之魂",
 "EBFS_NPC_KZS": "狂战士之魂",
 "EBFS_NPC_GCS": "工程师之魂",
 "EBFS_NPC_XYX": "游侠之魂",
 "EBFS_NPC_XLJ": "炼金之魂",
 "EBFS_NPC_XMS": "牧师之魂",
 "EBFS_NPC_SQS": "圣骑士之魂",
 "EBFS_NPC_XCK": "刺客之魂",
 "EBFS_NPC_XXG": "血族之魂",
 "EBFS_NPC_DLY": "德鲁伊之魂",
 "EBFS_ITEM_LEGEND": "金色传说",
 "EBFS_RED_YQ": "强力普通拳",
 "EBFS_RED_MY": "精准弹幕 影",
 "EBFS_RED_HLW": "旋风斩 烈",
 "EBFS_RED_XRW": "冰锥术 凝",
 "EBFS_RED_XYW": "暴风雪 界",
 "EBFS_RED_KLW": "投掷匕首 疾",
 "EBFS_RED_MTL": "毒瓶大师",
 "EBFS_HS_FIRE": "黑煞之心 炎",
 "EBFS_HS_ICE": "黑煞之心 冰",
 "EBFS_HS_ELE": "黑煞之心 雷",
 "EBFS_HS_POI": "黑煞之心 毒",
 "EBFS_HS_DARK": "黑煞之心 暗",
 "EBFS_HS_STR": "黑煞之心 力",
 "EBFS_HS_DEX": "黑煞之心 敏",
 "EBFS_HS_MAG": "黑煞之心 智",
 "EBFS_RED_NS": "火球术 彩",
 "EBFS_RED_YXX": "火球术 跃"}"""

                    return epicBuff.decodeFromString<Map<String, String>>().map {
                        Buff (it.value, it.key)
                    }.toMutableList().apply { add(Buff.emptyBuff) }
                }
            }
        }



    }

}
