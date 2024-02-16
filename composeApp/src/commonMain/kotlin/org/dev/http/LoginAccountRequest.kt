package org.dev.http

import org.dev.http.bean.loginAccount.CommonLoginRequestBody
import org.dev.http.bean.loginAccount.FailLoginResponse
import org.dev.http.bean.loginAccount.SuccessLoginResponse

object LoginAccountRequest {
    var currentServerType = ServerType.IOS

    //公共版本 用于将物品存入仓库
    var publicRevision = -1

    /**
     * 登陆请求
     */
    suspend fun login (success: (SuccessLoginResponse) -> Unit, fail: (FailLoginResponse) -> Unit)
    {

        val loginBody = CommonLoginRequestBody (account = "18575520578", password = "zdjjs123", loginType = "phone" )

        val response =
            post<CommonLoginRequestBody, FailLoginResponse, SuccessLoginResponse>(url = "https://api.soulknight-prequel.chillyroom.com/UserAuth/Login",
                loginBody)

        when (response) {
            is FailLoginResponse -> {
                fail(response)
            }
            is SuccessLoginResponse -> {
                authorization = response.session.token
                success(response)
            }
        }
    }
}

