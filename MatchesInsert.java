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


public class MatchesInsert {
	 
    public static void main(String[] args) {
    	final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
	   	 final String DB_URL = "jdbc:mysql://localhost/FinalProjectHW"; //REPLACE W YOUR DATABASE
	      
	   	 final String USER = "root";
	   	 final String PASSWORD = "root_123";
 
        String csvFilePath = "/Users/eddie/eclipse-workspace/ProjectPart4/src/matches.csv";
 
    
 
        Connection connection = null;
 
        try {
 
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            connection.setAutoCommit(false);
 
            String sql = "INSERT INTO Matches (ID, Date, Points, Num1, Num2, Num3, Num4, Tag) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
 
            BufferedReader lineReader = new BufferedReader(new FileReader(csvFilePath));
            String lineText = null;
 
            
 
            lineReader.readLine(); // skip header line
 
            while ((lineText = lineReader.readLine()) != null) {
                String[] data = lineText.split(",");
                
                
                int ID = Integer.parseInt(data[0]);
                Date Date = java.sql.Date.valueOf(data[1]);
                long Points = Long.parseLong(data[2]);
                int Num1 = Integer.parseInt(data[3]);
                int Num2 = Integer.parseInt(data[4]);
                int Num3 = Integer.parseInt(data[5]);
                int Num4 = Integer.parseInt(data[6]);
                String Tag = data[7];
                
                
 
                statement.setInt(1, ID);
                statement.setDate(2, Date);
                statement.setLong(3, Points);
                statement.setInt(4, Num1);
                statement.setInt(5, Num2);
                statement.setInt(6, Num3);
                statement.setInt(7, Num4);
                statement.setString(7, Tag);
 
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


