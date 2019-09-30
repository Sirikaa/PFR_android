package com.epsi.myproject;

import java.io.Serializable;
import java.util.List;

public class Interface implements Serializable{

    private int id;
    private String nom;
    private String mac;
    private TypeInterface type;
    private List<AdresseIp> adressesIp;

    public Interface() {

    }
    public Interface(int id, String nom, String mac, TypeInterface type, List<AdresseIp> adressesIp) {
        this.setId(id);
        this.setNom(nom);
        this.setMac(mac);
        this.setType(type);
        this.setAdressesIp(adressesIp);
    }

    public Interface(int id, String nom, String mac, TypeInterface type) {
        this.setId(id);
        this.setNom(nom);
        this.setMac(mac);
        this.setType(type);
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
    public String getMac() {
        return mac;
    }
    public void setMac(String mac) {
        this.mac = mac;
    }
    public TypeInterface getType() {
        return type;
    }
    public void setType(TypeInterface type) {
        this.type = type;
    }
    public List<AdresseIp> getAdressesIp() {
        return adressesIp;
    }
    public void setAdressesIp(List<AdresseIp> adressesIp) {
        this.adressesIp = adressesIp;
    }
}
