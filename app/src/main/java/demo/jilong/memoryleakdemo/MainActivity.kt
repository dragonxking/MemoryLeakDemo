package demo.jilong.memoryleakdemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : FragmentActivity() {

    val DUMMYFRAGMENTTAG= "DummyFragment"
    val DUMMYFRAGMENTTAG2= "DummyFragment2"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        kotlinact.setOnClickListener {
            this.startActivity(Intent(this, LeakCanaryTestActivity::class.java))
        }
        javaact.setOnClickListener {
            this.startActivity(Intent(this, LeakCanaryTestJActivity::class.java))
        }
        handlerActivity.setOnClickListener {
            this.startActivity(Intent(this, HandlerThreadTestActivity::class.java))
        }
        result.setOnClickListener {
            this.startActivityForResult(Intent(this, SubActivity::class.java), 20)
        }
//        fragment.setOnClickListener {
//            this.supportFragmentManager
//                .beginTransaction()
//                .add(R.id.fragment_holder, DummyFragment(),DUMMYFRAGMENTTAG)
//                .commit()
//        }
//        fragment2.setOnClickListener {
//            this.supportFragmentManager
//                .beginTransaction()
//                .add(R.id.fragment_holder2, DummyFragment2(),DUMMYFRAGMENTTAG2)
//                .commit()
//        }

        this.supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_holder, DummyFragment(),DUMMYFRAGMENTTAG)
            .add(R.id.fragment_holder2, DummyFragment2(),DUMMYFRAGMENTTAG2)
            .commit()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("MainActivity", "onActivityResult requestCode = $requestCode resultCode = $resultCode data = ${data?.getStringExtra("name")}")

//        val f = supportFragmentManager.findFragmentByTag(DUMMYFRAGMENTTAG)
//        f?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        Log.d("MainActivity", "onSaveInstanceState");
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.d("MainActivity", "onRestoreInstanceState");
    }

    fun checkWindow(){
        var windManager = applicationContext.getSystemService(WINDOW_SERVICE) as WindowManager
        windManager.
    }

}