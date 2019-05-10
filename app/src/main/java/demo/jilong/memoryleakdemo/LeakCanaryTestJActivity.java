package demo.jilong.memoryleakdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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
                /**
                 * 泄漏1
                 * 匿名内部类持有外部类的强引用 并且activity销毁时 消息还为处理； 出现内存泄漏
                 * 参考https://www.jianshu.com/p/f7e83108cd18
                 */
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("JavaHandlerLeak","匿名 runnable run");
                    }
                },10000);
                break;
            case R.id.handlerBtnStatic:
                /**
                 * 安全 使用静态内部类 且消息(MyRunnable)中没有对外部类的引用
                 */
                handler.postDelayed(new MyRunnable(),10000);
                break;
            case R.id.handlerBtnStatic2:

                /**
                 * 泄漏 虽然使用静态内部类 但消息(MyRunnable)中持有activity的强引用
                 */
                handler.postDelayed(new MyRunnable((TextView)findViewById(R.id.handler_java_leak)),10000);
                break;
            case R.id.handlerBtnStatic3:
                /**
                 * 安全 使用静态内部类 消息(MyRunnable)中持有view(activity)的弱引用
                 */
                handler.postDelayed(new MyRunnableWeak((TextView)findViewById(R.id.handler_java_leak)),10000);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清空消息队列也可以避免 没有静态内部来和弱引用时出现的内存泄漏
//        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
