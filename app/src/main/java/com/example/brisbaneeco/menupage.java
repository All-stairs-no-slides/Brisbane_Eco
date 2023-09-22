package com.example.brisbaneeco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class menupage extends AppCompatActivity {

    private TextView RubbishMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menupage);

        setViewIds();

        RubbishMenu.setOnClickListener(view -> {
            Intent intent = new Intent(menupage.this, RubbishMenuActivity.class);
            startActivity(intent);
        });
    }

    private void setViewIds(){
        RubbishMenu = findViewById(R.id.RubbishMenuBtn);
    }

}
