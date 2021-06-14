package com.apitech.lambda_sensor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    String aboutText_1;



    TextView textView_about1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);


        // binding object
        textView_about1 = findViewById(R.id.textView_card_about1) ;


//        aboutText_1 = " ";
//        textView_about1.setText(aboutText_1);

    }
}