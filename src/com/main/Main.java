package com.main;

import com.dao.BibliothequeDAO;
import com.dao.UserDAO;
import com.model.Bibliotheque;
import java.sql.SQLException;
import com.model.User;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        BibliothequeDAO bibliothequeDAO = new BibliothequeDAO();
        UserDAO userDAO = new UserDAO();


        while (true) {
            System.out.println("\n MENU PRINCIPAL");
            System.out.println("1. Gérer les livres");
            System.out.println("2. Gérer les utilisateurs");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");
            int choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1 -> gestionLivres(bibliothequeDAO, scanner);
                case 2 -> gestionUtilisateurs(userDAO, scanner);
                case 0 -> {
                    System.out.println("Au revoir !");
                    scanner.close();
                    return;
                }
                default -> System.out.println("Choix invalide !");
            }
        }
    }

    private static void gestionUtilisateurs(UserDAO userDAO, Scanner scanner) {
        // On initialise la variable choix à 0 avant la boucle
        int choix = 0;
       // int choix;
        do {
            System.out.println("\n👤 GESTION DES UTILISATEURS 👤");
            System.out.println("1. Ajouter un utilisateur");
            System.out.println("2. Modifier un utilisateur");
            System.out.println("3. Supprimer un utilisateur");
            System.out.println("4. Afficher tous les utilisateurs");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

            // Utiliser Integer.parseInt(scanner.nextLine()) au lieu de scanner.nextInt()
            try {
                choix = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Erreur : veuillez entrer un nombre !");
                continue; // Redemander un choix valide
            }

            switch (choix) {
                case 1 -> {
                    System.out.print("Nom : ");
                    String nom = scanner.nextLine();
                    System.out.print("Prénom : ");
                    String prenom = scanner.nextLine();
                    System.out.print("Email : ");
                    String email = scanner.nextLine();
                    System.out.print("Mot de passe : ");
                    String password = scanner.nextLine();
                    userDAO.addUser(new User(nom, prenom, email, password));
                }
                case 2 -> {
                    System.out.print("ID de l'utilisateur à modifier : ");
                    // Lire correctement l'ID
                    int id = Integer.parseInt(scanner.nextLine());
                    System.out.print("Nouveau nom : ");
                    String nom = scanner.nextLine();
                    System.out.print("Nouveau prénom : ");
                    String prenom = scanner.nextLine();
                    System.out.print("Nouvel email : ");
                    String email = scanner.nextLine();
                    userDAO.updateUser(id, nom, prenom, email);
                }
                case 3 -> {
                    System.out.print("ID de l'utilisateur à supprimer : ");
                    int id = Integer.parseInt(scanner.nextLine());
                    userDAO.deleteUser(id);
                }
                case 4 -> {
                    System.out.println("\n Liste des utilisateurs :");
                    userDAO.getAllUsers().forEach(System.out::println);
                }
                case 0 -> System.out.println("Au revoir !");
                default -> System.out.println("Choix invalide, veuillez réessayer.");
            }
        } while (choix != 0);
    }


    public static void gestionLivres(BibliothequeDAO bibliothequeDAO, Scanner scanner) {
        int choix;
        do {
            System.out.println("\n Gestion des livres");
            System.out.println("1. Ajouter un livre");
            System.out.println("2. Modifier un livre");
            System.out.println("3. Supprimer un livre");
            System.out.println("4. Afficher tous les livres");
            System.out.println("0. Quitter");
            System.out.print("Votre choix : ");

            choix = scanner.nextInt();
            scanner.nextLine(); // Pour éviter le problème de saut de ligne

            switch (choix) {
                case 1 -> bibliothequeDAO.addLivre();
                case 2 -> bibliothequeDAO.updateLivre();
                case 3 -> bibliothequeDAO.removeLivre();
                case 4 -> bibliothequeDAO.findAll();
                case 0 -> System.out.println("Au revoir !");
                default -> System.out.println("Choix invalide !");
            }
        } while (choix != 0);
    }
}
