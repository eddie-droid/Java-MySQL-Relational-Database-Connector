import java.io.BufferedReader; 
import java.io.IOException; 
import java.nio.charset.StandardCharsets;
 import java.nio.file.Files; 
 import java.nio.file.Path; 
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList; 
import java.util.List;
import java.io.File;
import java.io.FileReader;


public class PlayersInsert {
	 
    public static void main(String[] args) {
    	final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	   	 final String DB_URL = "jdbc:mysql://localhost/FinalProjectHW"; //REPLACE W YOUR DATABASE
	      
	   	 final String USER = "root";
	   	 final String PASSWORD = "root_123";
 
        String csvFilePath = "/Users/eddie/eclipse-workspace/ProjectPart4/src/players.csv";
 
    
 
        Connection connection = null;
 
        try {
 
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            connection.setAutoCommit(false);
 
            String sql = "INSERT INTO Players (ID, Tag, Nickname, DOB, Nationality, Race) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
 
            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;
 
            
 
            lineReader.readLine(); // skip header line
 
            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                
                
                int ID = Integer.parseInt(data[0]);
                String Tag = data[1];
                String Nickname = data[2];
                String Nationality = data[3];
                Date DOB = java.sql.Date.valueOf(data[4]);
                String Race = data[5];
                
 
                statement.setInt(1, ID);
                statement.setString(2, Tag);
                statement.setString(3, Nickname);
                statement.setDate(4, DOB);
                statement.setString(5, Nationality);
                statement.setString(6, Race);
 
                statement.addBatch();
 
               statement.executeBatch();
                
            }
 
            lineReader.close();
 
            // execute the remaining queries
            statement.executeBatch();
 
            connection.commit();
            connection.close();
 
        } catch (IOException ex) {
            System.err.println(ex);
        } catch (SQLException ex) {
            ex.printStackTrace();
 
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
 
    }
}


