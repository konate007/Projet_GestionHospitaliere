package com.example.projet_gestion_hospitaliere.model;

public class RendezVous {

    private long id ;
    private String nom ;
    private String heure ;
    private String date ;
    private  String image ;

    public RendezVous() {
    }

    public RendezVous(String nom, String heure, String date, String image) {
        this.nom = nom;
        this.heure = heure;
        this.date = date;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getHeure() {
        return heure;
    }

    public String getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
