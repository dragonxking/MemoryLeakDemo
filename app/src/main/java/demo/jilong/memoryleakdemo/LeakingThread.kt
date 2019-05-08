package com.example.leakcanary

import android.util.Log
import android.view.View

class LeakingThread : Thread() {

    val leakedViews = mutableListOf<View>()

    init {
        Log.d("LeakingThread","init")
        name = "Leaking thread"
        start()
    }

    override fun run() {
        synchronized(obj) {
            obj.wait()
        }
    }

    companion object {
        val obj = Object()
        val thread = LeakingThread()
    }
}