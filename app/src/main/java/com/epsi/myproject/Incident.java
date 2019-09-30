package com.epsi.myproject;

import java.io.Serializable;

public class Incident implements Serializable {

    private int id;
    private String dateOuverture;
    private String dateFermeture;
    private String descriptionSymptomes;
    private String descriptionResolution;
    private Statut statut;
    private Client client;
    private Materiel materiel;

    public Incident() {}

    public Incident(String dateOuverture, String descriptionSymptomes, Statut statut, Client client, Materiel materiel) {
        this.setDateOuverture(dateOuverture);
        this.setDescriptionSymptomes(descriptionSymptomes);
        this.setStatut(statut);
        this.setClient(client);
        this.setMateriel(materiel);
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDateOuverture() {
        return dateOuverture;
    }
    public void setDateOuverture(String dateOuverture) {
        this.dateOuverture = dateOuverture;
    }
    public String getDateFermeture() {
        return dateFermeture;
    }
    public void setDateFermeture(String dateFermeture) {
        this.dateFermeture = dateFermeture;
    }
    public String getDescriptionSymptomes() {
        return descriptionSymptomes;
    }
    public void setDescriptionSymptomes(String descriptionSymptomes) {
        this.descriptionSymptomes = descriptionSymptomes;
    }
    public String getDescriptionResolution() {
        return descriptionResolution;
    }
    public void setDescriptionResolution(String descriptionResolution) {
        this.descriptionResolution = descriptionResolution;
    }
    public Client getClient() {
        return client;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public Materiel getMateriel() {
        return materiel;
    }
    public void setMateriel(Materiel materiel) {
        this.materiel = materiel;
    }
    public Statut getStatut() {
        return statut;
    }
    public void setStatut(Statut statut) {
        this.statut = statut;
    }
}
