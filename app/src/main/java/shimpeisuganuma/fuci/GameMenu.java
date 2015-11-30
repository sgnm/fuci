package shimpeisuganuma.fuci;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Shin on 2015/11/26.
 */
public class GameMenu extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_game);

        ImageView imgDarts = (ImageView)findViewById(R.id.Darts);
        imgDarts.setOnClickListener(this);
        ImageView imgDrums = (ImageView)findViewById(R.id.Drums);
        imgDrums.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Drums:
                Intent intentDrums = new Intent(this, DrumsView.class);
                startActivity(intentDrums);
                break;
            case R.id.Darts:
                Intent intentDarts = new Intent(this, DartsView.class);
                startActivity(intentDarts);
                break;
        }
    }
}
