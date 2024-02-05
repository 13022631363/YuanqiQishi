package org.dev.http.util

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun json (prettyPrint: Boolean, ignoreUnknownKeys: Boolean): Json
{
    return Json {
        this.prettyPrint = prettyPrint
        this.ignoreUnknownKeys = ignoreUnknownKeys
        this.isLenient = true
    }
}

/**
 * 将对象解析成 json 字符串
 */
inline fun <reified T> T.encodeToString (): String
{
    return json(true, ignoreUnknownKeys = true).encodeToString(this)
}

/**
 * 将 json 字符串转成对象
 * 并忽略未知 keys
 */
inline fun <reified T> String.decodeFromString (): T
{
    return json(true, ignoreUnknownKeys = true).decodeFromString<T>(this)
}

