package demo.jilong.memoryleakdemo

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.R.attr.data
import android.widget.Button


class DummyFragment2 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = TextView(this.context)
        view.layoutParams = ViewGroup.LayoutParams(-1,-1)
        view.setBackgroundColor(Color.BLUE)
        view.text = "DummyFragment2"
        view.setTextColor(Color.WHITE)
        view.gravity = Gravity.CENTER
        view.textSize = 40f
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //没有从该fragment执行startActivityForResult 也就没有回调
        Log.e("DummyFragment2", "onActivityResult requestCode = $requestCode resultCode = $resultCode data = ${data?.getStringExtra("name")} activity: ${this.activity}")

    }
}