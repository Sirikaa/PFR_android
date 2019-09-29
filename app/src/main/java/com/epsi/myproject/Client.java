package com.epsi.myproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Client implements Serializable {



    private String id;
    private String nom;
    private String adresse1;
    private String adresse2;
    private String ville;
    private String cp;
    private List<Contact> liste_contacts = new ArrayList<Contact>();
    private  List<Materiel> liste_materiels = new ArrayList<Materiel>();


    public void setNom(String str){
        this.nom = str;
    }
    public void setAdresse1(String str){
        this.adresse1 = str;
    }
    public void setAdresse2(String str){
        this.adresse2 = str;
    }
    public void setVille(String str){
        this.ville = str;
    }
    public void setCp(String str){
        this.cp = str;
    }
    public void setListe_contacts(List<Contact> liste_contacts) {
        this.liste_contacts = liste_contacts;
    }
    public void setContact(Contact c){
        this.liste_contacts.add(c);
    }
    public void setListe_materiels(List<Materiel> liste_materiels) {
        this.liste_materiels = liste_materiels;
    }
    public void setMateriel(Materiel m){
        this.liste_materiels.add(m);
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getNom(){
        return this.nom;
    }
    public String getAdresse1(){
        return this.adresse1;
    }
    public String getAdresse2(){
        return this.adresse2;
    }
    public String getVille(){
        return this.ville;
    }
    public String getCp(){
        return this.cp;
    }
    public List<Contact> getListe_contacts() {
        return liste_contacts;
    }
    public void getListe_contacts(List<Contact> liste_contacts) {
        this.liste_contacts = liste_contacts;
    }
    public List<Materiel> getListe_materiels() {
        return liste_materiels;
    }
    public String getId() {
        return id;
    }


}
