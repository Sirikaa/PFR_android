package com.epsi.myproject.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.epsi.myproject.Client;
import com.epsi.myproject.Persistence.ClientPersistence;
import com.epsi.myproject.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    Client client;
    private static final String URL = "http://192.168.1.16:8080/resoapi/api/login";
    EditText matricule;
    EditText password;
    Button loginButton;
    public static TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        matricule = (EditText) findViewById(R.id.matricule);
        password = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.clickmebutton);
        data = (TextView) findViewById(R.id.databdd);

        loginButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                StringRequest req = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String resp) {
                        //Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                        try{
                            JSONObject jsonClient = new JSONObject(resp);
                            client = ClientPersistence.parseClientJson(jsonClient);
                            Log.i("FSD", "Client récupéré du JSON ! BONJOUR "+client.getNom()+" !!!");
                        }catch(JSONException jse){
                            jse.printStackTrace();
                        }
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("objClient", client);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse response = error.networkResponse;
                        String errorMsg = "";
                        if(response != null && response.data != null){
                            errorMsg = new String(response.data);
                        }
                        Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("matricule", matricule.getText().toString());
                        parameters.put("password", password.getText().toString());
                        return parameters;
                    }
                };
                RequestQueue rQueue = Volley.newRequestQueue(LoginActivity.this);
                rQueue.add(req);
            }
        });
    }
}
