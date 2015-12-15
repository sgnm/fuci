package shimpeisuganuma.fuci;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

/**
 * Created by Shin on 2015/11/24.
 */
public class ConnectProcessing extends Activity {

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

    /**
     * Action(ステータス表示).
     */
    private static final int VIEW_STATUS = 0;

    /**
     * Action(取得文字列).
     */
    private static final int VIEW_INPUT = 1;

    /**
     * Connect確認用フラグ
     */
    private boolean connectFlg = false;

    /**
     * BluetoothのOutputStream.
     */
    OutputStream mmOutputStream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.processing_connect);

        Intent intent = new Intent(this, MenuTop.class);
        startActivity(intent);

    }
}
