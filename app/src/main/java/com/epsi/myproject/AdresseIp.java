package com.epsi.myproject;

import java.io.Serializable;

public class AdresseIp implements Serializable {

    private int id;
    private String ipv4;
    private String ipv6;
    private String masque;
    private TypeAffectation typeAffectation;

    public AdresseIp() {

    }
    public AdresseIp(int id, String ipv4, String ipV6, String masque, TypeAffectation typeAffectation) {
        this.setId(id);
        this.setIpv4(ipv4);
        this.setIpv6(ipV6);
        this.setMasque(masque);
        this.setTypeAffectation(typeAffectation);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getIpv4() {
        return ipv4;
    }
    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }
    public String getIpv6() {
        return ipv6;
    }
    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
    }
    public String getMasque() {
        return masque;
    }
    public void setMasque(String masque) {
        this.masque = masque;
    }
    public TypeAffectation getTypeAffectation() {
        return typeAffectation;
    }
    public void setTypeAffectation(TypeAffectation typeAffectation) {
        this.typeAffectation = typeAffectation;
    }
}

