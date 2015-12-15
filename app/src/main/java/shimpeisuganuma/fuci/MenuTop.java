package shimpeisuganuma.fuci;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Shin on 2015/11/26.
 */
public class MenuTop extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_menu);

        ImageView imgPlay = (ImageView)findViewById(R.id.MenuPlay);
        imgPlay.setOnClickListener(this);

        Intent i = getIntent();
        String error = i.getStringExtra("error");
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MenuTop.this, GameMenu.class);
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        Intent i = getIntent();
        String error = i.getStringExtra("error");
        String success = i.getStringExtra("success");
        if (error != null){
            Toast.makeText(this, "もう一度接続して下さい", Toast.LENGTH_SHORT).show();
        }
        if (success != null){
            Toast.makeText(this, "Bluetoothは接続されています", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction()==KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_BACK:
                    moveTaskToBack(true);
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
