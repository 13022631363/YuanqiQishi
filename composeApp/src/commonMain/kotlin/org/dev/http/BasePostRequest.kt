package org.dev.http

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.dev.http.bean.ResponseStatus
import org.dev.http.util.decodeFromString
import org.dev.http.util.encodeToString

val client = HttpClient (CIO)

var authorization = ""

var character = ""



/**
 * @介绍 基础 post 请求
 */
suspend inline fun <reified B: Any, reified FR: Any, reified SR: Any> post(url: String, body: B): Any
{
    val response = client.request {
        method = HttpMethod.Post
        url(url)
        setBody(body.encodeToString())
        println(body.encodeToString())
        headers.apply {
            append("x-distro-id", currentServerType.uuid)
            append("x-unique-id", "b0cf8709c2fe262c")
            append("Content-Type", "application/json")
            append("x-game-version", "1.0.4")
            append("Cookie", "aliyungf_tc=cda0eae1ad50e83b749872ae0bd2d54ef73baa05df0a8ed5b72d7a57f9869364")
            append("Accept-Encoding", "gzip, deflate")
            append("x-locale", "zh-CN")
            append("x-game-lang", "Chinese")
            append("x-game-id", "5")
            append("Accept-Language", "zh-Hans-CN,  zh-CN")
            append("x-delivery-platform", "origin_taptap")
            append("Accept", "application/json")
            append("Host", "api.soulknight-prequel.chillyroom.com")

            if (authorization != "")
                append("Authorization", "Bearer $authorization")
            if (character != "")
                append("x-character-id", character)
        }
    }
    println(response.bodyAsText())
    return if (response.status == HttpStatusCode.OK)
        response.cast<SR>()
    else response.cast<FR>()
}


/**
 * 登陆 4399 的第一步骤
 * 1. 获取到重定向中的 state 参数
 */
suspend fun login4399 (username: String, password: String, state: String): ResponseStatus
{
    val firstResponse = client.submitForm(
        "https://ptlogin.4399.com/oauth2/loginAndAuthorize.do",
        formParameters = parameters {
            append("username", username)
            append("password", password)
            append("response_type", "TOKEN")
            append("client_id", "e4917859432826d117aae3734532bb89")
            append("state", state)

        }
    ){
    }

    val redirectResponse = client.submitForm  (
        url = "https://m.4399api.com/openapi/oauth-callback.html",
        encodeInQuery = true,
        formParameters = parameters {
            append("gamekey", "49321")
            append ("game_key","138471")
        }
    )

    return redirectResponse.cast<ResponseStatus>()
}

/**
 * 通过 json反序列化为 指定类型
 * 1. 忽略未知 keys
 * 2. 优化输出
 */
suspend inline fun <reified T> HttpResponse.cast (): T
{
    return this.bodyAsText().decodeFromString<T>()
}