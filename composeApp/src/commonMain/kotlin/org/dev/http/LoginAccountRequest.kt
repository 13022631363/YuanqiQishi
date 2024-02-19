package org.dev.http

import com.google.gson.Gson
import io.ktor.client.call.*
import io.ktor.client.statement.*
import org.dev.compoment.bean.GameUser
import org.dev.http.bean.loginAccount.*

object LoginAccountRequest {
    var currentServerType = ServerType.IOS

    //公共版本 用于将物品存入仓库
    var publicRevision = -1

    /**
     * 登陆请求
     */
    suspend fun login (body: CommonLoginRequestBody, success: (SuccessLoginResponse) -> Unit, passwordErrorFail: (FailLogin1Response) -> Unit, accountErrorFail: (FailLogin2Response) -> Unit)
    {
        val response =
            BasePostRequest.post<CommonLoginRequestBody>(url = "https://api.soulknight-prequel.chillyroom.com/UserAuth/Login",
                body)

        if (response.status.value == 200)
        {
            val result = response.body<SuccessLoginResponse>()
            GameUser.authorization = result.session.token
            success(result)
        }else{
            val bodyAsText = response.bodyAsText()
            Gson ().fromJson(bodyAsText, Map::class.java).let{
                if (it["errorCode"] == null)
                    passwordErrorFail (Gson ().fromJson(bodyAsText, FailLogin1Response::class.java))
                else accountErrorFail (Gson ().fromJson(bodyAsText, FailLogin2Response::class.java))
            }
        }
    }
}

