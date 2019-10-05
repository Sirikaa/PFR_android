package com.epsi.myproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Client implements Serializable{

    private int id;
    private String nom;
    private String matricule;
    private String password;
    private String adresse1;
    private String adresse2;
    private Ville ville;
    private List<Personne> contacts;
    private List<Materiel> materiels;

    public Client() {

    }
    public Client(int id, String nom, String matricule, String password, String adresse1, String adresse2, Ville ville, List<Personne> personnes, List<Materiel> materiels) {
        this.setId(id);
        this.setNom(nom);
        this.setMatricule(matricule);
        this.setPassword(password);
        this.setAdresse1(adresse1);
        this.setAdresse2(adresse2);
        this.setVille(ville);
        this.setContacts(personnes);
        this.setMateriels(materiels);
    }

    public Client(int id, String nom, String matricule, String password, String adresse1, String adresse2) {
        this.setId(id);
        this.setNom(nom);
        this.setMatricule(matricule);
        this.setPassword(password);
        this.setAdresse1(adresse1);
        this.setAdresse2(adresse2);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getAdresse1() {
        return adresse1;
    }
    public void setAdresse1(String adresse1) {
        this.adresse1 = adresse1;
    }
    public String getAdresse2() {
        return adresse2;
    }
    public void setAdresse2(String adresse2) {
        this.adresse2 = adresse2;
    }
    public List<Personne> getContacts() {
        return contacts;
    }
    public void setContacts(List<Personne> contacts) {
        this.contacts = contacts;
    }
    public List<Materiel> getMateriels() {
        return materiels;
    }
    public void setMateriels(List<Materiel> materiels) {
        this.materiels = materiels;
    }
    public String getMatricule() {
        return matricule;
    }
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Ville getVille() {
        return ville;
    }
    public void setVille(Ville ville) {
        this.ville = ville;
    }
    public void addMateriel(Materiel m){
        List<Materiel> materiels = this.getMateriels();
        materiels.add(m);
        this.setMateriels(materiels);
    }
}
