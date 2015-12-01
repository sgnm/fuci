package shimpeisuganuma.fuci;

import android.app.Activity;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Shin on 2015/11/29.
 */
public class DrumsView extends Activity implements View.OnClickListener {

    Button btnHat; Button btnSnare; Button btnClap; Button btnBass;
    private SoundPool mSoundPool;
    private int mSoundId[];

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_drums);

        btnHat = (Button) findViewById(R.id.hat);
        btnSnare = (Button) findViewById(R.id.snare);
        btnClap = (Button) findViewById(R.id.clap);
        btnBass = (Button) findViewById(R.id.bass);

        btnHat.setOnClickListener(this);
        btnSnare.setOnClickListener(this);
        btnClap.setOnClickListener(this);
        btnBass.setOnClickListener(this);

        mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        mSoundId = new int[4];
        mSoundId[0] = mSoundPool.load(getApplicationContext(), R.raw.hat, 0);
        mSoundId[1] = mSoundPool.load(getApplicationContext(), R.raw.snare, 0);
        mSoundId[2] = mSoundPool.load(getApplicationContext(), R.raw.clap, 0);
        mSoundId[3] = mSoundPool.load(getApplicationContext(), R.raw.bass, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        mSoundId = new int[4];
        mSoundId[0] = mSoundPool.load(getApplicationContext(), R.raw.hat, 0);
        mSoundId[1] = mSoundPool.load(getApplicationContext(), R.raw.snare, 0);
        mSoundId[2] = mSoundPool.load(getApplicationContext(), R.raw.clap, 0);
        mSoundId[3] = mSoundPool.load(getApplicationContext(), R.raw.bass, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // リリース
        mSoundPool.release();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.hat:
                mSoundPool.play(mSoundId[0], 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            case R.id.snare:
                mSoundPool.play(mSoundId[1], 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            case R.id.clap:
                mSoundPool.play(mSoundId[2], 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            case R.id.bass:
                mSoundPool.play(mSoundId[3], 1.0F, 1.0F, 0, 0, 1.0F);
                break;
        }
    }
}
