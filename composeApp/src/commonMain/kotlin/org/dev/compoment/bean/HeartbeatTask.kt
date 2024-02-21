package org.dev.compoment.bean

import kotlinx.coroutines.*
import org.dev.http.HeartBeatRequest
import org.dev.http.bean.heartBeat.HeartBeatResponse
import org.dev.http.bean.loginByCami.LoginByCamiSuccessResponse
import org.dev.http.cami.LoginByCami

object HeartbeatTask: Runnable {

    lateinit var stateCode: String
    lateinit var cami: String
    private var currentTime = 1L
    private var isOnce = true
    private var isExecute = true
    override fun run() {

        while (isExecute)
        {
            if (currentTime + 600000L <= System.currentTimeMillis() || isOnce)
            {
                isOnce = false
                currentTime = System.currentTimeMillis()

                runBlocking {
                        val response = HeartBeatRequest.heartBeatRequest (cami, stateCode)
                        println(response.code)
                        if (response.code != "200")
                        {

                            KeyInfo.checked = false
                            KeyInfo.money = false
                            KeyInfo.card = false
                            KeyInfo.stone = false
                            KeyInfo.feather = false
                            KeyInfo.equip = false
                            KeyInfo.task = false
                            this.cancel()
                            isExecute = false
                            return@runBlocking
                        }
                }
            }
        }

    }
}