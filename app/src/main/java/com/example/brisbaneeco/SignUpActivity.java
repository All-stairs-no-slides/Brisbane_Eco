package com.example.brisbaneeco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SignUpActivity extends AppCompatActivity {

    private Button SignedUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        setviewIds();

        SignedUp.setOnClickListener(view -> {
            Intent intent = new Intent(SignUpActivity.this, menupage.class);
            startActivity(intent);
        });
    }

    private void setviewIds()
    {
        SignedUp = findViewById(R.id.SignedUpButton);
    }
}