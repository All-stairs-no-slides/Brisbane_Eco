package com.example.brisbaneeco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private Button LoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setviewIds();

        LoggedIn.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, menupage.class);
            startActivity(intent);
        });
    }

    private void setviewIds()
    {
        LoggedIn = findViewById(R.id.LoginButton);
    }
}