package com.epsi.myproject;

import java.io.Serializable;

public class Interface implements Serializable {

    private String id;
    private String nom;
    private String mac;
    private String type;
    private String adresseIpv4;
    private String adresseIpv6;
    private String masque;
    private String typeAffectation;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getAdresseIpv4() {
        return adresseIpv4;
    }

    public void setAdresseIpv4(String adresseIpv4) {
        this.adresseIpv4 = adresseIpv4;
    }
    public String getAdresseIpv6() {
        return adresseIpv6;
    }

    public void setAdresseIpv6(String adresseIpv6) {
        this.adresseIpv6 = adresseIpv6;
    }
    public String getMasque() {
        return masque;
    }

    public void setMasque(String masque) {
        this.masque = masque;
    }
    public String getTypeAffectation() {
        return typeAffectation;
    }

    public void setTypeAffectation(String typeAffectation) {
        this.typeAffectation = typeAffectation;
    }

}
