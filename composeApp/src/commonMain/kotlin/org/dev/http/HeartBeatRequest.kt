package org.dev.http

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.*
import kotlinx.serialization.json.Json
import org.dev.http.BasePostRequest
import org.dev.http.bean.heartBeat.HeartBeatResponse

object HeartBeatRequest
{
    suspend fun heartBeatRequest (cami: String, stateCode: String): HeartBeatResponse
    {
        val httpClient = HttpClient (CIO){
            install (ContentNegotiation){
                register(
                    ContentType.Any, KotlinxSerializationConverter(
                        Json {
                            prettyPrint = true
                            isLenient = true
                            ignoreUnknownKeys = true
                        }
                    )
                )
            }
            install(ContentEncoding) {
                deflate(1.0F)
                gzip(0.9F)
            }
        }
        val response = httpClient.submitForm( url = "http://w.t3yanzheng.com/45C325B2176E35FC",
            parameters{
            append("kami", cami)
            append("statecode", stateCode)
        }){
            headers {
                append("User-Agent", "Apifox/1.0.0 (https://apifox.com)")
            }
        }
        return response.body()
    }
}