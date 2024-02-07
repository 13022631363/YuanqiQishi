package org.dev.http.bean.getRoleList

import kotlinx.serialization.Serializable

@Serializable
data class GetRoleListSuccessResponse(
    val characters: List<BasicMsg>,
    val gameData: PublicRevision
){
    @Serializable
    data class BasicMsg (
        val basic : Role
    )

    /**
     * 角色的 id 和 name
     */
    @Serializable
    data class Role (
        val id: String,
        val name: String
    )

    @Serializable
    data class PublicRevision(
        val revision: String
    )
}

@Serializable
data class GetRoleListFailResponse (
    val ok: Boolean,
    val errorCode: Int,
    val message: String,
    val error: Int,
    val msg: String
)


