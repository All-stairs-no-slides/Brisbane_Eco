package com.example.brisbaneeco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class CameraActivity extends AppCompatActivity {


    private Button AddP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        setviewIds();

        AddP.setOnClickListener(view -> {
            Intent intent = new Intent(CameraActivity.this, AddPoints.class);
            startActivity(intent);
        });
    }
    private void setviewIds()
    {
        AddP = findViewById(R.id.btnLogin);
    }
}