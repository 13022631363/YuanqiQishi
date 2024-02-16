package org.dev

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import org.dev.compoment.Cami
import org.dev.compoment.NavigationRailSample
import org.dev.http.*
import org.dev.http.bean.getItem.Item
import org.dev.http.bean.sceneEnter.SceneEnterResponse
import org.jetbrains.compose.resources.ExperimentalResourceApi
import kotlin.coroutines.coroutineContext

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
//        Cami(350.dp, 250.dp)
//        FeatureChoose()
        NavigationRailSample ()
    }
}

/**
 * val scope = rememberCoroutineScope()
 *     var color by remember { mutableStateOf(Color.Black) }
 *     var loginStatus by remember { mutableStateOf("") }
 *     MaterialTheme {
 *         Box (modifier = Modifier.background(if (isSystemInDarkTheme()) Color.Black else Color.White)
 *             .fillMaxSize(),
 *             contentAlignment = Alignment.Center){
 *             Column {
 *                 Icon(
 *                     modifier = Modifier.size(120.dp)
 *                         .clickable {
 *
 *                             CoroutineScope(Dispatchers.Default).launch {
 *                                 login(success = {
 *                                     println("成功")
 *
 *                                 }, fail = {
 *                                     println("失败...")
 *                                 })
 *
 *                                 getRoleList(success =  {response ->
 *                                     response.characters.forEach {
 *                                         println("${it.basic.id} ${it.basic.name}")
 *                                     }
 *                                 }, fail = {
 *                                     println("2314123")
 *                                     println(it.message)
 *
 *                                 })
 *
 *                                 getRoleInfo(success = {
 *                                     println(it.revision)
 *                                 }, fail = {
 *                                     println(it.message)
 *                                     println(it.errorCode)
 *                                     println(it.ok)
 *                                     println(authorization)
 *                                 })
 *                             }
 *                         },
 *                     imageVector = Icons.Default.Email,
 *                     contentDescription = null,
 *                     tint = color
 *                 )
 *
 *                 Icon(
 *                     modifier = Modifier.size(120.dp).padding(top = 100.dp)
 *                         .clickable {
 *                             //刷金币
 * //                        CoroutineScope(Dispatchers.Default).launch {
 * //                            castCoins(CastCoinsType.CastCoins10w) {
 * //                                println(it.gold)
 * //                            }
 * //                        }
 *                             //进入地图
 * //                          CoroutineScope (Dispatchers.Main).launch {
 * //                              sceneEnter(SceneEnterLevelType.Common, SceneAreaType.LU_WEI_SHI_DI,
 * //                                  success = {
 * //                                      it
 * //                                  })
 * //                          }
 *
 *                             //刷卡片
 *                             CoroutineScope(Dispatchers.Main).launch {
 *                                 getItemByType(2, Item.Card("野猪王", "E01_B02"),0,
 *                                     success = { response, successAmount ->
 *                                         println("成功响应 -> $response")
 *                                         println("成功刷出的数量 -> $successAmount")
 *                                     },
 *                                     fail = {
 *                                         println(it.msg)
 *                                     })
 *                             }
 *                         },
 *                     imageVector = Icons.Default.Add,
 *                     contentDescription = null,
 *                     tint = color
 *                 )
 *
 *                 Icon(
 *                     modifier = Modifier.size(120.dp).padding(top = 100.dp)
 *                         .clickable {
 *                             //刷羽毛
 *                             CoroutineScope (Dispatchers.Main).launch {
 *                                 getItemByType (2, Item.Feather,0,
 *                                     success = {response, successAmount ->
 *                                         println("成功响应 -> $response")
 *                                         println("成功刷出的数量 -> $successAmount")
 *                                     },
 *                                     fail = {
 *                                         println(it.msg)
 *                                     })
 *                             }
 *                         },
 *                     imageVector = Icons.Default.Add,
 *                     contentDescription =  null,
 *                     tint = color
 *                 )
 *                 Icon(
 *                     modifier = Modifier.size(120.dp).padding(top = 100.dp)
 *                         .clickable {
 *                             //刷装备
 *                             CoroutineScope (Dispatchers.Main).launch {
 *                                 getEquipment(2, 0,SceneEnterResponse.EquipmentPools.EquipmentInfo.EquipMatchConditions(name= "WB_GS_EQB", equipBuffSuit = "EBFS_BOSS_EQB", quality = "1"),
 *                                     sceneAreaType = SceneAreaType.NAN_BU_CAO_YUAN, sceneEnterLevelType = SceneEnterLevelType.Boss,
 *                                     success = {response, successAmount, info ->
 *                                         println("成功响应 -> $response")
 *                                         println("成功刷出的数量 -> $successAmount")
 *                                         println(info)
 *                                     },
 *                                     fail = {
 *                                         println(it.msg)
 *                                     })
 *                             }
 *                         },
 *                     imageVector = Icons.Default.Add,
 *                     contentDescription =  null,
 *                     tint = color
 *                 )
 *             }
 *
 *
 *         }
 *
 *     }
 */