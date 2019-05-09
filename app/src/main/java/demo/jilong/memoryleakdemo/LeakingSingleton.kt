package demo.jilong.memoryleakdemo

import android.view.View

object LeakingSingleton {
    val leakedViews = mutableListOf<View>()
}