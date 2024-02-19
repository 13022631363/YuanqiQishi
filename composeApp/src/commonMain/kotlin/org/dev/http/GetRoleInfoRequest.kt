package org.dev.http

import io.ktor.client.call.*
import org.dev.http.bean.getRoleInfo.GetRoleInfoFailResponse
import org.dev.http.bean.getRoleInfo.GetRoleInfoSuccessResponse

object GetRoleInfoRequest {
    /**
     * 获取指定角色的详细信息请求
     * //todo 后续可能从这里获取更多角色当前信息
     */
    suspend fun getRoleInfo (success: (GetRoleInfoSuccessResponse) -> Unit, fail: (GetRoleInfoFailResponse) -> Unit)
    {
        val response = BasePostRequest.post<String> (url = "https://api.soulknight-prequel.chillyroom.com/Character/Fetch",
            null)

        if (response.status.value == 200)
            success (response.body())
        else
        {
            fail (response.body<GetRoleInfoFailResponse>().apply {
                message = "未选择角色 character 参数未定义"
            })
        }
    }
}