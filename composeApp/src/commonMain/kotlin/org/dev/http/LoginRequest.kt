package org.dev.http

import org.dev.http.bean.CommonLoginBody
import org.dev.http.bean.FailLoginResponse
import org.dev.http.bean.ResponseStatus
import org.dev.http.bean.SuccessLoginResponse

var currentServerType = ServerType.IOS

val loginBody = CommonLoginBody ()

//公共版本 用于将物品存入仓库
var publicRevision = -1

suspend fun login (): ResponseStatus
{

    loginBody.account = "18575520578"
    loginBody.password = "zdjjs123"
    loginBody.loginType = "phone"


    val result = post<CommonLoginBody, FailLoginResponse, SuccessLoginResponse>(url = "https://api.soulknight-prequel.chillyroom.com/UserAuth/Login",
        loginBody)

    when (result::class.java)
    {
        FailLoginResponse::class.java -> {
            val failMsg = result as FailLoginResponse
            val status = ResponseStatus ("失败", "null", "null", "")
            failMsg.msg?.let {
               return status.apply {
                   textContent = it
                   message = failMsg.errorDetails!!
               }
            }
            return status


        }
        SuccessLoginResponse::class.java -> {
            authorization = (result as SuccessLoginResponse).session.token
            return ResponseStatus ("成功", "登陆成功", "登陆成功", "")
        }
    }
    return ResponseStatus ("失败", "未知失败原因", "未知失败原因", "")
}