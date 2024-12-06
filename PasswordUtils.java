package project_bankApplication;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class PasswordUtils {

    public static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] hashedBytes = messageDigest.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte b : hashedBytes) {
            sb.append(String.format("%02x", b)); 
        }
        return sb.toString();
    }

    public static boolean verifyPassword(String plainPassword, String hashedPassword) throws NoSuchAlgorithmException {
        return hashPassword(plainPassword).equals(hashedPassword); 
    }

    
    public static boolean storeHashedPassword(Connection connection, String username, String plainPassword) throws SQLException, NoSuchAlgorithmException {
        String hashedPassword = hashPassword(plainPassword); 
        String query = "UPDATE users SET password_hash = ? WHERE username = ?"; 

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, hashedPassword);
            ps.setString(2, username);       
            int rowsUpdated = ps.executeUpdate(); 
            return rowsUpdated > 0; 
        }
    }

    
    public static String getStoredPasswordHash(Connection connection, String username) throws SQLException {
        String query = "SELECT password_hash FROM users WHERE username = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username); 
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("password_hash"); 
            } else {
                return null; 
            }
        }
    }
}
