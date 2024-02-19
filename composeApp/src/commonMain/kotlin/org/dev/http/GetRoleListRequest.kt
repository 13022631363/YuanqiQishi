package org.dev.http

import io.ktor.client.call.*
import org.dev.compoment.bean.GameUser
import org.dev.http.LoginAccountRequest.publicRevision
import org.dev.http.bean.getRoleList.GetRoleListFailResponse
import org.dev.http.bean.getRoleList.GetRoleListRequestBody
import org.dev.http.bean.getRoleList.GetRoleListSuccessResponse

/**
 * 获取角色列表请求
 */
suspend fun getRoleList (success: (GetRoleListSuccessResponse) -> Unit, fail: (GetRoleListFailResponse) -> Unit)
{
    val body = GetRoleListRequestBody (480, false)


        val  response = BasePostRequest.post<GetRoleListRequestBody>(
            "https://api.soulknight-prequel.chillyroom.com/Character/FetchGameData",
            body
        )

        if (response.status.value == 200)
        {
            val result = response.body<GetRoleListSuccessResponse>()
            publicRevision = result.gameData.revision.toInt()
            success(result)
        }else fail(response.body())
}

