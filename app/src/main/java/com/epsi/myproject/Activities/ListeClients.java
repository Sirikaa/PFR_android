package com.epsi.myproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.epsi.myproject.Client;
import com.epsi.myproject.Adapters.ClientAdapter;
import com.epsi.myproject.Interface;
import com.epsi.myproject.Materiel;
import com.epsi.myproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ListeClients extends AppCompatActivity{
    /*private static final String URI = "http://192.168.1.16:8080/resoapi/api/";
    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_clients);

        //J'ai du mettre final pour pouvoir l'utiliser dans la classe interne OnItemClickListener
        final List<Client> clientList = getClientList();
        ClientAdapter adapter = new ClientAdapter(this, clientList);
        ListView clientView = (ListView) findViewById(R.id.listView);
        clientView.setAdapter(adapter);

        //Classe interne pour réagir au clique sur un client de la liste
        clientView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //On récup l'object client qu'on a sélectionné
                Client client = clientList.get(position);
                Intent intent = new Intent(ListeClients.this, FicheClient.class);
                //Vu qu'on a implémenté Client par la classe Serializable, on peut envoyé tout l'objet
                intent.putExtra("objClient", client);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public List<Client> getClientList() {
        // Initialize a list of Entry objects
        List<Client> clientList = new ArrayList<Client>();
        //Méthode pour récupérer le contenu du JSON
        StringBuilder sb = recupContenuJson(R.raw.clients_contacts);

        //Partie où on rempli l'objet client avec les infos du JSON
        try {
            String cId, cNom, cAdresse1, cAdresse2, cVille, cCp;

            JSONObject rootObj = new JSONObject(sb.toString());
            JSONArray completeArray = rootObj.getJSONArray("clients");

            for (int i = 0; i < completeArray.length(); i++) {
                //On récupère les infos CLIENT
                JSONObject clientObj = completeArray.getJSONObject(i);

                Client client = new Client();
                cId = clientObj.getString("clt_id");
                client.setId(cId);

                //On ajoute la liste de ses matériels
                List<Materiel> materiels = parseMateriel(client.getId());
                client.setMateriels(materiels);

                cNom = clientObj.getString("clt_nom");
                client.setNom(cNom);
                if(checkValue(clientObj.getString("clt_adr1"))){
                    cAdresse1 = clientObj.getString("clt_adr1");
                    client.setAdresse1(cAdresse1);
                }
                if(checkValue(clientObj.getString("clt_adr2"))){
                    cAdresse2 = clientObj.getString("clt_adr2");
                    client.setAdresse2(cAdresse2);
                }

                if(clientObj.has("ville") && !(clientObj.optString("ville").isEmpty())){
                    JSONObject villeObj = clientObj.getJSONObject("ville");
                    //J'ai été obligée de mettre plein de if sinon si c'est vide ça fait une JSONException et la boucle s'arrêtait.
                    if(villeObj.length() >= 0 && villeObj.has("ville_nom")){
                        if(checkValue(villeObj.getString("ville_nom"))){
                            cVille = villeObj.getString("ville_nom");
                            client.setVille(cVille);
                        }
                        if(villeObj.has("ville_cp")) {
                            if(checkValue(villeObj.getString("ville_cp"))) {
                                cCp = villeObj.getString("ville_cp");
                                client.setCp(cCp);
                            }
                        }
                    }
                }

                JSONArray contactArray = clientObj.getJSONArray("contacts");
                for (int j = 0; j < contactArray.length(); j++) {
                    Contact contact = new Contact();
                    String contactNom, contactPrenom, contactTel, contactEmail, contactFonction;
                    //On récupère les infos de contact
                    JSONObject contactObject = contactArray.getJSONObject(j);

                    contactNom = contactObject.getString("pers_nom");
                    contact.setNom(contactNom);
                    contactPrenom = contactObject.getString("pers_prenom");
                    contact.setPrenom(contactPrenom);

                    if(checkValue(contactObject.getString("pers_tel"))){
                        contactTel = contactObject.getString("pers_tel");
                        contact.setTel(contactTel);
                    }
                    if(checkValue(contactObject.getString("pers_email"))){
                        contactEmail = contactObject.getString("pers_email");
                        contact.setEmail(contactEmail);
                    }

                    if(!(contactObject.optString("fonction").isEmpty())){
                        JSONObject fonctionObj = contactObject.getJSONObject("fonction");

                        if(fonctionObj.length() >= 0 && fonctionObj.has("fct_libelle")){
                            if(fonctionObj.getString("fct_libelle") != "null") {
                                contactFonction = fonctionObj.getString("fct_libelle");
                                contact.setFonction(contactFonction);
                            }
                        }
                    }
                    client.setContact(contact);
                }
                clientList.add(client);
                //Log.d("valeur", "Le CLIENT " +client.getNom()+" a été rajouté à la liste des CLIENTS");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //On retourne la liste de tous les clients présents dans le JSON
        return clientList;
    }
    public StringBuilder recupContenuJson(int json){
        StringBuilder sb = new StringBuilder();
        Resources res = getResources();
        InputStream inputStream = res.openRawResource(json);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        }catch(IOException ioe ){
            ioe.getMessage();
        }
        return sb;
    }
    public List<Materiel> parseMateriel(String idC){
        List<Materiel> materiels = new ArrayList<Materiel>();
        StringBuilder sb = recupContenuJson(R.raw.materiel);
        StringBuilder sbTm = recupContenuJson(R.raw.type_materiel);

        try{
            /*Interface itf;
            List<Interface> lItf = new ArrayList<Interface>();*/

            /*JSONArray completeArray = new JSONArray(sb.toString());
            for(int i =0; i<completeArray.length(); i++){
                JSONObject materielObj = completeArray.getJSONObject(i);
                Materiel m = new Materiel();

                //TODO Matcher le matériel au client...
                String idCMateriel = materielObj.getString("idclient");
                if(Integer.parseInt(idCMateriel) == Integer.parseInt(String.valueOf(idC))){
                    m.setId(materielObj.getString("id"));
                    m.setLibelle(materielObj.getString("libelle"));

                    String idTypeMateriel = materielObj.getString("idtype");

                    JSONArray typeM = new JSONArray(sbTm.toString());
                    String idTypeM, libelleTypeM;
                    for(int j =0; j<typeM.length();j++){
                        JSONObject typeMat = typeM.getJSONObject(j);
                        idTypeM = typeMat.getString("id");
                        if(idTypeMateriel == idTypeM){
                            libelleTypeM = typeMat.getString("libelle");
                            m.setTypeMateriel(libelleTypeM);
                            m.setIdTypeMateriel(Integer.parseInt(idTypeMateriel));
                        }
                    }
                    parseInterface(m);
                    materiels.add(m);
                }
            }
        }catch(JSONException jse){
            jse.printStackTrace();
        }
        return materiels;
    }
    public void parseInterface(Materiel m){
        List<Interface> interfaces = new ArrayList<Interface>();
        m.setListe_interfaces(interfaces);
        StringBuilder sb = recupContenuJson(R.raw.interfaces);
        StringBuilder sbTIf = recupContenuJson(R.raw.type_if);

        try{
            JSONArray completeArray = new JSONArray(sb.toString());
            for(int i =0; i<completeArray.length(); i++){
                JSONObject interfaceObj = completeArray.getJSONObject(i);
                Interface itfc = new Interface();

                String idMaterielItfc = interfaceObj.getString("idmateriel");

                if(Integer.parseInt(idMaterielItfc) == Integer.parseInt(m.getId())){

                    itfc.setId(interfaceObj.getString("id"));
                    itfc.setNom(interfaceObj.getString("nom"));
                    itfc.setMac(interfaceObj.getString("mac"));

                    String idTypeInterface = interfaceObj.getString("idtype");
                    JSONArray typeInterface = new JSONArray(sbTIf.toString());
                    for(int j = 0; j < typeInterface.length(); j++){
                        JSONObject typeItfc = typeInterface.getJSONObject(j);
                        String idTypeItfc = typeItfc.getString("id");
                        if(idTypeInterface == idTypeItfc){
                            itfc.setType(typeItfc.getString("libelle"));
                        }
                    }
                    parseAdressesIP(itfc);
                    interfaces.add(itfc);
                }
            }
        }catch(JSONException jse){
            jse.printStackTrace();
        }
        m.setListe_interfaces(interfaces);
    }
    public void parseAdressesIP(Interface i){
        StringBuilder sbAIp = recupContenuJson(R.raw.adresse_ip);
        StringBuilder sbTA = recupContenuJson(R.raw.type_affectation);

        try{
            JSONArray completeArray = new JSONArray(sbAIp.toString());
            for(int j =0; j<completeArray.length(); j++){
                JSONObject ipObj = completeArray.getJSONObject(j);

                String idInterfaceIp = ipObj.getString("idinterface");

                if(Integer.parseInt(idInterfaceIp) == Integer.parseInt(i.getId())){

                    i.setAdresseIpv4(ipObj.getString("ipV4"));
                    if(checkValue(ipObj.getString("ipV6"))){
                        i.setAdresseIpv6(ipObj.getString("ipV6"));
                    }
                    i.setMasque(ipObj.getString("masque"));

                    String idTypeAffIp = ipObj.getString("idtypeaff");
                    JSONArray typeAff = new JSONArray(sbTA.toString());
                    for(int k = 0; k < typeAff.length(); k++){
                        JSONObject typeAffectation = typeAff.getJSONObject(k);
                        String idTypeAff = typeAffectation.getString("id");
                        if(idTypeAffIp == idTypeAff){
                            i.setTypeAffectation(typeAffectation.getString("libelle"));
                        }
                    }
                }
            }
        }catch(JSONException jse){
            jse.printStackTrace();
        }
    }
    public boolean checkValue(String s){
        if(s != "null"){
            return true;
        }else{
            return false;
        }
    }*/
}
