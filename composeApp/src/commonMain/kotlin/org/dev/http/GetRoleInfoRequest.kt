package org.dev.http

import org.dev.http.bean.getRoleInfo.GetRoleInfoFailResponse
import org.dev.http.bean.getRoleInfo.GetRoleInfoSuccessResponse

suspend fun getRoleInfo (success: (GetRoleInfoSuccessResponse) -> Unit, fail: (GetRoleInfoFailResponse) -> Unit)
{
    val response = post<String,GetRoleInfoFailResponse, GetRoleInfoSuccessResponse> (url = "https://api.soulknight-prequel.chillyroom.com/Character/Fetch",
        null)

    when (response)
    {
        is GetRoleInfoSuccessResponse -> success (response)
        is GetRoleInfoFailResponse -> fail (response.apply {
            message = "未选择角色 character 参数未定义"
        })
    }
}