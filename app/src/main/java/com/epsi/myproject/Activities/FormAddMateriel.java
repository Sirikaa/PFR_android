package com.epsi.myproject.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.epsi.myproject.Adapters.TypeAffectationSpinner;
import com.epsi.myproject.Adapters.TypeInterfaceSpinner;
import com.epsi.myproject.Adapters.TypeMaterielSpinner;
import com.epsi.myproject.AdresseIp;
import com.epsi.myproject.Interface;
import com.epsi.myproject.Materiel;
import com.epsi.myproject.Persistence.JsonApiPersistence;
import com.epsi.myproject.R;
import com.epsi.myproject.TypeAffectation;
import com.epsi.myproject.TypeInterface;
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
    private int idMateriel;
    private EditText libelleMateriel;
    private EditText serialMateriel;
    private Button addMaterielButton;
    private String URL;

    private Spinner typeMaterielSpinner;
    private List<String> categories = new ArrayList<>();
    private String categorieSelected;
    private List<Integer> idsCategories = new ArrayList<>();
    private int idTypeMaterielSelected;

    private Spinner typeInterfaceSpinner;
    private List<String> categoriesItf = new ArrayList<>();
    private String categorieItfSelected;
    private List<Integer> idsCategoriesItf = new ArrayList<>();
    private int idTypeInterfaceSelected;

    private Spinner typeAffectationSpinner;
    private List<String> categoriesAff = new ArrayList<>();
    private String categorieAffSelected;
    private List<Integer> idsCategoriesAff = new ArrayList<>();
    private int idTypeAffectationSelected;

    private boolean isUpdate = false;
    private LinearLayout parentLayoutItf;

    private List<Interface> formInterfaces = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_materiel_layout);
        parentLayoutItf = findViewById(R.id.interfaces);

        Bundle extras = getIntent().getExtras();
        idClient = extras.getInt("idClient");

        //On vérifie si on va update ou add
        if(getIntent().hasExtra("idMateriel")){
            idMateriel = (int) extras.getSerializable("idMateriel");
            isUpdate = true;
            initializeUpdateView();
        }else{
            initializeAddView();
        }
    }

    private void initializeAddView(){
        URL = "http://192.168.1.16:8080/resoapi/api/materiels/client/"+idClient;
        libelleMateriel = findViewById(R.id.formLibelleMateriel);
        addMaterielButton = findViewById(R.id.buttonAddMateriel);
        serialMateriel = findViewById(R.id.formSerialMateriel);

        //Car le premier champ de la liste est du vide.
        categories.add("");
        idsCategories.add(0);
        initTypeMaterielSpinner(0);


        addMaterielButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new HTTPAsyncPostTask().execute(URL);
            }
        });
    }

    public class HTTPAsyncPostTask extends AsyncTask<String, Void, String> {
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
            setInfosInterfaces();
            Materiel materiel = new Materiel(libelleMateriel.getText().toString(), serialMateriel.getText().toString(), new TypeMateriel(idTypeMaterielSelected, categorieSelected), formInterfaces);
            setPostRequestContent(conn, JsonApiPersistence.buildMaterielJsonObject(materiel));


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

    private void initializeUpdateView(){
        final Materiel  materiel = JsonApiPersistence.getInfosMateriel(idMateriel);
        URL = "http://192.168.1.16:8080/resoapi/api/client/"+idClient+"/materiel/"+idMateriel;
        libelleMateriel = findViewById(R.id.formLibelleMateriel);
        addMaterielButton = findViewById(R.id.buttonAddMateriel);
        serialMateriel = findViewById(R.id.formSerialMateriel);

        addMaterielButton.setText("Enregistrer les modifications");
        libelleMateriel.setText(materiel.getLibelle());
        serialMateriel.setText(materiel.getSerial());

        initTypeMaterielSpinner(materiel.getType().getId());
        for(Interface itf : materiel.getInterfaces()){
            addInterface(itf);
        }

        addMaterielButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new HTTPAsyncPutTask().execute(URL);
            }
        });
    }

    public class HTTPAsyncPutTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                try {
                    return put(urls[0]);
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

        private String put(String myUrl) throws IOException, JSONException {
            URL url = new URL(myUrl);

            //On configure la connexion
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            //On ajoute le JSON qu'on veut transmettre au serveur
            setInfosInterfaces();
            Materiel materiel = new Materiel(libelleMateriel.getText().toString(), serialMateriel.getText().toString(), new TypeMateriel(idTypeMaterielSelected, categorieSelected), formInterfaces);
            setPutRequestContent(conn, JsonApiPersistence.buildMaterielJsonObject(materiel));

            //On fait la requête POST sur l'URL
            conn.connect();

            //On récupère la réponse du serveur
            return convertStreamToString(conn.getInputStream());
        }

        private void setPutRequestContent(HttpURLConnection conn, JSONObject jsonObject) throws IOException {
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

    public void onAddInterface(View v){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itfView = inflater.inflate(R.layout.fragment_interfaces, null);
        parentLayoutItf.addView(itfView);
        typeInterfaceSpinner = itfView.findViewById(R.id.formSpinnerTypeInterfaceInterface);
        initTypeInterfaceSpinner(typeInterfaceSpinner);
    }
    public void onDeleteInterface(View v){
        parentLayoutItf.removeView((View) v.getParent().getParent());
    }

    //PAS TOUCHER
    public void onAddIp(View v){
        LinearLayout parentLayoutIp = (LinearLayout) v.getParent();
        parentLayoutIp = parentLayoutIp.findViewById(R.id.ips);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View ipView = inflater.inflate(R.layout.fragment_adressesip, null);
        parentLayoutIp.addView(ipView);
        typeAffectationSpinner = ipView.findViewById(R.id.formSpinnerTypeAffIp);
        initTypeAffectationSpinner(typeAffectationSpinner);
    }
    public void onDeleteIp(View v){
        LinearLayout parentLayoutIp = (LinearLayout) v.getParent().getParent().getParent();
        parentLayoutIp.removeView((ViewGroup)v.getParent().getParent());
    }

    private void initTypeMaterielSpinner(int idTypeMateriel){
        typeMaterielSpinner = TypeMaterielSpinner.initSpinner(this,(Spinner) findViewById(R.id.formSpinnerTypeMaterielMateriel), categories);
        if(isUpdate){
            typeMaterielSpinner.setSelection(idTypeMateriel-1);
        }
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
    }
    private void initTypeInterfaceSpinner(Spinner spinner){
        categoriesItf = new ArrayList<>();
        typeInterfaceSpinner = TypeInterfaceSpinner.initSpinner(this,spinner, categoriesItf);
        typeInterfaceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long itemID) {
                if (position >= 0 && position < categoriesItf.size()) {
                    idTypeInterfaceSelected = TypeInterfaceSpinner.getIdsTypes(idsCategoriesItf).get(position);
                    categorieItfSelected = categoriesItf.get(position);
                } else {
                    Toast.makeText(FormAddMateriel.this, "Erreur dans le choix de la catégorie", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }
    private void initTypeAffectationSpinner(Spinner spinner){
        categoriesAff = new ArrayList<>();
        typeAffectationSpinner = TypeAffectationSpinner.initSpinner(this,spinner, categoriesAff);
        typeAffectationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long itemID) {
                if (position >= 0 && position < categoriesAff.size()) {
                    idTypeAffectationSelected = TypeAffectationSpinner.getIdsTypes(idsCategoriesAff).get(position);
                    categorieAffSelected = categoriesAff.get(position);
                } else {
                    Toast.makeText(FormAddMateriel.this, "Erreur dans le choix de la catégorie", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void addInterface(Interface itf){
        List<AdresseIp> ips = itf.getAdressesIp();

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View itfView = inflater.inflate(R.layout.fragment_interfaces, null);
        parentLayoutItf.addView(itfView);

        EditText formNomInterface = itfView.findViewById(R.id.formNomInterface);
        formNomInterface.setText(itf.getNom());
        EditText formMacInterface = itfView.findViewById(R.id.formMacInterface);

        if(itf.getMac() == "null"){
            formMacInterface.setText("");
        }else{
            formMacInterface.setText(itf.getMac());
        }

        typeInterfaceSpinner = itfView.findViewById(R.id.formSpinnerTypeInterfaceInterface);
        initTypeInterfaceSpinner(typeInterfaceSpinner);
        if(isUpdate){
            typeInterfaceSpinner.setSelection(itf.getType().getId() -1);
        }

        if(ips.size() > 0){
            for(AdresseIp  ip : ips){
                LinearLayout parentLayoutIp = (LinearLayout) itfView;
                parentLayoutIp = parentLayoutIp.findViewById(R.id.ips);
                final View ipView = inflater.inflate(R.layout.fragment_adressesip, null);
                parentLayoutIp.addView(ipView);

                EditText formIpv4Ip = ipView.findViewById(R.id.formIpv4Ip);
                formIpv4Ip.setText(ip.getIpv4());


                EditText formIpv6Ip = ipView.findViewById(R.id.formIpv6Ip);
                if(ip.getIpv6() == "null"){
                    formIpv6Ip.setText("");
                }else{
                    formIpv6Ip.setText(ip.getIpv6());
                }

                EditText formMasqueIp = ipView.findViewById(R.id.formMasqueIp);
                formMasqueIp.setText(ip.getMasque());
                typeAffectationSpinner = ipView.findViewById(R.id.formSpinnerTypeAffIp);
                initTypeAffectationSpinner(typeAffectationSpinner);
                if(isUpdate){
                    typeAffectationSpinner.setSelection(ip.getTypeAffectation().getId() -1);
                }

            }
        }
    }

    private void setInfosInterfaces(){
        formInterfaces = new ArrayList<>();
        //Connaitre le nombre d'interfaces
        for(int i=0; i<parentLayoutItf.getChildCount(); i++){
            List<AdresseIp> ips = new ArrayList<>();
            View child = parentLayoutItf.getChildAt(i);

            EditText formNomInterface = child.findViewById(R.id.formNomInterface);
            String nom = formNomInterface.getText().toString();
            EditText formMacInterface = child.findViewById(R.id.formMacInterface);
            String mac = formMacInterface.getText().toString();

            //Connaitre le nombre d'adresses IP
            LinearLayout ipsLayout = child.findViewById(R.id.ips);
            if(ipsLayout.getChildCount() > 0){
                for(int j=0; j<ipsLayout.getChildCount(); j++){
                    View childIp = ipsLayout.getChildAt(j);

                    EditText formIpv4Ip = childIp.findViewById(R.id.formIpv4Ip);
                    String ipv4 = formIpv4Ip.getText().toString();
                    EditText formIpv6Ip = childIp.findViewById(R.id.formIpv6Ip);
                    String ipv6 = formIpv6Ip.getText().toString();
                    EditText formMasqueIp = childIp.findViewById(R.id.formMasqueIp);
                    String masque = formMasqueIp.getText().toString();

                    ips.add(new AdresseIp(ipv4
                            , ipv6
                            , masque
                            , new TypeAffectation(idTypeAffectationSelected, categorieAffSelected)));
                }
            }

            formInterfaces.add(new Interface(nom
                    , mac
                    , new TypeInterface(idTypeInterfaceSelected, categorieItfSelected)
                    , ips));
        }
    }
}
