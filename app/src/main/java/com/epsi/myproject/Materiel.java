package com.epsi.myproject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

public class Materiel implements Serializable{

    private int id;
    private String libelle;
    private String serial;
    private TypeMateriel type;
    private List<Interface> interfaces;

    public Materiel() {}

    public Materiel(int id, String libelle, String serial) {
        this.setId(id);
        this.setLibelle(libelle);
        this.setSerial(serial);
    }
    public Materiel(int id, String libelle, String serial, TypeMateriel type, List<Interface> interfaces) {
        this.setId(id);
        this.setLibelle(libelle);
        this.setSerial(serial);
        this.setType(type);
        this.setInterfaces(interfaces);
    }

    public Materiel(int id, String libelle, String serial, TypeMateriel type) {
        this.setId(id);
        this.setLibelle(libelle);
        this.setSerial(serial);
        this.setType(type);
    }

    public Materiel(String libelle, String serial, TypeMateriel type, List<Interface> interfaces) {
        this.setLibelle(libelle);
        this.setSerial(serial);
        this.setType(type);
        this.setInterfaces(interfaces);
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
    public String getSerial() {
        return serial;
    }
    public void setSerial(String serial) {
        this.serial = serial;
    }
    public TypeMateriel getType() {
        return type;
    }
    public void setType(TypeMateriel type) {
        this.type = type;
    }
    public List<Interface> getInterfaces() {
        return interfaces;
    }
    public void setInterfaces(List<Interface> interfaces) {
        this.interfaces = interfaces;
    }
}