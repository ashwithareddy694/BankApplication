package project_bankApplication;

import java.sql.*;

public class UserLogin {

    
    public static boolean login(String username, String password) throws Exception {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            
            connection = SetConnection.getConnection();

            
            String query = "SELECT password_hash FROM users WHERE username = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, username); 
            rs = ps.executeQuery(); 

            if (rs.next()) {
                
                String storedHash = rs.getString("password_hash");

                
                return PasswordUtils.verifyPassword(password, storedHash); 
            } else {
                
                System.out.println("Invalid username.");
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Login error: " + e.getMessage());
            throw e; 
        } finally {
            
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println("Error closing ResultSet: " + e.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    System.out.println("Error closing PreparedStatement: " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing Connection: " + e.getMessage());
                }
            }
        }
    }
}
