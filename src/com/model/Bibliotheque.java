package com.model;
import com.dao.DatabaseDAO;

import java.sql.*;
import java.util.Scanner;

public class Bibliotheque {
    private Connection conn; // Connexion à la base de données

    // Constructeur qui initialise la connexion à MySQL
   /* public Bibliotheque() {
        try {
            conn = Database.getConnection(); // Connexion à la BDD
            if (conn == null) {
                System.out.println("ERREUR : Connexion NULL !");
            } else {
                System.out.println("Connexion MySQL réussie !");
            }
        } catch (SQLException e) {
            System.out.println("ERREUR : Impossible de se connecter !");
            e.printStackTrace();
        }
    }   */
    public Bibliotheque() {
        try {
            System.out.println("🔍 Tentative d'initialisation de la connexion...");
            this.conn = DatabaseDAO.getConnection(); // Essaye d'obtenir la connexion

            if (this.conn == null) {
                System.out.println("ERREUR : Connexion toujours NULL !");
            } else {
                System.out.println("Connexion MySQL réussie dans Bibliotheque !");
            }
        } catch (SQLException e) {
            System.out.println("ERREUR : Impossible de se connecter !");
            e.printStackTrace();
        }
    }


    // 1. Ajouter un livre
    public void addLivre() {
        if (conn == null) {
            System.out.println("ERREUR : Connexion NULL dans addLivre() !");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Titre : ");
        String titre = scanner.nextLine();

        System.out.print("Description : ");
        String description = scanner.nextLine();

        System.out.print("Date de publication (AAAA-MM-JJ) : ");
        String datePublication = scanner.nextLine();

        System.out.print("Genres : ");
        String genre = scanner.nextLine();

        String sql = "INSERT INTO livre (titre, description, date_publication, genre) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titre);
            stmt.setString(2, description);
            stmt.setString(3, datePublication);
            stmt.setString(4, genre);
            stmt.executeUpdate();
            System.out.println("Livre ajouté !");
        } catch (SQLException e) {
            System.out.println("ERREUR SQL : Impossible d'ajouter le livre !");
            e.printStackTrace();
        }
    }

    // 2. Modifier un livre
    public void updateLivre() {
        if (conn == null) {
            System.out.println("ERREUR : Connexion NULL dans updateLivre() !");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("ID du livre à modifier : ");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consommer la ligne vide

        // Vérifier si le livre existe
        String checkSql = "SELECT * FROM livre WHERE id = ?";
        try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
            checkStmt.setInt(1, id);
            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                System.out.println("Aucun livre trouvé avec l'ID " + id);
                return;
            }
        } catch (SQLException e) {
            System.out.println("ERREUR SQL : Impossible de vérifier le livre !");
            e.printStackTrace();
            return;
        }

        // Demander les nouvelles valeurs
        System.out.print("Nouveau titre (laisser vide pour ne pas changer) : ");
        String titre = scanner.nextLine();

        System.out.print("Nouvelle description (laisser vide pour ne pas changer) : ");
        String description = scanner.nextLine();

        System.out.print("Nouvelle date de publication (AAAA-MM-JJ, laisser vide pour ne pas changer) : ");
        String datePublication = scanner.nextLine();

        System.out.print("Nouveau genre (laisser vide pour ne pas changer) : ");
        String genre = scanner.nextLine();

        // Construire la requête SQL dynamique
        StringBuilder sql = new StringBuilder("UPDATE livre SET ");
        boolean needComma = false;

        if (!titre.isEmpty()) {
            sql.append("titre = ?");
            needComma = true;
        }
        if (!description.isEmpty()) {
            if (needComma) sql.append(", ");
            sql.append("description = ?");
            needComma = true;
        }
        if (!datePublication.isEmpty()) {
            if (needComma) sql.append(", ");
            sql.append("date_publication = ?");
            needComma = true;
        }
        if (!genre.isEmpty()) {
            if (needComma) sql.append(", ");
            sql.append("genre = ?");
        }

        sql.append(" WHERE id = ?");

        try (PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (!titre.isEmpty()) stmt.setString(paramIndex++, titre);
            if (!description.isEmpty()) stmt.setString(paramIndex++, description);
            if (!datePublication.isEmpty()) stmt.setString(paramIndex++, datePublication);
            if (!genre.isEmpty()) stmt.setString(paramIndex++, genre);

            stmt.setInt(paramIndex, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("✅ Livre mis à jour !");
            } else {
                System.out.println("Aucun livre modifié.");
            }
        } catch (SQLException e) {
            System.out.println("ERREUR SQL : Impossible de modifier le livre !");
            e.printStackTrace();
        }
    }

    // 3. Supprimer un livre
    public void removeLivre() {
        if (conn == null) {
            System.out.println("ERREUR : Connexion NULL dans removeLivre() !");
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("ID du livre à supprimer : ");
        int id = scanner.nextInt();
        scanner.nextLine();

        String sql = "DELETE FROM livre WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Livre supprimé !");
            } else {
                System.out.println("Aucun livre trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.out.println("ERREUR SQL : Impossible de supprimer le livre !");
            e.printStackTrace();
        }
    }

    // 4. Afficher tous les livres
    public void findAll() {
        if (conn == null) {
            System.out.println("ERREUR : Connexion NULL dans findAll() !");
            return;
        }

        String sql = "SELECT * FROM livre";
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n Liste des livres :");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + " | Titre: " + rs.getString("titre") +
                        " | Description: " + rs.getString("description") +
                        " | Date: " + rs.getString("date_publication") +
                        " | Genre: " + rs.getString("genre"));
            }
        } catch (SQLException e) {
            System.out.println("ERREUR SQL : Impossible d'afficher les livres !");
            e.printStackTrace();
        }
    }
}
