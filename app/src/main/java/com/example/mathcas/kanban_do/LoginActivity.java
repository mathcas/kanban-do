package com.example.mathcas.kanban_do;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login;
    private TextView register;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        login = (Button) findViewById(R.id.login);
        register = findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.login:
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                LoginActivity.this.startActivity(intent);
                break;
            case R.id.register:
                Intent intent2 = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent2);
                break;
        }

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
