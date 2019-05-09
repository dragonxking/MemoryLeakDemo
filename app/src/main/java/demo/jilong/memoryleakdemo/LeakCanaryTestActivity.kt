/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package demo.jilong.memoryleakdemo

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_leak_canary_test.*


class LeakCanaryTestActivity : Activity() {
    private lateinit var leakedView: TextView
    private val handler = Handler()

    companion object {
        var innerInstance: InnerClass? = null
        var staticInnerInstance: StaticInnerClass? = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(demo.jilong.memoryleakdemo.R.layout.activity_leak_canary_test)
        Log.d("LeakCanaryTestActivity", "onCreate")
        leakedView = findViewById<TextView>(demo.jilong.memoryleakdemo.R.id.helper_text)
        init()
    }

    private fun init() {
        val application = application as DemoApplication

        appBtn.setOnClickListener { application.leakedViews.add(leakedView) }
        singletonBtn.setOnClickListener { LeakingSingleton.leakedViews.add(leakedView) }
        threadBtn.setOnClickListener { LeakingThread.thread.leakedViews.add(leakedView) }
        innerClassBtn.setOnClickListener { innerInstance = InnerClass() } //非静态内部类作为静态变量内存泄漏
        staticinnerClassBtn.setOnClickListener { staticInnerInstance = StaticInnerClass() }
        handlerBtn.setOnClickListener {
            //不会出现内存泄漏
            handler.postDelayed({
                Log.d("LeakCanaryTestActivity", "handler.postDelayed lamada runnable")
            },10000L)
        }
        handlerBtn2.setOnClickListener {
            //不会出现内存泄漏
            handler.postDelayed(object : Runnable{
                override fun run() {
                    Log.d("LeakCanaryTestActivity", "handler.postDelayed object : Runnable")
                }
            },10000L)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LeakCanaryTestActivity", "onDestroy")
//        innerInstance = null //非静态内部类作为静态变量内存泄漏  解决方案1 将变量置空
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.d("LeakCanaryTestActivity", "onSaveInstanceState")
    }

    //静态内部类 无法访问外部类成员
    /*class TestStasticInnerClass{
        var test = this@LeakCanaryTestActivity.TAG
    }


    companion object TestStaticInnerObject{
        fun test(){
            var test = this@LeakCanaryTestActivity.TAG
        }
    }

    object TestInnerObject{
        fun test(){
            var test = this@LeakCanaryTestActivity.TAG
        }
    }*/

    //非静态内部类可以访问
    inner class InnerClass

    //非静态内部类作为静态变量内存泄漏 解决方案2 改为静态内部类
    class StaticInnerClass


}
