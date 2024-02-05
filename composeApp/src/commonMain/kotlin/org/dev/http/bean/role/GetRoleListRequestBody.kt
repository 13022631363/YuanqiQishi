package org.dev.http.bean.role

import kotlinx.serialization.Serializable

/**
 * 获取角色列表的请求体
 * 默认参数 {"dateTimeUtcOffsetOfMinutes":480,"firstLaunch":false}
 */
@Serializable
data class GetRoleListRequestBody(
    val dateTimeUtcOffsetOfMinutes: Int,
    val firstLaunch: Boolean
)
