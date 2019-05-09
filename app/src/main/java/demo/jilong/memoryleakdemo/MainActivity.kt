package demo.jilong.memoryleakdemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        kotlinact.setOnClickListener {
            this.startActivity(Intent(this,LeakCanaryTestActivity::class.java))
        }
        javaact.setOnClickListener {
            this.startActivity(Intent(this,LeakCanaryTestJActivity::class.java))
        }
    }
}