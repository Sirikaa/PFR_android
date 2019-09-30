package com.epsi.myproject;

import java.io.Serializable;

public class Fonction implements Serializable {
    private int id;
    private String libelle;

    public Fonction() {

    }

    public Fonction(int id, String libelle) {
        this.setId(id);
        this.setLibelle(libelle);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }
}
