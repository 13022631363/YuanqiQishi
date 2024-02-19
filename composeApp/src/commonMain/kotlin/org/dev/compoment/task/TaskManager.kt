package org.dev.compoment.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.dev.compoment.bean.RunState
import org.dev.compoment.bean.TaskInfo

object TaskManager
{
    val displayTask = mutableListOf<TaskInfo>()

    var displayTaskSize by mutableStateOf(displayTask.size)

    private val allTask = mutableSetOf<TaskInfo>()

    private val waitTask = mutableSetOf<TaskInfo> ()

    var taskSizeIsZero by mutableStateOf(true)

    private var isRunning = false

    private val coroutineScope = CoroutineScope(Dispatchers.Default)


    fun startTask ()
    {
        coroutineScope.launch {
            if (isRunning) return@launch
            val needRelease = mutableSetOf<TaskInfo>()
            if (allTask.isNotEmpty())
            {
                allTask.forEach {
                    isRunning = true
                    it.run()
                    if (it.state.value == RunState.Finish || it.state.value == RunState.Error)
                        needRelease.add(it)
                }
                removeAll(needRelease)
            }
            isRunning = false
            if (waitTask.isNotEmpty())
            {
                allTask.addAll(waitTask)
                waitTask.clear()
                startTask()
            }
        }

    }

    fun addTask (task: TaskInfo)
    {
        displayTask.add(task)
        if (isRunning)
            waitTask.add(task)
        else  allTask.add(task)

        displayTaskSize = displayTask.size
        updateSize ()
    }

    fun releaseTask (task: TaskInfo)
    {
        displayTask.remove(task)
        allTask.remove(task)
        displayTaskSize = displayTask.size
        updateSize()
    }

    private fun removeAll (tasks: MutableSet<TaskInfo>)
    {
        tasks.forEach {
            allTask.remove(it)
        }
        updateSize()
    }

    private fun updateSize ()
    {
        taskSizeIsZero = allTask.isEmpty()
    }


}