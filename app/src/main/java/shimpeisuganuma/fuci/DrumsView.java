package shimpeisuganuma.fuci;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

/**
 * Created by Shin on 2015/11/29.
 */
public class DrumsView extends Activity {

    Button hat; Button snare; Button clap; Button bass;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_drums);

        hat = (Button) findViewById(R.id.hat);
        snare = (Button) findViewById(R.id.snare);
        clap = (Button) findViewById(R.id.clap);
        bass = (Button) findViewById(R.id.bass);
    }
}
