package com.epsi.myproject.Persistence;

import android.util.Log;

import com.epsi.myproject.AdresseIp;
import com.epsi.myproject.Client;
import com.epsi.myproject.Interface;
import com.epsi.myproject.Materiel;
import com.epsi.myproject.Personne;
import com.epsi.myproject.TypeAffectation;
import com.epsi.myproject.TypeInterface;
import com.epsi.myproject.TypeMateriel;
import com.epsi.myproject.Ville;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public abstract class JsonApiPersistence {

    private static final String TYPE_AFFECTATION_JSON_ARRAY_NAME = "typeAffectation";
    private static final String TYPE_INTERFACE_JSON_ARRAY_NAME = "type";
    private static final String TYPE_MATERIEL_JSON_ARRAY_NAME = "type";
    private static final String ADRESSE_IP_JSON_ARRAY_NAME = "adressesIp";
    private static final String INTERFACES_JSON_ARRAY_NAME = "interfaces";
    private static final String PERSONNES_JSON_ARRAY_NAME = "contacts";
    private static final String MATERIELS_JSON_ARRAY_NAME = "materiels";

    public static TypeAffectation parseTypeAffectationJson(JSONObject json) throws JSONException {
        return new TypeAffectation(json.getInt("id"), json.getString("libelle"));
    }
    public static AdresseIp parseAdresseIpJson(JSONObject json) throws JSONException {
        return new AdresseIp(json.getInt("id"), json.getString("ipv4"), json.getString("ipv6"), json.getString("masque"), parseTypeAffectationJson(json.getJSONObject(TYPE_AFFECTATION_JSON_ARRAY_NAME)));
    }
    public static List<AdresseIp> parseAdressesIpJsonArray(JSONArray jsonArray) throws JSONException{
        List<AdresseIp> adressesIp = new ArrayList<AdresseIp>();
        for(int k=0; k<jsonArray.length(); k++){
            JSONObject jsonAdresseIp = jsonArray.getJSONObject(k);
            adressesIp.add(parseAdresseIpJson(jsonAdresseIp));
        }
        return adressesIp;

    }
    public static TypeInterface parseTypeInterfaceJson(JSONObject json) throws JSONException {
        return new TypeInterface(json.getInt("id"), json.getString("libelle"));
    }
    public static Interface parseInterfaceJson (JSONObject json) throws JSONException{
        return new Interface(json.getInt("id"), json.getString("nom"), json.getString("mac"), parseTypeInterfaceJson(json.getJSONObject(TYPE_INTERFACE_JSON_ARRAY_NAME)), parseAdressesIpJsonArray(json.getJSONArray(ADRESSE_IP_JSON_ARRAY_NAME)));
    }
    public static List<Interface> parseInterfacesJsonArray(JSONArray jsonArray) throws JSONException{
        List<Interface> interfaces = new ArrayList<Interface>();
        for(int j=0; j<jsonArray.length(); j++){
            JSONObject jsonInterface = jsonArray.getJSONObject(j);
            interfaces.add(parseInterfaceJson(jsonInterface));
        }
        return interfaces;
    }
    public static TypeMateriel parseTypeMaterielJson(JSONObject json) throws JSONException{
        return new TypeMateriel(json.getInt("id"), json.getString("libelle"));
    }
    public static Materiel parseMaterielJson(JSONObject json){
        Materiel m = null;
        try{
            m = new Materiel(json.getInt("id"), json.getString("libelle"), json.getString("serial"), parseTypeMaterielJson(json.getJSONObject(TYPE_MATERIEL_JSON_ARRAY_NAME)), parseInterfacesJsonArray(json.getJSONArray(INTERFACES_JSON_ARRAY_NAME)));
        }catch(JSONException je){
            je.printStackTrace();
        }
        return m;
    }
    public static List<Materiel> parseMaterielsJsonArray(JSONArray jsonArray) throws JSONException{
        List<Materiel> materiels = new ArrayList<Materiel>();
        for(int i=0; i<jsonArray.length(); i++){
            JSONObject jsonMateriel = jsonArray.getJSONObject(i);
            materiels.add(parseMaterielJson(jsonMateriel));
        }
        return materiels;
    }
    public static Ville parseVilleJson(JSONObject json) throws JSONException{
        return new Ville(json.getJSONObject("ville").getInt("id"), json.getJSONObject("ville").getString("cp"), json.getJSONObject("ville").getString("ville"));
    }
    public static Personne parsePersonneJson(JSONObject json) throws JSONException{
        return new Personne(json.getInt("id"), json.getString("nom"), json.getString("prenom"), json.getString("email"), json.getString("telephone"));
    }
    public static List<Personne> parsePersonnesJsonArray(JSONArray jsonArray) throws JSONException{
        List<Personne> contacts = new ArrayList<Personne>();
        for(int i=0; i<jsonArray.length(); i++){
            JSONObject jsonPersonne = jsonArray.getJSONObject(i);
            contacts.add(parsePersonneJson(jsonPersonne));
        }
        return contacts;
    }
    public static Client parseClientJson(JSONObject json) throws JSONException {
        return new Client(json.getInt("id"), json.getString("nom"), json.getString("matricule"), json.getString("password"), json.getString("adresse1"), json.getString("adresse2"), parseVilleJson(json), parsePersonnesJsonArray(json.getJSONArray(PERSONNES_JSON_ARRAY_NAME)), parseMaterielsJsonArray(json.getJSONArray(MATERIELS_JSON_ARRAY_NAME)));
    }
    public static ArrayList<JSONObject> jsonArrayToJsonObjects(JSONArray jsonArray) throws JSONException {
        ArrayList<JSONObject> jsons = new ArrayList<JSONObject>();
        for(int i=0; i<jsonArray.length();i++){
            jsons.add(jsonArray.getJSONObject(i));
        }
        return jsons;
    }

    //buildJsonObjects To send to API
    public static JSONObject buildTypeJsonObject(int id, String libelle) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", id);
        json.put("libelle", libelle);
        return json;
    }
    public static JSONObject buildMaterielJsonObject(Materiel m) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("libelle", m.getLibelle());
        json.put("serial", m.getSerial());
        json.put("type", buildTypeJsonObject(m.getType().getId(), m.getType().getLibelle()));
        json.put("interfaces", buildInterfacesJsonArrayFromObject(m.getInterfaces()));
        return json;
    }
    public static JSONArray buildInterfacesJsonArrayFromObject(List<Interface> itfs) throws JSONException{
        JSONArray jsonArray = new JSONArray();
        for(Interface itf : itfs){
            JSONObject json = new JSONObject();
            json.put("nom", itf.getNom());
            json.put("mac", itf.getMac());
            json.put("type", buildTypeJsonObject(itf.getType().getId(), itf.getType().getLibelle()));
            json.put("adressesIp", buildIpsJsonArrayFromObject(itf.getAdressesIp()));
            jsonArray.put(json);
        }
        return jsonArray;
    }
    public static JSONArray buildIpsJsonArrayFromObject(List<AdresseIp> ips) throws JSONException{
        JSONArray jsonArray = new JSONArray();
        for(AdresseIp ip : ips){
            JSONObject json = new JSONObject();
            json.put("ipv4", ip.getIpv4());
            json.put("ipv6", ip.getIpv6());
            json.put("masque", ip.getMasque());
            json.put("typeAffectation", buildTypeJsonObject(ip.getTypeAffectation().getId(), ip.getTypeAffectation().getLibelle()));
            jsonArray.put(json);
        }
        return jsonArray;
    }

    //Get infos of Client from BDD at each page of Android APP
    public static Client getInfosClient(int id){
        Client cli = new Client();
        try{
            cli = JsonApiPersistence.parseClientJson(new JSONObject(new HttpGetRequest().execute("http://192.168.1.16:8080/resoapi/api/client/"+id).get()));
        }catch(ExecutionException ee){
            ee.printStackTrace();
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }catch(JSONException je){
            je.printStackTrace();
        }
        return cli;
    }
    public static List<TypeMateriel> getTypesMateriel(){
        List<TypeMateriel> typesMateriel = new ArrayList<>();
        String getURI = "http://192.168.1.16:8080/resoapi/api/typesmateriel";
        HttpGetRequest getRequest = new HttpGetRequest();
        try{
            String result = getRequest.execute(getURI).get();
            for (int i=0; i<JsonApiPersistence.jsonArrayToJsonObjects(new JSONArray(result)).size(); i++){
                TypeMateriel tm = JsonApiPersistence.parseTypeMaterielJson(JsonApiPersistence.jsonArrayToJsonObjects(new JSONArray(result)).get(i));
                typesMateriel.add(tm);
            }
        }catch(ExecutionException ee){
            ee.printStackTrace();
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }catch(JSONException je) {
            je.printStackTrace();
        }
        return typesMateriel;
    }
    public static List<TypeInterface> getTypesInterface(){
        List<TypeInterface> typesInterface = new ArrayList<>();
        String getURI = "http://192.168.1.16:8080/resoapi/api/typesinterface";
        HttpGetRequest getRequest = new HttpGetRequest();
        try{
            String result = getRequest.execute(getURI).get();
            for (int i=0; i<JsonApiPersistence.jsonArrayToJsonObjects(new JSONArray(result)).size(); i++){
                TypeInterface ti = JsonApiPersistence.parseTypeInterfaceJson(JsonApiPersistence.jsonArrayToJsonObjects(new JSONArray(result)).get(i));
                typesInterface.add(ti);
            }
        }catch(ExecutionException ee){
            ee.printStackTrace();
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }catch(JSONException je) {
            je.printStackTrace();
        }
        return typesInterface;
    }
    public static List<TypeAffectation> getTypesAffectation(){
        List<TypeAffectation> typesAffectation = new ArrayList<>();
        String getURI = "http://192.168.1.16:8080/resoapi/api/typesaffectation";
        HttpGetRequest getRequest = new HttpGetRequest();
        try{
            String result = getRequest.execute(getURI).get();
            for (int i=0; i<JsonApiPersistence.jsonArrayToJsonObjects(new JSONArray(result)).size(); i++){
                TypeAffectation ta = JsonApiPersistence.parseTypeAffectationJson(JsonApiPersistence.jsonArrayToJsonObjects(new JSONArray(result)).get(i));
                typesAffectation.add(ta);
            }
        }catch(ExecutionException ee){
            ee.printStackTrace();
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }catch(JSONException je) {
            je.printStackTrace();
        }
        return typesAffectation;
    }
    public static List<Personne> getContacts(int idClient){
        List<Personne> personnes = new ArrayList<Personne>();
        String TypesMaterielGetURI = "http://192.168.1.16:8080/resoapi/api/client/"+idClient+"/contacts";
        HttpGetRequest getRequest = new HttpGetRequest();
        try{
            String result = getRequest.execute(TypesMaterielGetURI).get();
            for (int i=0; i<JsonApiPersistence.jsonArrayToJsonObjects(new JSONArray(result)).size(); i++){
                Personne p = JsonApiPersistence.parsePersonneJson(JsonApiPersistence.jsonArrayToJsonObjects(new JSONArray(result)).get(i));
                personnes.add(p);
            }
        }catch(ExecutionException ee){
            ee.printStackTrace();
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }catch(JSONException je) {
            je.printStackTrace();
        }
        return personnes;
    }
    public static Materiel getInfosMateriel(int id){
        Materiel m = new Materiel();
        try{
            m = JsonApiPersistence.parseMaterielJson(new JSONObject(new HttpGetRequest().execute("http://192.168.1.16:8080/resoapi/api/materiel/"+id).get()));
        }catch(ExecutionException ee){
            ee.printStackTrace();
        }catch(InterruptedException ie){
            ie.printStackTrace();
        }catch(JSONException je){
            je.printStackTrace();
        }
        return m;
    }
}
