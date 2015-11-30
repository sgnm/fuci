package shimpeisuganuma.fuci;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

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
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(MenuTop.this, GameMenu.class);
        startActivity(intent);
    }
}
