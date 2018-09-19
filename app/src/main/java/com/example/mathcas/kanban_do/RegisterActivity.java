package com.example.mathcas.kanban_do;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private Button login;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastrar);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        // Obtain the FirebaseAnalytics instance.
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:

                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "button_login");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "button_login_on_register");
                bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "button");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, bundle);
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                RegisterActivity.this.startActivity(intent);
                break;
            case R.id.register:
                Intent intent1 = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(intent1);
                Bundle bundle2 = new Bundle();
                bundle2.putString(FirebaseAnalytics.Param.ITEM_ID, "button_login");
                bundle2.putString(FirebaseAnalytics.Param.ITEM_NAME, "button_login_on_register");
                bundle2.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "button");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle2);
        }
    }
}
