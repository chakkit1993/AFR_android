package com.apitech.lambda_sensor;

import android.app.Application;
import android.util.Log;

import com.apitech.lambda_sensor.bluetooth.BluetoothSPP;
import com.apitech.lambda_sensor.modelData.DataMonitor;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class cBaseApplication extends Application {


    public BluetoothSPP myBlueComms;
    private  static  String TAG = "BaseApplication";
    private  DataMonitor mDataMonitor;
    @Override
    public void onCreate() {
        super.onCreate();

        myBlueComms = new BluetoothSPP(this);
        mDataMonitor = new DataMonitor();
        myBlueComms.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {
            public void onDeviceDisconnected() {
                Log.i(TAG , "onDeviceDisconnected");

            }

            public void onDeviceConnectionFailed() {
                Log.i(TAG , "onDeviceConnectionFailed");

            }

            public void onDeviceConnected(String name, String address) {
                Log.i(TAG , "onDeviceConnected " + name);

            }
        });

        myBlueComms.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {
            public void onDataReceived(int[] data, String message) {
                boolean packageLost = false;


                // Toast.makeText(getApplicationContext(),message + " msg",Toast.LENGTH_SHORT).show();
                byte[] ascii = message.getBytes(StandardCharsets.US_ASCII);
                String asciiString = Arrays.toString(data);
                String aString = new String(ascii);
                //System.out.println(aString );

                String stats_cj125 =  Integer.toHexString(data[15] & 0xFF) +  Integer.toHexString(data[16] & 0xFF)  ;


                System.out.println(asciiString + " ????   " + stats_cj125) ; // print [74, 97, 118, 97]

                double[] packageData = new double[64];

                int[] header = new int[2];






                header[0] =  data[0];
                header[1] =  data[1];

                if(header[0]  == 'A' && header[1] == 'T'){
                    int index = 0;

                    packageData[2] = (data[3] * 100) +  data[4]; // UB
                    packageData[3] = (data[5] * 100) +  data[6]; // UA
                    packageData[4] = (data[7] * 100) +  data[8]; // UR

                    packageData[5] = (data[9] * 100) +  data[10]; // Bat
                    packageData[6] = (data[11] * 100) +  data[12]; // Lamda
                    packageData[7] = (data[13] * 100) +  data[14]; // AFR

                    packageData[8] = (data[15] * 100); // CJ125_Status

                    // if flag data[17] = 1  => nagative
                    if(data[17] == 0 ){
                        packageData[9] =  ( (data[18] * 100) +  data[19]); // Ip_mA
                    }else{
                        packageData[9] =  -1 * ( (data[18] * 100) +  data[19]); // Ip_mA
                    }

                }
                  packageData[10] = (data[20] * 100) +  data[21]; // DAC

                //System.out.println("packageData");
                // Log.i(TAG , "Data \n" + packageData[2]);

                DataMonitor mData = new DataMonitor();
                mData.setAdcValue_UB((int)packageData[2]);
                mData.setAdcValue_UA((int)packageData[3]);
                mData.setAdcValue_UR((int)packageData[4]);
                mData.setAdcValue_DAC((int)packageData[10]);


                mData.setSupplyVoltage((float) packageData[5] / 100);
                mData.setLAMBDA_VALUE((float)packageData[6] / 100);
                mData.setOXYGEN_CONTENT((float)packageData[7] / 100);


               // String hex = Integer.toHexString(data[15]) + Integer.toHexString(data[16]) ;
                mData.setCJ125_Status(stats_cj125);
                mData.setIp_mA((float)packageData[9] / 100);



                System.out.println( "OXYGEN_CONTENT " + mData.getOXYGEN_CONTENT()) ; // print [74, 97, 118, 97]


                mDataMonitor = mData;
            }
        });
    }

    public DataMonitor getDataMonitor(){

        DataMonitor dataMonitor = new DataMonitor();

        dataMonitor = mDataMonitor;
        return dataMonitor;
    }


    public  BluetoothSPP getBluetooth(){
        return this.myBlueComms;
    }
    public  void BT_send(String data){

        if(myBlueComms.isServiceAvailable()) {
            myBlueComms.send(data, true);
        }

    }
    public  void BT_sendByte(byte[] data){

        if(myBlueComms.isServiceAvailable()) {
            myBlueComms.sendByte(data, 32);
        }

    }

}
