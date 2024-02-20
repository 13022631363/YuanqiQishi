package org.dev.http.bean.gameDifficulty

import kotlinx.serialization.Serializable

@Serializable
data class DifficultyRequestBody(
    val difficulty: Int
)
