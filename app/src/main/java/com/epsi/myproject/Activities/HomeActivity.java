package com.epsi.myproject.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.epsi.myproject.Client;
import com.epsi.myproject.R;

public class HomeActivity extends AppCompatActivity {
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Client c = (Client) getIntent().getSerializableExtra("objClient");
        Log.i("FSD", "HOME VIEW : "+c.getNom());
        text = (TextView) findViewById(R.id.homeMsg);
        text.setText(c.getNom());
    }
}
