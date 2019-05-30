package demo.jilong.memoryleakdemo;

import android.app.Activity;
import android.os.*;
import android.view.View;
import android.widget.Toast;

public class HandlerThreadTestActivity extends Activity implements View.OnClickListener {
    final LooperThread looperThread = new LooperThread();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_handler_test);
        looperThread.start();

        findViewById(R.id.looperThread).setOnClickListener(this);
        findViewById(R.id.handlerThread).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.looperThread:
                looperThread.handler.sendEmptyMessage(100);
                break;
            case R.id.handlerThread:
                HandlerThread handlerThread = new HandlerThread("jinlong");
                handlerThread.start();
                Handler handler = new Handler(handlerThread.getLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        long id = Thread.currentThread().getId();
                        System.out.println("HandlerThread runnable id: " + id);
                        Toast.makeText(HandlerThreadTestActivity.this, "HandlerThread ID: " + id, Toast.LENGTH_SHORT).show();
                    }
                });

                break;
            default:
                break;
        }
    }

    class LooperThread extends Thread {
        public Handler handler;

        @Override
        public void run() {
            super.run();
            Looper.prepare();
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    long id = Thread.currentThread().getId();
                    System.out.println("looper thread id: " + id);
                    Toast.makeText(HandlerThreadTestActivity.this, "LooperThreadId: " + id, Toast.LENGTH_SHORT).show();
                }
            };

            Looper.loop();
        }
    }
}
