package demo.jilong.memoryleakdemo

import android.app.Application
import android.os.StrictMode
import android.view.View
import com.squareup.leakcanary.LeakCanary

class DemoApplication : Application() {
    val leakedViews = mutableListOf<View>()

    override fun onCreate() {
        super.onCreate()
//        enabledStrictMode()
        initLeakCanary()
    }

    private fun enabledStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyLog()
                .penaltyDeath()
                .build()
        )
    }

    private fun initLeakCanary(){
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        LeakCanary.install(this)
    }
}