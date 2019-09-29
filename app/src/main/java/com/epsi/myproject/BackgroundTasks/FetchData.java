package com.epsi.myproject.BackgroundTasks;

import android.os.AsyncTask;

import com.epsi.myproject.Activities.LoginActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchData extends AsyncTask<Void, Void, Void> {
    String data="";
    @Override
    protected Void doInBackground(Void... voids){
        try{
            URL url = new URL("http://192.168.1.16:8080/resoapi/api/clients");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //Read data from stream
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while(line != null){
                line = br.readLine();
                data = data+line;
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch(IOException ioe){
            ioe.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(Void avoid){
        super.onPostExecute(avoid);
        LoginActivity.data.setText(this.data);
    }
}
