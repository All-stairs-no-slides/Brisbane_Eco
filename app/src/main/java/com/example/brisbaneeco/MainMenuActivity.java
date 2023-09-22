package com.example.brisbaneeco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainMenuActivity extends AppCompatActivity {

    private Button Rubbish;
//    private Button option2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        SetViewIds();

        Rubbish.setOnClickListener(view -> {
            Intent intent = new Intent(MainMenuActivity.this, RubbishMenuActivity.class);
            startActivity(intent);
        });
//        option2.setOnClickListener(view -> {
//            Intent intent = new Intent(MainMenuActivity.this, menupage.class);
//            startActivity(intent);
//        });
    }

    private void SetViewIds()
    {
        Rubbish = findViewById(R.id.RubbishBtn);
//        option2 = findViewById(R.id.btnOption2);
    }
}