package com.lza.android.inter.process.call

import com.lza.android.inter.process.library.interfaces.IPCNoProguard

/**
 * @author liuzhongao
 * @since 2024/1/14 23:41
 */
interface ProcessService : IPCNoProguard {

    val processName: String

    val processNameNull: String? get() = null

    fun getProcessInfo(): String?

    fun testFunction(path: String, parameters: Int): String

    fun testFunctionNull(path: String, parameters: Int): String? = null

    suspend fun suspendTestFunction(path: String, parameters: Int): String

    suspend fun suspendTestFunctionNull(path: String, parameters: Int): String? = null

    suspend fun suspendPostDataToRemote(arrayParameter: Array<String>): Array<Int>

    suspend fun String.calculateStringCount(): Int
}