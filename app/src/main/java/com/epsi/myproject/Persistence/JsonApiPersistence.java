package com.epsi.myproject.Persistence;

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
    public static Materiel parseMaterielJson(JSONObject json) throws JSONException{
        return new Materiel(json.getInt("id"), json.getString("libelle"), json.getString("serial"), parseTypeMaterielJson(json.getJSONObject(TYPE_MATERIEL_JSON_ARRAY_NAME)), parseInterfacesJsonArray(json.getJSONArray(INTERFACES_JSON_ARRAY_NAME)));
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
}
