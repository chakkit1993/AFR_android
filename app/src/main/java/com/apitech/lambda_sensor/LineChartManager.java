package com.apitech.lambda_sensor;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class LineChartManager {
     private  static  String  TAG = "LinChart Manager";


     /*
     ##########################################################

        Polynomial Regression Variable

     ##########################################################
      */
     int n, N;
    public static ArrayList<String> coeff = new ArrayList<String>();




      /*
     ##########################################################

        End Variable

     ##########################################################
      */



    public void resetChart(LineChart chart){
        chart.fitScreen();
        chart.setData(null);

        chart.notifyDataSetChanged();
        //  chart.clear();
         chart.postInvalidate();

    }




    public LineChart setPositionAxis(LineChart chart , double position , double min , double max ){
        if(position > max )
            position = max ;


        if(position < min )
            position = min;


        LimitLine ll1 = new LimitLine((float)(position/1000.0) , (int)position + " ");
        ll1.setLineWidth(2f);
        ll1.setLineColor(Color.RED);
        ll1.enableDashedLine(3f, 0f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
        ll1.setTextSize(10f);
        ll1.setTextColor(Color.RED);

        XAxis axis = chart.getXAxis();
        axis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        axis.addLimitLine(ll1);

        return chart;
    }


    public LineChart setLimitLine(LineChart chart , float targetAFR){
        if(targetAFR > 20 ){
            targetAFR = 20;
        }

        if(targetAFR < 0 ){
            targetAFR = 0;
        }


        LimitLine ll1 = new LimitLine(targetAFR, targetAFR + " Target");
        ll1.setLineWidth(2f);
        ll1.setLineColor(Color.RED);
        ll1.enableDashedLine(3f, 3f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
        ll1.setTextSize(10f);
        ll1.setTextColor(Color.RED);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(ll1);

        return chart;
    }

    public LineDataSet createSet() {

        LineDataSet set = new LineDataSet(null, "TEST(HP)");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        //set.setColor(ColorTemplate.getHoloBlue());
        set.setColor(Color.BLUE);
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setDrawCircles(false);
        set.setCircleRadius(1f);
        set.setDrawFilled(false);
        set.setFillAlpha(65);
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }
    public LineChart setupChartTestSensor(LineChart chart , int color){



        // chart.setOnChartValueSelectedListener((OnChartValueSelectedListener) this);

        // enable description text
        chart.getDescription().setEnabled(false);

        // enable touch gestures
        chart.setTouchEnabled(false);

        // enable scaling and dragging
        chart.setDragEnabled(false);
        chart.setScaleEnabled(false);
        chart.setDrawGridBackground(true);

        // if disabled, scaling can be done on x- and y-axis separately
        chart.setPinchZoom(false);

        // set an alternative background color
        chart.setBackgroundColor(Color.BLACK);
        chart.setGridBackgroundColor(Color.BLACK);

        LineData data = new LineData();
        data.setValueTextColor(color);

        LineData data2 = new LineData();
        data2.setValueTextColor(color);

        // add empty data
        chart.setData(data);
        chart.setData(data2);

        // get the legend (only possible after setting data)

        Legend l = chart.getLegend();
        //l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_INSIDE);
        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        //l.setTypeface(tfLight);
        l.setTextColor(color);




        XAxis xl = chart.getXAxis();
        //xl.setTypeface(tfLight);
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTextColor(color);
        xl.setDrawGridLines(true);
        // xl.setAxisMaximum(20f);




        xl.setAxisMinimum(0f);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);




        YAxis leftAxis = chart.getAxisLeft();
        //leftAxis.setTypeface(tfLight);

        leftAxis.setTextColor(color);
      //  leftAxis.setAxisMaximum(25f);
        leftAxis.setLabelCount(6);
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawLimitLinesBehindData(true);

//        LimitLine ll1 = new LimitLine(14.5f, "14.5 Target");
//        ll1.setLineWidth(2f);
//        ll1.setLineColor(Color.RED);
//        ll1.enableDashedLine(3f, 3f, 0f);
//        ll1.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);
//        ll1.setTextSize(10f);
//        ll1.setTextColor(Color.RED);
//
//        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
//        leftAxis.addLimitLine(ll1);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);



        return chart;
    }
    public LineDataSet createSetTestSensor(String name , int color) {

        LineDataSet set = new LineDataSet(null, name);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        //set.setColor(ColorTemplate.getHoloBlue());
        set.setColor(color);
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setDrawCircles(false);
        set.setCircleRadius(1f);
        set.setDrawFilled(false);
        set.setFillAlpha(65);
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.WHITE);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }

    public LineDataSet createSet2() {

        LineDataSet set = new LineDataSet(null, "TEST(Torque)");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        // set.setColor(ColorTemplate.getHoloBlue());
        set.setColor(Color.RED);
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setDrawCircles(false);
        set.setCircleRadius(1f);
        set.setDrawFilled(false);
        set.setFillAlpha(65);
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.GREEN);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }
    public LineDataSet createSet3() {

        LineDataSet set = new LineDataSet(null, "TEST(AFR)");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        // set.setColor(ColorTemplate.getHoloBlue());
        set.setColor(Color.BLUE);
        set.setCircleColor(Color.WHITE);
        set.setLineWidth(2f);
        set.setDrawCircles(false);
        set.setCircleRadius(1f);
        set.setDrawFilled(false);
        set.setFillAlpha(65);
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.GREEN);
        set.setValueTextSize(9f);
        set.setDrawValues(false);
        return set;
    }

    public LineDataSet createSet(String name , int color  , ArrayList<Entry> lineEntries , boolean enableDashed) {

        LineDataSet set = new LineDataSet(lineEntries, name);
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        // set.setColor(ColorTemplate.getHoloBlue());
        if(enableDashed)
        set.enableDashedLine(3f, 10f,
                0);

        set.setColor(color);
        set.setCircleColor(Color.BLACK);
        set.setLineWidth(2f);
        set.setDrawCircles(false);
        set.setCircleRadius(2f);
        set.setDrawFilled(false);
        set.setFillAlpha(65);
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setFillColor(ColorTemplate.getHoloBlue());
        set.setHighLightColor(Color.rgb(244, 117, 117));
        set.setValueTextColor(Color.GREEN);
        set.setValueTextSize(8f);
        set.setDrawValues(false);

        return set;
    }

    public Entry findMaximumDataEntry(ArrayList<Entry>  _series ){
       Entry result = null;
        double max_value = 0;
        int positionMax  = 0;
        for (int i = 0 ; i < _series.size(); i++){
            double temp =  _series.get(i).getY();
            if(temp > max_value){
                max_value = temp;
                positionMax = i;
            }
        }

        Log.i(TAG,"Max Value " + max_value  + " Index "+ _series.get(positionMax).getX());
        result = new Entry( _series.get(positionMax).getX() , (float)max_value);

        return result;
    }

    public Entry findMinimumDataEntry(ArrayList<Entry>  _series ){
        Entry result = null;
        double max_value = 0;
        int positionMax  = 0;
        for (int i = 0 ; i < _series.size(); i++){
            double temp =  _series.get(i).getY();
            if(temp > max_value){
                max_value = temp;
                positionMax = i;
            }
        }

        Log.i(TAG,"Max Value " + max_value  + " Index "+ _series.get(positionMax).getX());
        result = new Entry( (float)max_value,_series.get(positionMax).getX());

        return result;
    }





}
