package com.apitech.lambda_sensor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apitech.lambda_sensor.bluetooth.BluetoothSPP;
import com.apitech.lambda_sensor.bluetooth.BluetoothState;
import com.apitech.lambda_sensor.bluetooth.DeviceList;
import com.apitech.lambda_sensor.modelData.DataMonitor;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private  static  String TAG = "MainActivity";
    private  static  Integer SETTING_ACTIVITY_REQUEST_CODE = 11 ;
    private  static  Integer TESTING_ACTIVITY_REQUEST_CODE= 12 ;
    private  static  Integer ABOUT_ACTIVITY_REQUEST_CODE= 13 ;
    BluetoothSPP bt;
    private Menu menu;
    private Button btn_onSend;
    private TextView textStatus, textStatusMachine;
    private ImageView img_startButton;
    private  int count = 0;
    private DataMonitor mDataMonitor;
    private TextView textView_card_1,textView_card_2,textView_card_3;


    private final Random RAND = new Random();
    private Gauge gauge1 , gauge2 , gauge3,gauge4;



    @Override
    public void onBackPressed() {
        //super.onBackPressed();

        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if( thread.currentThread().isAlive()){
                            thread.interrupt();
                        }
                        System.exit(0);
                        //finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate");


        btn_onSend = (Button)findViewById(R.id.btn_onSend) ;
        textStatus = (TextView)findViewById(R.id.textStatus);
//        img_startButton = findViewById(R.id.img_startButton);
//        textStatusMachine =  (TextView)findViewById(R.id.textStatusMachine);
        gauge1 = findViewById(R.id.gauge1);
        gauge2= findViewById(R.id.gauge2);
        gauge3 = findViewById(R.id.gauge3);
        textView_card_1 = findViewById(R.id.textView_card_1);
        textView_card_2 = findViewById(R.id.textView_card_2);
        textView_card_3 = findViewById(R.id.textView_card_3);


        bt =  ((cBaseApplication)this.getApplicationContext()).myBlueComms;
//        bt =  new BluetoothSPP(getApplicationContext());


        if(!bt.isBluetoothAvailable()) {
            Toast.makeText(getApplicationContext()
                    , "Bluetooth is not available"
                    , Toast.LENGTH_SHORT).show();
            finish();
        }


        if( thread.currentThread().isAlive()){
            thread.start();
        }



        bt.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceDisconnected() {

//                if( thread.currentThread().isAlive()){
//                    thread.interrupt();
//                }
                textStatus.setText("Status : Not connect");
                mDataMonitor = new DataMonitor();


                menu.clear();
                getMenuInflater().inflate(R.menu.menu_connection, menu);
            }

            public void onDeviceConnectionFailed() {





//                if( thread.currentThread().isAlive()){
//                    thread.interrupt();
//                }

                textStatus.setText("Status : Connection failed");


            }

            public void onDeviceConnected(String name, String address) {


               // Log.i(TAG,"Thread isAlive " + thread.currentThread().isAlive()  + "Thread isDaemon " + thread.currentThread().isDaemon() +"Thread isInterrupted " + thread.currentThread().isInterrupted());

//                if( thread.currentThread().isAlive()){
//                    thread.start();
//                }
                mDataMonitor = new DataMonitor();
                textStatus.setText("Status : Connected to " + name);

                menu.clear();
                getMenuInflater().inflate(R.menu.menu_disconnection, menu);

            }
        });
        bt.setBluetoothStateListener(new BluetoothSPP.BluetoothStateListener() {
            public void onServiceStateChanged(int state) {
                if(state == BluetoothState.STATE_CONNECTED){
                    Log.i(TAG, "State : Connected");
                }
                else if(state == BluetoothState.STATE_CONNECTING)
                    Log.i(TAG, "State : Connecting");
                else if(state == BluetoothState.STATE_LISTEN){
                    // mDataMonitor = new DataMonitor(Diameter,NumOfTooth,Inertia,Factor1);
                    Log.i(TAG, "State : Listen");
                }
                else if(state == BluetoothState.STATE_NONE)
                    Log.i(TAG, "State : None");
            }
        });








    }

    private  void updateData(){


        gauge1.setValue((float)(mDataMonitor.getOXYGEN_CONTENT()));
        gauge2.setValue((float)(mDataMonitor.getLAMBDA_VALUE()));
        gauge3.setValue((float)(mDataMonitor.getSupplyVoltage()));
        // gauge4.setValue((int)(mDataMonitor.getRPM_Roller()/100.0));


        textView_card_1.setText(String.format("%.2f" ,mDataMonitor.getLAMBDA_VALUE() ));
        textView_card_2.setText(String.format("%.2f" ,mDataMonitor.getOXYGEN_CONTENT()));
        textView_card_3.setText(String.format("%.2f" ,mDataMonitor.getSupplyVoltage()));

    }



    final Handler handler = new Handler();
    final Runnable runnable = new Runnable() {
        boolean IsRunningThread = true;
        @Override
        public void run() {
//
//            if (Thread.interrupted()) {
//                IsRunningThread = false;
//                Log.i(TAG ," interrupted run1");
//                return;
//            }



            while (!Thread.currentThread().isInterrupted()) {

                try {


                    if(bt.getServiceState() ==  BluetoothState.STATE_CONNECTED ){


                        byte[] USART1_TX = new byte[32];
                        for ( int i=0; i<32; i++ )
                            USART1_TX[i] = 0;

                        USART1_TX[0] = (byte)0x62;
                        USART1_TX[1] = (byte)0xA0;;
                        USART1_TX[2] = (byte)'s';;
                        USART1_TX[3] = (byte)'t';;
                        USART1_TX[4] = (byte)'a';;
                        USART1_TX[5] = (byte)'r';;
                        USART1_TX[6] = (byte)count++;;
                        int checksum =0;
                        for ( int i = 0; i < 32 - 2; i++)
                        {
                            checksum += USART1_TX[i];
                        }


                        USART1_TX[30] =(byte)checksum;
                        USART1_TX[31] =(byte)0xC9;

                        ((cBaseApplication)getApplicationContext()).BT_sendByte(USART1_TX);



                        //Toast.makeText(getBaseContext() , "Send" ,Toast.LENGTH_SHORT).show();



                    }else{
                        //Toast.makeText(getBaseContext() , "Please Connect Bluetooth" ,Toast.LENGTH_SHORT).show();
                    }


                    if(bt.getServiceState() == BluetoothState.STATE_CONNECTED){
                        mDataMonitor =  ((cBaseApplication)getApplicationContext()).getDataMonitor();
                    }else{
                        mDataMonitor = new DataMonitor();
                    }


                    //Toast.makeText(getBaseContext() , "Data " + mDataMonitor.getAdcValue_UA(),Toast.LENGTH_SHORT).show();
                    //Log.i(TAG,"DATA OUTPUT"  + mDataMonitor.getCJ125_Status() + " ///////////////////////    DATA OUTPUT"  + mDataMonitor.getIp_mA());
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                    Log.i(TAG,"Thread  =>  interrupt" );
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                handler.post(new Runnable(){
                    public void run() {
                        if(mDataMonitor != null){
                            // Log.i(TAG ," handler run2   " + mDataMonitor.getAdcValue_UA()  +" bluetooth state run2   " + bt.getServiceState());
                            updateData();
                        }

                    }

                });
            }
        }

    };
    final Thread thread = new Thread(runnable);

    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_connection, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menu_device_connect) {
            bt.setDeviceTarget(BluetoothState.DEVICE_OTHER);

			//if(bt.getServiceState() == BluetoothState.STATE_CONNECTED)
			    bt.disconnect();

            Intent intent = new Intent(getApplicationContext(), DeviceList.class);
            startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);

        } else if(id == R.id.menu_disconnect) {
            if(bt.getServiceState() == BluetoothState.STATE_CONNECTED)
                bt.disconnect();
        }
        else if(id == R.id.menu_test_sensor) {
            Intent intent = null;
            intent = new Intent(getApplicationContext(), TestingSensorActivity.class);
            startActivityForResult(intent , TESTING_ACTIVITY_REQUEST_CODE );
        }
        else if(id == R.id.menu_about) {
            Intent intent = null;
            intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivityForResult(intent , ABOUT_ACTIVITY_REQUEST_CODE );
        }


        return super.onOptionsItemSelected(item);
    }
    public void onStart() {
        super.onStart();

        if (!bt.isBluetoothEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, BluetoothState.REQUEST_ENABLE_BT);
        } else {
            if(!bt.isServiceAvailable()) {

                bt.setupService();
                bt.startService(BluetoothState.DEVICE_ANDROID);
                //setup();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK){
                String address = data.getExtras().getString(BluetoothState.EXTRA_DEVICE_ADDRESS);
                Log.i(TAG , "Activity Result " + address );
                Log.i(TAG , "Activity Result " + bt.getConnectedDeviceAddress() );


                if(bt != null){
                    bt.connect(address);
                }

            }

        } else if (requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_ANDROID);
                //setup();
            } else {
                Toast.makeText(getApplicationContext()
                        , "Bluetooth was not enabled."
                        , Toast.LENGTH_SHORT).show();
                finish();
            }
        }     // check that it is the SecondActivity with an OK result

    }

    @Override
    protected void onDestroy() {
        if( thread.currentThread().isAlive()){
            thread.interrupt();
        }
        super.onDestroy();
    }
}