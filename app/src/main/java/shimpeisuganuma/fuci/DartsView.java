package shimpeisuganuma.fuci;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Shin on 2015/11/27.
 */

public class DartsView extends Activity implements View.OnClickListener, Runnable {

    /* tag */
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

    int i = 0;
    int s1, s2, s3, s4, s5, s6;
    int sum1, sum2;
    int j = 0;
    int player = 0;

    TextView strSum1; TextView strSum2;
    TextView score1; TextView score2; TextView score3; TextView score4; TextView score5; TextView score6;

    String str1 = ""; String str2 = "";
    String str = "";

    int n;

    Intent intent;

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
//        int n = (int) (Math.random() * 10) + 1;
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
        i = 0;
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


    @Override
    protected void onPause(){
        super.onPause();

        isRunning = false;
        connectFlg = false;

        try{
            mSocket.close();
        }
        catch(Exception e){}
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

            intent = new Intent(DartsView.this, MenuTop.class);
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
                    str = str.substring(0, str.length() - 1);
                    n = Integer.parseInt(str);
                    setScore(player);
                    i++;
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
