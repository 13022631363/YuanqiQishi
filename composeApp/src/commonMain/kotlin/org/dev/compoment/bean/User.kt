package org.dev.compoment.bean

import androidx.compose.runtime.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import org.dev.http.LoginAccountRequest
import org.dev.http.ServerType
import org.dev.http.ServerType.*
import org.dev.http.bean.getRoleList.GetRoleListSuccessResponse
import org.dev.http.bean.loginAccount.CommonLoginRequestBody
import org.dev.http.getRoleList
import org.dev.http.login4399

/**
 * 卡密验证后的用户信息
 */
object User
{
    var id = ""
    var endTime = ""
    var amount = ""
}

object GameUser
{
    private lateinit var loginInfo: CommonLoginRequestBody

    var roleList by mutableStateOf(mutableListOf<GetRoleListSuccessResponse.BasicMsg>())

    var role by mutableStateOf(GetRoleListSuccessResponse.Role ("", ""))

    var confirmRole by mutableStateOf(false)

    var authorization = ""

    var character = ""

    val coroutineScope = CoroutineScope(Dispatchers.Default)

    var isLogin by mutableStateOf(false)

    suspend fun login (username: String, password: String, serverType: ServerType)
    {
        LoginAccountRequest.currentServerType = serverType

        when (serverType)
        {
            TAP_TAP, IOS, H_Y_K_B -> {
                loginInfo = CommonLoginRequestBody ("phone", username, password, )
                LoginAccountRequest.login(loginInfo,
                    success = {
                        isLogin = true
                    },
                    fail = {
                        isLogin = false
                    })
            }
            FOUR_THREE_NINE_NINE -> {
//                login4399(username, password, )
            }
        }
    }

    suspend fun selectRole ()
    {
        if (isLogin)
        {
            getRoleList( success = {
                roleList = it.characters.toMutableList()
            }, fail = {

            })
        }
    }

}