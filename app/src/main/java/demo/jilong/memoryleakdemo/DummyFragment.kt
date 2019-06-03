package demo.jilong.memoryleakdemo

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.Button


class DummyFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = LayoutInflater.from(this.activity).inflate(R.layout.fragment_dummy,container,false)
        view.findViewById<Button>(R.id.startActivity).setOnClickListener {
            this.startActivityForResult(Intent(this.activity, SubActivity::class.java), 20)
        }
        Log.e("DummyFragment","onActivityResult onCreateView activity: ${this.activity}")

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //有从该fragment执行startActivityForResult 有回调
        Log.e("DummyFragment", "onActivityResult requestCode = $requestCode resultCode = $resultCode data = ${data?.getStringExtra("name")} activity: ${this.activity}")


    }
}