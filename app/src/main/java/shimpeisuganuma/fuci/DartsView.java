package shimpeisuganuma.fuci;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Shin on 2015/11/27.
 */

public class DartsView extends Activity implements View.OnClickListener {

    int i = 0;
    int s1, s2, s3, s4, s5, s6;
    int sum1, sum2;
    int j = 0;
    int player = 0;

    TextView strSum1; TextView strSum2;
    TextView score1; TextView score2; TextView score3; TextView score4; TextView score5; TextView score6;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_darts);

        Button btn = (Button)findViewById(R.id.btn);
        btn.setOnClickListener(this);
        Button btnReset = (Button)findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);

        strSum1 = (TextView)findViewById(R.id.sum1);
        strSum1.setTextColor(Color.RED);

        strSum2 = (TextView)findViewById(R.id.sum2);
        score1 = (TextView) findViewById(R.id.score1);
        score2 = (TextView) findViewById(R.id.score2);
        score3 = (TextView) findViewById(R.id.score3);
        score4 = (TextView) findViewById(R.id.score4);
        score5 = (TextView) findViewById(R.id.score5);
        score6 = (TextView) findViewById(R.id.score6);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn:
                setScore(player);
                i++;
                break;
            case R.id.btnReset:
                resetScore();
                break;
        }
    }

    public void setScore(int player){
        int n = (int) (Math.random() * 10) + 1;
        switch (player){
            case 0:
                switch (i %= 3) {
                    case 0:
                        score1.setText(String.valueOf(n));
                        s1 = n;
                        break;
                    case 1:
                        score2.setText(String.valueOf(n));
                        s2 = n;
                        break;
                    case 2:
                        score3.setText(String.valueOf(n));
                        s3 = n;
                        calcScore(player);
                        break;
                }
                break;
            case 1:
                switch (i %= 3) {
                    case 0:
                        score4.setText(String.valueOf(n));
                        s4 = n;
                        break;
                    case 1:
                        score5.setText(String.valueOf(n));
                        s5 = n;
                        break;
                    case 2:
                        score6.setText(String.valueOf(n));
                        s6 = n;
                        calcScore(player);
                        break;
                }
                break;
        }
    }

    public void calcScore(int player){
        switch (player){
            case 0:
                int oldSum = s1 + s2 + s3;
                sum1 += oldSum;
                strSum1.setText(String.valueOf(sum1));
                break;
            case 1:
                int oldSum2 = s4 + s5 + s6;
                sum2 += oldSum2;
                strSum2.setText(String.valueOf(sum2));
                break;
        }
    }

    public void resetScore(){
        s1 = 0; s2 = 0; s3 = 0; s4 = 0; s5 = 0; s6 = 0; sum1 = 0; sum2 = 0;
        score1.setText("0"); score2.setText("0"); score3.setText("0"); score4.setText("0"); score5.setText("0"); score6.setText("0");
        strSum1.setText("0"); strSum2.setText("0");
    }

    public void dispPlayer(int player){
        switch (player){
            case 0:
                strSum1.setTextColor(Color.RED);
                strSum2.setTextColor(Color.WHITE);
                break;
            case 1:
                strSum1.setTextColor(Color.WHITE);
                strSum2.setTextColor(Color.RED);
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX(), touchY = event.getY();
        float r = 230;
        float posX = 0;
        float posY = 460;

        double length = Math.hypot(touchX - posX, touchY - posY);

        if (length <= r && event.getAction() == MotionEvent.ACTION_UP) {
            j++;
            player = j % 2;
            dispPlayer(player);
            return true;
        } else {
            return false;
        }
    }
}
