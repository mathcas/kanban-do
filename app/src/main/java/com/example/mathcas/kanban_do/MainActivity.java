package com.example.mathcas.kanban_do;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private FloatingActionButton floatingActionButton1;
    private LinearLayout linearLayout1;
    private int count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout1 = findViewById(R.id.linear1);
        floatingActionButton1 = findViewById(R.id.floatingActionButton4);
        textView = findViewById(R.id.textView);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText newTextView = new EditText(getBaseContext());
                count++;
                newTextView.setText("texto " + String.valueOf(count));
                linearLayout1.addView(newTextView);

            }
        });
    }
}
