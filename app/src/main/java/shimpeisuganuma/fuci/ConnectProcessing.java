package shimpeisuganuma.fuci;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Shin on 2015/11/24.
 */
public class ConnectProcessing extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.processing_connect);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView imgPros = (ImageView)findViewById(R.id.Connecting);
                imgPros.setImageResource(R.drawable.connected);
                Log.d("TAG", "2000ms delayed");
                Intent intent = new Intent(ConnectProcessing.this, GameMenu.class);
                startActivity(intent);
            }
        }, 2000);
    }

}
