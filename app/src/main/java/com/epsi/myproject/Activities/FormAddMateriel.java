package com.epsi.myproject.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.epsi.myproject.Adapters.TypeMaterielSpinner;
import com.epsi.myproject.Client;
import com.epsi.myproject.Persistence.JsonApiPersistence;
import com.epsi.myproject.R;
import com.epsi.myproject.TypeMateriel;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FormAddMateriel extends AppCompatActivity {
    private int idClient;
    private EditText libelleMateriel;
    private EditText serialMateriel;
    private Button addMaterielButton;
    private String URL;
    private Spinner typeMaterielSpinner;
    private List<String> categories = new ArrayList<>();
    private String categorieSelected;
    private List<Integer> idsCategories = new ArrayList<>();
    private int idTypeMaterielSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_add_materiel_layout);

        Bundle extras = getIntent().getExtras();
        int idClient = (int) extras.getSerializable("idClient");

        initializeView();
    }

    private void initializeView(){
        URL = "http://192.168.1.16:8080/resoapi/api/materiels/client/"+idClient;
        libelleMateriel = findViewById(R.id.formLibelleMateriel);
        addMaterielButton = findViewById(R.id.buttonAddMateriel);
        serialMateriel = findViewById(R.id.formSerialMateriel);

        //Car le premier champ de la liste est du vide.
        categories.add("");
        idsCategories.add(0);

        typeMaterielSpinner = TypeMaterielSpinner.initTypeMaterielSpinner(this,(Spinner) findViewById(R.id.formSpinnerTypeMaterielMateriel), categories);
        typeMaterielSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long itemID) {
                if (position >= 0 && position < categories.size()) {
                    idTypeMaterielSelected = TypeMaterielSpinner.getIdsTypeMateriel(idsCategories).get(position);
                    categorieSelected = categories.get(position);
                } else {
                    Toast.makeText(FormAddMateriel.this, "Erreur dans le choix de la catégorie", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        addMaterielButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new HTTPAsyncTask().execute(URL);
            }
        });
    }

    public class HTTPAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                try {
                    return post(urls[0]);
                } catch (JSONException e) {
                    e.printStackTrace();
                    return "Error!";
                }
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(FormAddMateriel.this, ListeMateriels.class);
            intent.putExtra("idClient", idClient);
            startActivity(intent);
        }

        private String post(String myUrl) throws IOException, JSONException {
            URL url = new URL(myUrl);

            //On configure la connexion
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            //On ajoute le JSON qu'on veut transmettre au serveur
            setPostRequestContent(conn, JsonApiPersistence.buildMaterielJsonObject(libelleMateriel.getText().toString(), serialMateriel.getText().toString(), new TypeMateriel(idTypeMaterielSelected, categorieSelected)));

            //On fait la requête POST sur l'URL
            conn.connect();

            //On récupère la réponse du serveur
            return convertStreamToString(conn.getInputStream());
        }

        private void setPostRequestContent(HttpURLConnection conn, JSONObject jsonObject) throws IOException {
            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(jsonObject.toString());
            writer.flush();
            writer.close();
            os.close();
        }

        private String convertStreamToString(InputStream is) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(FormAddMateriel.this, ListeMateriels.class);
        intent.putExtra("idClient", idClient);
        startActivity(intent);
    }


}
