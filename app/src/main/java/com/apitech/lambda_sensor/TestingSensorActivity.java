package com.apitech.lambda_sensor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.apitech.lambda_sensor.modelData.DataMonitor;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;


public class TestingSensorActivity extends AppCompatActivity {
    private  static  String TAG = "TestingSonsorActivity";
    private LineChartManager chartManager;
    private LineChart chart1,chart2,chart3,chart4,chart5;
    private DataMonitor mDataMonitor;
    private TextView tv_chartTest1 , tv_chartTest2, tv_chartTest3,tv_chartTest4,tv_chartTest5;
    private int[] colors;
    private  String[] nameChart = {"UB ADC" , "UA ADC" , "UR ADC" ,"ADC 2" ,"ADC 3"};

    @Override
    public void onBackPressed() {
        // Create the result Intent and include the MAC address
        Intent intent = new Intent();
        // Set result and finish this Activity
        setResult(Activity.RESULT_OK, intent);
        finish();
        super.onBackPressed();
    }
    @Override
    protected void onStop() {
        Log.i(TAG ,"onStop" );
        super.onStop();
    }

    @Override
    protected void onPause() {

        Log.i(TAG ,"onPause" );
        super.onPause();
    }

    public void onDestroy() {
        if(Thread.currentThread().isAlive())
            thread.interrupt();
        Log.i(TAG ,"onDestroy" );
        super.onDestroy();
        //  bt.stopService();
    }
    public void onStart() {
        super.onStart();

        thread.start();
        Log.i(TAG ,"onStart" );

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_sensor);

        colors = getApplicationContext().getResources().getIntArray(R.array.colorsLineChart);

        chart1 = findViewById(R.id.lineChartTest1);
        chart2 = findViewById(R.id.lineChartTest2);
        chart3 = findViewById(R.id.lineChartTest3);
        chart4 = findViewById(R.id.lineChartTest4);
        chart5 = findViewById(R.id.lineChartTest5);


        chartManager = new LineChartManager();

        chart1 = chartManager.setupChartTestSensor(chart1 , colors[0]);
        chart2 = chartManager.setupChartTestSensor(chart2, colors[1]);
        chart3 = chartManager.setupChartTestSensor(chart3, colors[2]);
        chart4 = chartManager.setupChartTestSensor(chart4, colors[3]);
        chart5 = chartManager.setupChartTestSensor(chart5, colors[4]);

        tv_chartTest1 = findViewById(R.id.tv_chartTest1);
        tv_chartTest1.setTextColor(colors[0]);
        tv_chartTest2 = findViewById(R.id.tv_chartTest2);
        tv_chartTest2.setTextColor(colors[1]);
        tv_chartTest3 = findViewById(R.id.tv_chartTest3);
        tv_chartTest3.setTextColor(colors[2]);
        tv_chartTest4 = findViewById(R.id.tv_chartTest4);
        tv_chartTest4.setTextColor(colors[3]);
        tv_chartTest5 = findViewById(R.id.tv_chartTest5);
        tv_chartTest5.setTextColor(colors[4]);


    }
    private void addEntryChart(DataMonitor inComming ,LineChart chart) {

        DataMonitor dataIncomming = inComming;
        //Log.i(TAG,"addEntry");
        //  LineData linedata = chart1.getLineData();

        LineData linedata = chart.getData();
        int chartPosition = 0;

        if(chart == this.chart1){
            chartPosition = 0;
        }else if(chart ==   this.chart2){
            chartPosition =1;
        }else if(chart ==  this.chart3){
            chartPosition =2;
        }else if(chart ==  this.chart4){
            chartPosition =3;
        }else if(chart ==  this.chart5){
            chartPosition =4;
        }
        else{
            chartPosition =0;
        }
        //List<ILineDataSet>   dataSetList =  linedata.getDataSets();
        if (linedata == null) {
            linedata = new LineData();
            chart.setData(linedata);
        }


        if (linedata != null) {


            //linedata.addDataSet(dataSetList);
            ILineDataSet set = linedata.getDataSetByIndex(0);
            // set.addEntry(...); // can be called as well
            if (set == null) {
                set = chartManager.createSetTestSensor(nameChart[chartPosition],colors[chartPosition]);
                //Log.i(TAG,"create set ");
                linedata.addDataSet(set);
            }

            double xPoint = set.getEntryCount();
            double yPoint = inComming.getAdcValue_UB();


            YAxis leftAxis = chart.getAxisLeft();

            switch (chartPosition){
                case 0 :   yPoint =inComming.getAdcVolt_UB();
                    leftAxis.setLabelCount(4);
                    leftAxis.setAxisMaximum( 20 );
                    break;
                case 1 :    yPoint =inComming.getAdcVolt_UA();
                    leftAxis.setLabelCount(2);
                    leftAxis.setAxisMaximum( 6 );
                    break;
                case 2 :     yPoint =inComming.getAdcVolt_UR();
                    leftAxis.setLabelCount(2);
                   leftAxis.setAxisMaximum( 6 );
                    break;
                case 3 :     yPoint =inComming.getIp_mA();
                    leftAxis.setLabelCount(2);
                    leftAxis.setAxisMaximum( 10 );
                    break;
                case 4:     yPoint =inComming.getAdcVolt_DAC();
                    leftAxis.setLabelCount(2);
                    leftAxis.setAxisMaximum( 6 );
                    break;
            }



            linedata.addEntry(new Entry(    checkFloat(xPoint) ,    checkFloat(yPoint)), 0);
            linedata.notifyDataChanged();

            chart.notifyDataSetChanged();
            chart.setVisibleXRangeMaximum(50);
            chart.moveViewToX( linedata.getEntryCount()+ 5);

        }
    }


    private float checkFloat(double value){
        if(value < 0.0 )
            return  0;
        else
            return (float)value;
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
                    mDataMonitor =  ((cBaseApplication)getApplicationContext()).getDataMonitor();
                    Thread.sleep(100);
                }
                catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    e.printStackTrace();
                }
                handler.post(new Runnable(){
                    public void run() {
                        Log.i(TAG ," Thread test sensor" + mDataMonitor.getSupplyVoltage());
                        updateData();
                        //updateFab2();
                        //updateFab1();
                    }
                });
            }
        }

    };
    final Thread thread = new Thread(runnable);


    private void updateData(){
        addEntryChart(mDataMonitor , chart1);
        addEntryChart(mDataMonitor , chart2);
        addEntryChart(mDataMonitor , chart3);
        addEntryChart(mDataMonitor , chart4);
        addEntryChart(mDataMonitor , chart5);

        tv_chartTest1.setText(String.format(" %.2f V" ,mDataMonitor.getAdcVolt_UB() ));
        tv_chartTest2.setText(String.format(" %.2f V" ,mDataMonitor.getAdcVolt_UA() ));
        tv_chartTest3.setText(String.format(" %.2f V " ,mDataMonitor.getAdcVolt_UR() ));
        tv_chartTest4.setText(String.format(" %.2f mA " ,mDataMonitor.getIp_mA() ));
        tv_chartTest5.setText(String.format(" %.2f V " ,mDataMonitor.getAdcVolt_DAC() ));

    }
}
