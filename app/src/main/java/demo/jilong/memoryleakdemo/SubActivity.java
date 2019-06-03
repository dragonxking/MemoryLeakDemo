package demo.jilong.memoryleakdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SubActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
        Intent resultIntent = new Intent();
        resultIntent.putExtra("name","jinlong");
        setResult(30,resultIntent);
    }

}
