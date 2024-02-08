package org.dev.http.bean.sceneEnter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 进入地图请求体
 */
@Serializable
data class SceneEnterRequestBody(
    @SerialName ("stageType")
    val sceneLevelType: Int,
    @SerialName ("sceneAreaType")
    val sceneAreaType: Int,
    val layer: Int
)
