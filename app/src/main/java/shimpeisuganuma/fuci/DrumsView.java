package shimpeisuganuma.fuci;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Shin on 2015/11/29.
 */
public class DrumsView extends Activity implements View.OnClickListener, Runnable {

    private static final String TAG = "BluetoothSample";

    /* Bluetooth Adapter */
    private BluetoothAdapter mAdapter;

    /* Bluetoothデバイス */
    private BluetoothDevice mDevice;

    /* Bluetooth UUID(固定) */
    private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    /* デバイス名 環境に合わせて変更*/
    private final String DEVICE_NAME = "HC-06";

    /* Soket */
    private BluetoothSocket mSocket;

    /* Thread */
    private Thread mThread;

    /* Threadの状態を表す */
    private boolean isRunning;

    /** Action(ステータス表示). */
    private static final int VIEW_STATUS = 0;

    /** Action(取得文字列). */
    private static final int VIEW_INPUT = 1;

    /** Connect確認用フラグ */
    private boolean connectFlg = false;

    /** BluetoothのOutputStream. */
    OutputStream mmOutputStream = null;

    Button btnHat; Button btnSnare; Button btnClap; Button btnBass;
    private SoundPool mSoundPool;
    private int mSoundId[];

    String str1 = ""; String str2 = "";
    String str = "";

    int n;

    Intent intent;

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

        // Bluetoothのデバイス名を取得
        // デバイス名は、RNBT-XXXXになるため、
        // DVICE_NAMEでデバイス名を定義
        mAdapter = BluetoothAdapter.getDefaultAdapter();
//        mStatusTextView.setText("SearchDevice");
        Set< BluetoothDevice > devices = mAdapter.getBondedDevices();
        for ( BluetoothDevice device : devices){

            if(device.getName().equals(DEVICE_NAME)){
//                mStatusTextView.setText("find: " + device.getName());
                Log.d("BLE", "find: " + device.getName());
                mDevice = device;
            }
        }

        mThread = new Thread(this);
        // Threadを起動し、Bluetooth接続
        isRunning = true;
        mThread.start();
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

        isRunning = false;
        connectFlg = false;

        try{
            mSocket.close();
        }
        catch(Exception e){}
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

    public void playSound(int id){
        switch (id){
            case 1:
                mSoundPool.play(mSoundId[0], 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            case 2:
                mSoundPool.play(mSoundId[1], 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            case 3:
                mSoundPool.play(mSoundId[2], 1.0F, 1.0F, 0, 0, 1.0F);
                break;
            case 4:
                mSoundPool.play(mSoundId[3], 1.0F, 1.0F, 0, 0, 1.0F);
                break;
        }
    }

    // スレッド処理(connectボタン押下後に実行)
    @Override
    public void run() {
        InputStream mmInStream = null;

        Message valueMsg = new Message();
        valueMsg.what = VIEW_STATUS;
        valueMsg.obj = "connecting...";
        mHandler.sendMessage(valueMsg);

        try{

            // 取得したデバイス名を使ってBluetoothでSocket接続
            mSocket = mDevice.createRfcommSocketToServiceRecord(MY_UUID);
            mSocket.connect();
            mmInStream = mSocket.getInputStream();
            mmOutputStream = mSocket.getOutputStream();

            // InputStreamのバッファを格納
            byte[] buffer = new byte[1024];

            // 取得したバッファのサイズを格納
            int bytes;
            valueMsg = new Message();
            valueMsg.what = VIEW_STATUS;
            valueMsg.obj = "connected.";
            mHandler.sendMessage(valueMsg);

//            intent = new Intent(DartsView.this, DartsView.class);
//            intent.putExtra("success", "接続に成功しました");
//            startActivity(intent);

            connectFlg = true;

            while(isRunning){

                // InputStreamの読み込み
                bytes = mmInStream.read(buffer);
                Log.i(TAG,"bytes="+bytes);
                // String型に変換
                String readMsg = new String(buffer, 0, bytes);

                // null以外なら表示
                if(readMsg.trim() != null && !readMsg.trim().equals("")){
                    Log.i(TAG,"value="+readMsg.trim());

                    valueMsg = new Message();
                    valueMsg.what = VIEW_INPUT;
                    valueMsg.obj = readMsg;
                    mHandler.sendMessage(valueMsg);
                }
            }
        }
        // エラー処理
        catch(Exception e){

            valueMsg = new Message();
            valueMsg.what = VIEW_STATUS;
            valueMsg.obj = "Error1:" + e;
            mHandler.sendMessage(valueMsg);
            Log.d("BLE", "接続に失敗しました");

            intent = new Intent(DrumsView.this, MenuTop.class);
            intent.putExtra("error", "接続に失敗しました");
            startActivity(intent);

            try{
                mSocket.close();
            }catch(Exception ee){}
            isRunning = false;
            connectFlg = false;
        }
    }

    /**
     * 描画処理はHandlerでおこなう
     */
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int action = msg.what;
            String msgStr = (String)msg.obj;

            if(action == VIEW_INPUT){
//                mInputTextView.setText(msgStr);

                if (msgStr.length() == 1){
                    str1 = msgStr;
                    str += str1;
                }else if (msgStr.length() == 2){
                    str2 = msgStr;
                    str += str2;
                }

                if (str.length() > 2) {
                    Log.d("BLE", "input value: " + str);
                    char c = str.charAt(str.length() - 1);
                    n = Integer.parseInt(String.valueOf(c));
                    playSound(n);
                    str = "";
                }
            }
            else if(action == VIEW_STATUS){
//                mStatusTextView.setText(msgStr);
                Log.d("BLE", "status: " + msgStr);
            }
        }
    };

}
