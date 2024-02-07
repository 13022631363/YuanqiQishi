package org.dev.http

import org.dev.http.bean.getRoleList.GetRoleFailResponse
import org.dev.http.bean.getRoleList.GetRoleListRequestBody
import org.dev.http.bean.getRoleList.GetRoleSuccessResponse

/**
 * 获取角色列表请求
 * //todo 后续可能从这里获取更多角色当前信息
 */
suspend fun getRoleList (success: (GetRoleSuccessResponse) -> Unit, fail: (GetRoleFailResponse) -> Unit)
{
    val body = GetRoleListRequestBody (480, false)


        val  response = post<GetRoleListRequestBody, GetRoleFailResponse, GetRoleSuccessResponse>(
            "https://api.soulknight-prequel.chillyroom.com/Character/FetchGameData",
            body
        )



        when (response) {
            is GetRoleFailResponse -> {
                fail(response)
            }
            is GetRoleSuccessResponse -> {
                publicRevision = response.gameData.revision.toInt()
                character = response.characters[0].basic.id //todo 暂时
                success(response)

            }
        }

}

