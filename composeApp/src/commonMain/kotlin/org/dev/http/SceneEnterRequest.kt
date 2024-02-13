package org.dev.http

import kotlinx.coroutines.delay
import org.dev.http.bean.sceneEnter.SceneEnterRequestBody
import org.dev.http.bean.sceneEnter.SceneEnterResponse
import java.util.Random

/**
 * 进入地图请求
 */
suspend fun sceneEnter (sceneLevelType: SceneEnterLevelType,  sceneAreaType: SceneAreaType, layer: Int = 5, delay: Long, success: (SceneEnterResponse) -> Unit)
{
    delay(delay)

    val body = SceneEnterRequestBody (sceneLevelType.level, sceneAreaType.areaValue, layer)

    val response = post<SceneEnterRequestBody, SceneEnterResponse, SceneEnterResponse>(url = "https://api.soulknight-prequel.chillyroom.com/Scene/SceneEnter",
        body = body) as SceneEnterResponse

    response.let(success)
}

/**
 * 地图级别类型
 */
enum class SceneEnterLevelType (val level: Int)
{
    Common (2),
    Boss (4)
}

/**
 * 地图类型
 */
enum class SceneAreaType (val areaName: String, val areaValue: Int)
{
    MO_FA_SHI_SHEN_DIAN ("魔法石神殿", 8),
    NAN_BU_CAO_YUAN ("南部草原", 9),
    LU_WEI_SHI_DI ("芦苇湿地", 10),
    MI_WU_CAO_YUAN ("", 11),
    CHAO_SHI_RONG_DONG ("潮湿溶洞", 12),
    SHUI_JING_DONG_KU ("水晶洞窟", 13),
    JI_JING_SHAN_GU ("寂静山谷", 14),
    GAN_ZAO_HUANG_MO ("干燥荒漠", 15),
    FEI_QI_CHENG_BAO ("废弃城堡", 16),
    JU_SHI_SHAN_DI ("巨石山地", 17),
    NING_JING_SEN_LIN ("宁静森林", 18),
    RE_LANG_HAI_DAO ("热浪海岛", 19),
    ZHI_RE_HUO_SHAN ("炙热火山", 20),
    YAN_HAN_XUE_SHAN ("严寒雪山", 21),
    GU_DAI_YI_JI ("古代遗迹", 22),
    JI_XIE_DA_LU ("机械大陆", 23),
    ZUI_ZHONG_ZHI_TA ("最终之塔", 24),
    HU_BAO_SHAN_ZHAI ("虎豹山寨", 34),
    SHI_DIAO_YUAN_LIN ("石雕园林", 35),
    WU_CAI_FENG_LIN ("五彩枫林", 36),
    CHANG_CHENG_YI_JI ("长城遗迹", 37),
    SHEN_MI_SHAN_MAI ("神秘山脉", 38);

    companion object
    {
        fun random (): SceneAreaType
        {
            return SceneAreaType.entries.random()
        }
    }
}