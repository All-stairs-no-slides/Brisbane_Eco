package com.example.brisbaneeco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class launcher extends AppCompatActivity {

    private TextView TitleTextView;
    private Button SignUp;
    private Button LogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        setViewIds();

        SignUp.setOnClickListener(view -> {
            Intent intent = new Intent(launcher.this, SignUpActivity.class);
            startActivity(intent);
        });

        LogIn.setOnClickListener(view -> {
            Intent intent = new Intent(launcher.this, LoginActivity.class);
            startActivity(intent);
        });
    }
    private void setViewIds()
    {
        SignUp = findViewById(R.id.btnSignup);
        LogIn = findViewById(R.id.btnLogin);
    }

}