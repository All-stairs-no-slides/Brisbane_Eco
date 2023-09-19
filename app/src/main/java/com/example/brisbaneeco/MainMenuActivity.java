package com.example.brisbaneeco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    private Button Rubbish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        SetViewIds();

        Rubbish.setOnClickListener(view -> {
            Intent intent = new Intent(MainMenuActivity.this, RubbishMenuActivity.class);
            startActivity(intent);
        });
    }

    private void SetViewIds()
    {
        Rubbish = findViewById(R.id.RubbishBtn);
    }
}