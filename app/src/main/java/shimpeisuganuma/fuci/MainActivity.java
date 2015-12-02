package shimpeisuganuma.fuci;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private static final int REQUEST_ENABLE_BLUETOOTH = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BluetoothAdapter取得
        BluetoothAdapter Bt = BluetoothAdapter.getDefaultAdapter();
        if(!Bt.equals(null)){
            //Bluetooth対応端末の場合の処理
            Log.d("BLE", "Bluetoothがサポートされてます。");
        }else{
            //Bluetooth非対応端末の場合の処理
            Log.d("BLE", "Bluetoothがサポートれていません。");
            finish();
        }

        boolean btEnable = Bt.isEnabled();
        if(btEnable == true){
            //BluetoothがONだった場合の処理
            Log.d("BLE", "BLEはONです");
            Intent intent = new Intent(MainActivity.this, ConnectProcessing.class);
            startActivity(intent);
        }else{
            //OFFだった場合、ONにすることを促すダイアログを表示する画面に遷移
            Log.d("BLE", "BLEはOFFです、ONにしてください");
            Intent btOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(btOn, REQUEST_ENABLE_BLUETOOTH);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int ResultCode, Intent date){
        //ダイアログ画面から結果を受けた後の処理を記述
        if(requestCode == REQUEST_ENABLE_BLUETOOTH){
            if(ResultCode == Activity.RESULT_OK){
                //BluetoothがONにされた場合の処理
                Log.d("BLE", "BluetoothをONにしてもらえました。");
                Intent intent = new Intent(MainActivity.this, ConnectProcessing.class);
                startActivity(intent);
            }else{
                Log.d("BLE", "BluetoothがONにしてもらえませんでした。");
                Toast.makeText(this, "BluetoothをONにして、再起動してください。", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
