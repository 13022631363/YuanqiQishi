package org.dev.http

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


        val  response = post<GetRoleListRequestBody, GetRoleListFailResponse, GetRoleListSuccessResponse>(
            "https://api.soulknight-prequel.chillyroom.com/Character/FetchGameData",
            body
        )



        when (response) {
            is GetRoleListFailResponse -> {
                fail(response)
            }
            is GetRoleListSuccessResponse -> {
                publicRevision = response.gameData.revision.toInt()
                success(response)

            }
        }

}

