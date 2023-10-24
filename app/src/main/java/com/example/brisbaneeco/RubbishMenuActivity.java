package com.example.brisbaneeco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class RubbishMenuActivity extends AppCompatActivity {

    private Button BinLocations;
    private Button PickUp;
    private Button RubbishInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rubbish_menu);

        setViewIds();

        BinLocations.setOnClickListener(view -> {
            Intent intent = new Intent(RubbishMenuActivity.this, MapsActivity.class);
            startActivity(intent);
        });

        PickUp.setOnClickListener(view -> {
            Intent intent = new Intent(RubbishMenuActivity.this, PickupDateActivity.class);
            startActivity(intent);
        });

        RubbishInfo.setOnClickListener(view -> {
            Intent intent = new Intent(RubbishMenuActivity.this, RubbishInfoActivity.class);
            startActivity(intent);
        });
    }

    private void setViewIds()
    {
        BinLocations = findViewById(R.id.BinLocationsBtn);
        PickUp = findViewById(R.id.PickUpBtn);
        RubbishInfo = findViewById(R.id.RubbishLogBtn);
    }
}