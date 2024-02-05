package org.dev.http

/**
 * @介绍 渠道服务器类型
 */
enum class ServerType (val id: String, val serverName: String, val uuid: String){
    TAP_TAP("1","taptap", "2e9a2011-2a42-46e1-b648-42a8c598b43b"),
    H_Y_K_B("2","好游快爆", "3e414bc2-063d-4a62-a347-9b9848eb9ecf"),
    IOS("3","ios", "a24d3e9f-4fdc-4b6e-935a-74aebb715173"),
    FOUR_THREE_NINE_NINE("4","4399", "39e85428-7278-4836-b839-8355bb8a5767");

    companion object {
        @JvmStatic
        fun idOf (id: String): ServerType?
        {
            entries.forEach {
                if (id == it.id)
                    return it
            }
            return null
        }

        fun serverNameOf (serverName: String): ServerType?
        {
            entries.forEach {
                if (serverName == it.serverName)
                    return it
            }
            return null
        }

    }
}