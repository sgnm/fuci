package shimpeisuganuma.fuci;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Shin on 2015/11/24.
 */
public class ConnectProcessing extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.processing_connect);

        ImageView imgPros = (ImageView)findViewById(R.id.Connecting);
        imgPros.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        ImageView imgPros = (ImageView)findViewById(R.id.Connecting);
        imgPros.setImageResource(R.drawable.connected);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("TAG", "2000ms delayed");
            }
        }, 2000);
    }
}
