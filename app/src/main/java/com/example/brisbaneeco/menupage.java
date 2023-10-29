package com.example.brisbaneeco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class menupage extends AppCompatActivity {

    private TextView RubbishMenu;
    private ImageView ProfileMenu;
    private TextView CameraScreen;
    private TextView MedalScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menupage);

        setViewIds();

        RubbishMenu.setOnClickListener(view -> {
            Intent intent = new Intent(menupage.this, RubbishMenuActivity.class);
            startActivity(intent);
        });

        ProfileMenu.setOnClickListener(view -> {
            Intent intent = new Intent(menupage.this, ProfileActivity.class);
            startActivity(intent);
        });

        CameraScreen.setOnClickListener(view -> {
            Intent intent = new Intent(menupage.this, QRCodeScanner.class);
            startActivity(intent);
        });

        MedalScreen.setOnClickListener(view -> {
            Intent intent = new Intent(menupage.this, MedalsPage.class);
            startActivity(intent);
        });
    }

    private void setViewIds(){
        RubbishMenu = findViewById(R.id.RubbishMenuBtn);
        ProfileMenu = findViewById(R.id.ProfileMenuBtn);
        CameraScreen = findViewById(R.id.CameraBtn);
        MedalScreen = findViewById(R.id.BadgeBtn);
    }
}
