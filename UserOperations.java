package project_bankApplication;

import java.sql.*;

public class UserOperations {

    
    public static double checkBalance(String username) throws Exception {
        String query = "SELECT balance FROM users WHERE username = ?";
        double balance = 0.0;

        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = SetConnection.getConnection();  
            ps = connection.prepareStatement(query); 
            ps.setString(1, username); 
            rs = ps.executeQuery();  

            if (rs.next()) {
                
                balance = rs.getDouble("balance");
            } else {
                
                throw new SQLException("User not found.");
            }

        } catch (SQLException e) {
            System.out.println("Error fetching balance: " + e.getMessage());
            throw e;  
        } finally {
            
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (connection != null) connection.close();
        }

        return balance;  
    }

    
}
