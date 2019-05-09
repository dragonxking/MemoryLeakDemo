package demo.jilong.memoryleakdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class LeakCanaryTestJActivity extends Activity implements View.OnClickListener {
    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_canary_test_java);
        findViewById(R.id.handlerBtn).setOnClickListener(this);
        findViewById(R.id.handlerBtnStatic).setOnClickListener(this);
        findViewById(R.id.handlerBtnStatic2).setOnClickListener(this);
        findViewById(R.id.handlerBtnStatic3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.handlerBtn:
                postMessage1();
                break;
            case R.id.handlerBtnStatic:
                handler.postDelayed(new MyRunnable(),10000);
                break;
            case R.id.handlerBtnStatic2:
                handler.postDelayed(new MyRunnable((TextView)findViewById(R.id.handler_java_leak)),10000);
                break;
            case R.id.handlerBtnStatic3:
                handler.postDelayed(new MyRunnableWeak((TextView)findViewById(R.id.handler_java_leak)),10000);
                break;
        }
    }

    /**
     * 匿名内部类持有外部类的强引用 并且activity销毁时 消息还为处理； 出现内存泄漏
     * 参考https://www.jianshu.com/p/f7e83108cd18
     */
    private void postMessage1() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("JavaHandlerLeak","匿名 runnable run");
            }
        },10000);
    }

    static class MyRunnable implements Runnable{
        private TextView textView;

        public MyRunnable() {
        }

        public MyRunnable(TextView textView) {
            this.textView = textView;
        }



        @Override
        public void run() {
            if(textView!=null) {
                textView.setText("update by static InnerClass  runnable complete");
                Log.d("JavaHandlerLeak","static InnerClass runnable run");

            }
        }
    }

    static class MyRunnableWeak implements Runnable{
        private WeakReference<TextView> weakReferenceTv;

        public MyRunnableWeak(TextView textView) {
            this.weakReferenceTv = new WeakReference<TextView>(textView);
        }

        @Override
        public void run() {
            TextView tv = weakReferenceTv.get();
            if(tv!=null) {
                tv.setText("update by static InnerClass  runnable complete");
                Log.d("JavaHandlerLeak","[static InnerClass + WeakReference] runnable run");

            }
        }
    }
}
