package com.lza.android.inter.process.call

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.lza.android.inter.process.library.ProcessCenter
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSendAction: View = findViewById(R.id.btn_send_action)
        btnSendAction.setOnClickListener { v ->
            val intent = Intent(v.context, LibraryActivity::class.java)
            v.context.startActivity(intent)
        }

        findViewById<View>(R.id.btn_interface_property_call).setOnClickProcessInterfaceListener { view ->
            val startTimeStamp = SystemClock.elapsedRealtimeNanos()
            val remoteResult = this.processName
            Log.i("MainActivity", "remoteProcessName: ${remoteResult}")
            Log.i("MainActivity", "remote call cost: ${(SystemClock.elapsedRealtimeNanos() - startTimeStamp) / 1_000_000L}ms")
        }

        findViewById<View>(R.id.btn_interface_normal_function_call).setOnClickProcessInterfaceListener { view ->
            val startTimeStamp = SystemClock.elapsedRealtimeNanos()
            val remoteResult = this.testFunction(path = "122312", 12882198)
            Log.i("MainActivity", "testFunction: ${remoteResult}")
            Log.i("MainActivity", "remote call cost: ${(SystemClock.elapsedRealtimeNanos() - startTimeStamp) / 1_000_000L}ms")
        }

        findViewById<View>(R.id.btn_interface_suspend_function_call).setOnClickProcessInterfaceListener { view ->
            this@MainActivity.lifecycleScope.launch {
                val startTimeStamp = SystemClock.elapsedRealtimeNanos()
                val remoteResult = this@setOnClickProcessInterfaceListener.suspendTestFunction(path = "122312", 12882198)
                Log.i("MainActivity", "suspendTestFunction: ${remoteResult}")
                Log.i("MainActivity", "remote call cost: ${(SystemClock.elapsedRealtimeNanos() - startTimeStamp) / 1_000_000L}ms")
            }
        }

        findViewById<View>(R.id.btn_interface_property_call_null).setOnClickProcessInterfaceListener { view ->
            val startTimeStamp = SystemClock.elapsedRealtimeNanos()
            val remoteResult = this.processNameNull
            Log.i("MainActivity", "property return null: ${remoteResult}")
            Log.i("MainActivity", "remote call cost: ${(SystemClock.elapsedRealtimeNanos() - startTimeStamp) / 1_000_000L}ms")
        }

        findViewById<View>(R.id.btn_interface_normal_function_call_null).setOnClickProcessInterfaceListener { view ->
            val startTimeStamp = SystemClock.elapsedRealtimeNanos()
            val remoteResult = this.testFunctionNull(path = "122312", 12882198)
            Log.i("MainActivity", "testFunction return null: ${remoteResult}")
            Log.i("MainActivity", "remote call cost: ${(SystemClock.elapsedRealtimeNanos() - startTimeStamp) / 1_000_000L}ms")
        }

        findViewById<View>(R.id.btn_interface_suspend_function_call_null).setOnClickProcessInterfaceListener { view ->
            this@MainActivity.lifecycleScope.launch {
                val startTimeStamp = SystemClock.elapsedRealtimeNanos()
                val remoteResult = this@setOnClickProcessInterfaceListener.suspendTestFunctionNull(path = "122312", 12882198)
                Log.i("MainActivity", "suspendTestFunction return null: ${remoteResult}")
                Log.i("MainActivity", "remote call cost: ${(SystemClock.elapsedRealtimeNanos() - startTimeStamp) / 1_000_000L}ms")
            }
        }

    }

    private inline fun View.setOnClickProcessInterfaceListener(crossinline onClick: ProcessService.(View) -> Unit) {
        val processService = ProcessCenter.getService(ProcessConst.KEY_LIBRARY_PROCESS, ProcessService::class.java, LibraryProcessServiceImpl)
        this.setOnClickListener { view ->
            processService.onClick(view)
        }
    }
}