package com.model;

public class User {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String password;

    // Constructeur vide
    public User() {}

    // Constructeur complet (sans l'ID)
    public User(String nom, String prenom, String email, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
    }

    // Constructeur avec ID (utile pour récupérer un utilisateur existant)
    public User(int id, String nom, String prenom, String email, String password) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
        return "Utilisateur [ID=" + id + ", Nom=" + nom + ", Prénom=" + prenom + ", Email=" + email + "]";
    }
}
