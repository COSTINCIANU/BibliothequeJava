package com.dao;

import com.model.User;
//import org.mindrot.jbcrypt.BCrypt;
import BCrypt.BCrypt;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
   /*  public static void main(String[] args) {
        String candidate = "$2a$10$pcR4SaZd3PMD/nXQKMssxupMLncDoFwfU7avg/wdpLVChNqGOXbLu";
        String password = "123";

        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());

        if (BCrypt.checkpw(candidate, hashed)) {
            System.out.println("It matches");
        }

        else {
            System.out.println("It does not match");
        }
    } */

    private Connection conn;


    /* public UserDAO() throws SQLException {
        this.conn = DatabaseDAO.getConnection();
    } */

    public UserDAO() {
        try {
            conn = DatabaseDAO.getConnection();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
        }
    }

    // Ajouter un utilisateur avec mot de passe hashé
    public void addUser(User user) {
        String sql = "INSERT INTO users (nom, prenom, email, password) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getPrenom());
            stmt.setString(3, user.getEmail());
            // Hachage du mot de passe
            stmt.setString(4, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
            stmt.executeUpdate();
            System.out.println("Utilisateur ajouté !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    // Modifier un utilisateur (sans changer le mot de passe)
    public void updateUser(int id, String nom, String prenom, String email) {
        String sql = "UPDATE users SET nom=?, prenom=?, email=? WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nom);
            stmt.setString(2, prenom);
            stmt.setString(3, email);
            stmt.setInt(4, id);
            stmt.executeUpdate();
            System.out.println("Utilisateur modifié !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification : " + e.getMessage());
        }
    }

    // Supprimer un utilisateur
    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Utilisateur supprimé !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    // Afficher tous les utilisateurs
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        // Ne pas récupérer le password
        String sql = "SELECT id, nom, prenom, email FROM users";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setEmail(rs.getString("email"));
                users.add(user);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return users;
    }
}
