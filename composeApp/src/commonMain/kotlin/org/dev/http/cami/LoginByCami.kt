package org.dev.http.cami

import io.ktor.http.*
import org.dev.http.BasePostRequest.submitForm
import org.dev.http.bean.loginByCami.LoginByCamiFailResponse
import org.dev.http.bean.loginByCami.LoginByCamiSuccessResponse


suspend fun loginByCami (kami: String, success: (LoginByCamiSuccessResponse) -> Unit, fail: (LoginByCamiFailResponse) -> Unit)
{
            submitForm(
                parameters {
                    append("kami", kami)
                    append("imei", "asbkaw")
                },
                success = success,
                fail = fail)

}