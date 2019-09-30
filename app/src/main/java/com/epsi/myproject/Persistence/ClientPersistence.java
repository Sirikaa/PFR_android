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

public abstract class ClientPersistence {

    public static Client parseClientJson(JSONObject json) throws JSONException {
        Log.i("FSD", "Entrée dans la persistance");
        Client c = new Client(json.getInt("id"), json.getString("nom"), json.getString("matricule"), json.getString("password"), json.getString("adresse1"), json.getString("adresse2"));
        Log.i("FSD", "Client créé !");

        //VILLE
        Ville v = new Ville(json.getJSONObject("ville").getInt("id"), json.getJSONObject("ville").getString("cp"), json.getJSONObject("ville").getString("ville"));
        c.setVille(v);
        Log.i("FSD", "Ville ajoutée");

        //CONTACTS
        List<Personne> contacts = new ArrayList<Personne>();
        for(int i=0; i<json.getJSONArray("contacts").length(); i++){
            JSONObject jsonPersonne = json.getJSONArray("contacts").getJSONObject(i);
            Personne p = new Personne(jsonPersonne.getInt("id"), jsonPersonne.getString("nom"), jsonPersonne.getString("prenom"), jsonPersonne.getString("email"), jsonPersonne.getString("telephone"));
            contacts.add(p);
        }
        c.setContacts(contacts);
        Log.i("FSD", "Contacts ajoutés !");

        //MATERIELS
        List<Materiel> materiels = new ArrayList<Materiel>();
        List<Interface> interfaces = new ArrayList<Interface>();
        List<AdresseIp> adressesIp = new ArrayList<AdresseIp>();

        for(int i=0; i<json.getJSONArray("materiels").length(); i++){
            JSONObject jsonMateriel = json.getJSONArray("materiels").getJSONObject(i);
            JSONObject jsonTypeMateriel = jsonMateriel.getJSONObject("type");
            TypeMateriel typeMateriel = new TypeMateriel(jsonTypeMateriel.getInt("id"), jsonTypeMateriel.getString("libelle"));
            Log.i("FSD", "Type Matériel : "+typeMateriel.getId()+" - "+typeMateriel.getLibelle());

            Materiel materiel = new Materiel(jsonMateriel.getInt("id"), jsonMateriel.getString("libelle"), jsonMateriel.getString("serial"), typeMateriel);
            Log.i("FSD", "Matériel : "+materiel.getLibelle());

            JSONArray jsonArrayInterfaces = jsonMateriel.getJSONArray("interfaces");
            for(int j=0; j<jsonArrayInterfaces.length(); j++){
                JSONObject jsonInterface = jsonArrayInterfaces.getJSONObject(j);
                JSONObject jsonTypeInterface = jsonInterface.getJSONObject("type");
                TypeInterface typeInterface = new TypeInterface(jsonTypeInterface.getInt("id"), jsonTypeInterface.getString("libelle"));
                Log.i("FSD", "Type Interface :"+typeInterface.getId()+" - "+typeInterface.getLibelle());

                Interface itf = new Interface(jsonInterface.getInt("id"), jsonInterface.getString("nom"), jsonInterface.getString("mac"), typeInterface);
                Log.i("FSD", "Interface : "+itf.getNom());

                JSONArray jsonArrayAdressesIp = jsonInterface.getJSONArray("adressesIp");
                for(int k=0; k<jsonArrayAdressesIp.length(); k++){
                    Log.i("FSD", "Entrée dans la boucle pour les IPS");
                    JSONObject jsonAdresseIp = jsonArrayAdressesIp.getJSONObject(k);
                    JSONObject jsonTypeAffectation = jsonAdresseIp.getJSONObject("typeAffectation");
                    TypeAffectation typeAffectation = new TypeAffectation(jsonTypeAffectation.getInt("id"), jsonTypeAffectation.getString("libelle"));
                    Log.i("FSD", "Type Affectation"+typeAffectation.getLibelle());

                    AdresseIp adresseIp = new AdresseIp(jsonAdresseIp.getInt("id"), jsonAdresseIp.getString("ipv4"), jsonAdresseIp.getString("ipv6"), jsonAdresseIp.getString("masque"), typeAffectation);
                    Log.i("FSD", "Adresse IP : "+adresseIp.getIpv4());
                    adressesIp.add(adresseIp);
                    Log.i("FSD", "Adresse IP ajoutée !");

                }
                itf.setAdressesIp(adressesIp);
                Log.i("FSD", "Adresses IP ajoutées à l'interface !");
                interfaces.add(itf);
                Log.i("FSD", "Interface ajoutée !");
            }
            materiel.setInterfaces(interfaces);
            Log.i("FSD", "Interfaces ajoutées au matériel !");
            materiels.add(materiel);
            Log.i("FSD", "Matériel ajouté !");
        }
        c.setMateriels(materiels);
        Log.i("FSD", "Matériels ajoutés au client!");
        return c;
    }
}
