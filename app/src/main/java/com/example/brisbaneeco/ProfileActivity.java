package com.example.brisbaneeco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    private TextView BadgeShortcut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setViewIds();

        BadgeShortcut.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, MedalsPage.class);
            startActivity(intent);
        });
    }

    private void setViewIds() {
        BadgeShortcut = findViewById(R.id.BadgeShortcutBtn);
    }
}
