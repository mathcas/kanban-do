package com.kanbandos.mathcas.kanban_do;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login;
    private TextView register;
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email = findViewById(R.id.email_textView);
        password = findViewById(R.id.password_textView);
        login = (Button) findViewById(R.id.button_login);
        register = findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_login:
                onLogin();
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

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            //email.setText(user.getEmail().toString());
            //password.setVisibility(View.GONE);

            findViewById(R.id.register).setVisibility(View.GONE);
            findViewById(R.id.sign_out).setVisibility(View.VISIBLE);
        } else {

            findViewById(R.id.register).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_out).setVisibility(View.GONE);
        }
    }

    private void onLogin() {
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password);
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
