package org.dev.http.bean.getRoleInfo

import kotlinx.serialization.Serializable

/**
 * 单个角色的详情
 * 当前只有 revision
 * 还可以获取角色的装备等等...
 */
@Serializable
data class GetRoleInfoSuccessResponse(
    val revision: String
)

@Serializable
data class GetRoleInfoFailResponse (
    val ok: Boolean,
    val errorCode: String,
    var message: String
)
