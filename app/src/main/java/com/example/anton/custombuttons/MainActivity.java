package com.example.anton.custombuttons;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.anton.custombuttons.views.fireButton;
import com.example.anton.custombuttons.views.thunderButton;
import com.example.anton.custombuttons.views.waterButton;

public class MainActivity extends AppCompatActivity {

    private thunderButton mybuttonThunder;
    private waterButton mybuttonWater;
    private fireButton mybuttonFire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mybuttonThunder = (thunderButton) findViewById(R.id.my_custom_button_thunder);
        mybuttonWater = (waterButton) findViewById(R.id.my_custom_button_water);
        mybuttonFire = (fireButton) findViewById(R.id.my_custom_button_fire);

        mybuttonThunder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mybuttonThunder.clickAnimation();
            }
        });

        mybuttonWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mybuttonWater.clickAnimation();
            }
        });

        mybuttonFire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mybuttonFire.clickAnimation();
            }
        });

    }
}
