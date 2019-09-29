package com.epsi.myproject.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.epsi.myproject.BackgroundTasks.FetchData;
import com.epsi.myproject.R;

public class LoginActivity extends AppCompatActivity {
    Button button;
    public static TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        button = (Button) findViewById(R.id.clickmebutton);
        data = (TextView) findViewById(R.id.databdd);

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                FetchData process = new FetchData();
                process.execute();
            }
        });
    }
}
