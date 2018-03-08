package com.example.sadjang.codepenal.Objets;

/**
 * Created by sadjang on 16/11/2016.
 */
public class Article {
    int numero;
    String Nom;
    String section;
    String chapitre;
    String titre;
    String text;
    int numDepart;
    int numFin;


    public Article(int numero, String nom, String section, String chapitre, String titre,String text,int numDepart,int numFin) {
        this.numero = numero;
        Nom = nom;
        this.section = section;
        this.chapitre = chapitre;
        this.titre = titre;
        this.text=text;
        this.numDepart=numDepart;
        this.numFin=numFin;
    }

    public int getNumDepart() {
        return numDepart;
    }

    public void setNumDepart(int numDepart) {
        this.numDepart = numDepart;
    }

    public int getNumFin() {
        return numFin;
    }

    public void setNumFin(int numFin) {
        this.numFin = numFin;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNom() {
        return Nom;
    }

    public void setNom(String nom) {
        Nom = nom;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getChapitre() {
        return chapitre;
    }

    public void setChapitre(String chapitre) {
        this.chapitre = chapitre;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}
