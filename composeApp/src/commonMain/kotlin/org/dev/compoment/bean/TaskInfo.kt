package org.dev.compoment.bean

import androidx.compose.runtime.MutableState

interface TaskInfo
{
    var openDialog: MutableState<Boolean>

    val taskName: String

    val taskDetail:String

    val amount: Int

    var state: MutableState<RunState>

    var message: MutableState<String>
    suspend fun run ()
}