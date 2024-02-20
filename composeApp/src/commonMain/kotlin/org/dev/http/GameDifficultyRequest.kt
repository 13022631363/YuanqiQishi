package org.dev.http

import org.dev.http.bean.gameDifficulty.DifficultyRequestBody

object GameDifficultyRequest
{
    suspend fun setDifficulty (value: Int)
    {
        BasePostRequest.post("https://api.soulknight-prequel.chillyroom.com/Difficulty/SetDifficulty",
            DifficultyRequestBody (value))
    }
}