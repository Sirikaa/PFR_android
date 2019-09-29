package com.epsi.myproject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Materiel implements Serializable {

    private String id;
    private String libelle;
    private String typeMateriel;
    private int idTypeMateriel;
    private List<Interface> liste_interfaces = new ArrayList<Interface>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public List<Interface> getListe_interfaces() {
        return liste_interfaces;
    }

    public void setListe_interfaces(List<Interface> liste_interfaces) {
        this.liste_interfaces = liste_interfaces;
    }

    public void setInterface(Interface i){
        this.liste_interfaces.add(i);
    }

    public String getTypeMateriel() {
        return typeMateriel;
    }

    public void setTypeMateriel(String typeMateriel) {
        this.typeMateriel = typeMateriel;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getIdTypeMateriel() {
        return idTypeMateriel;
    }

    public void setIdTypeMateriel(int idTypeMateriel) {
        this.idTypeMateriel = idTypeMateriel;
    }


}
