package com.example.mathcas.kanban_do;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                RegisterActivity.this.startActivity(intent);
                break;
            case R.id.register:
                Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(intent1);
        }
    }
}
