package shimpeisuganuma.fuci;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

/**
 * Created by Shin on 2015/11/24.
 */
public class ConnectProcessing extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.processing_connect);

        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        Log.v("width",          String.valueOf(display.getWidth()));       // 非推奨
        Log.v("height",         String.valueOf(display.getHeight()));

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView imgPros = (ImageView)findViewById(R.id.Connecting);
                imgPros.setImageResource(R.drawable.connected);
                Log.d("TAG", "2000ms delayed");
                Intent intent = new Intent(ConnectProcessing.this, MenuTop.class);
                startActivity(intent);
            }
        }, 2000);
    }

}
