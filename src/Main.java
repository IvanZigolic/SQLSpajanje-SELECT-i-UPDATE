import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = createDataSource();
        try(Connection connection = dataSource.getConnection(); Statement stmt = connection.createStatement()){
            System.out.println("Database connection established");
            //1. Preimenovanje drzave u SQL bazi
            int rowsAffected = stmt.executeUpdate("UPDATE Drzava SET Naziv='Hrvatska' WHERE Naziv = 'Croatia'");
            System.out.println("Country has been updated!");

            //int rowsAffected = stmt.executeUpdate("UPDATE Drzava SET Naziv='Hrvatska' WHERE IDDrzava=1");
            //String drzava = "Hrvatska";
            //String sql = "UPDATE Drzava SET Naziv='Hrvatska' WHERE IDDrzava=1";
            //String sql = "UPDATE Drzava SET Naziv='" + drzava +"' WHERE IDDrzava=" + 1;
            //String sql1 = String.format("UPDATE Drzava SET Naziv='%s' WHERE IDDrzava=1", drzava);
            //2. Broj redaka affectan
            //int rowsAffected = stmt.executeUpdate(sql1);

            //System.out.println("Država je uspješno preimenovana!");
            //System.out.println("Broj promjenjenih redaka: " + rowsAffected);

            //3. Dohvacanje drzava SELECT
            ResultSet rs = stmt.executeQuery("SELECT IDDrzava, Naziv FROM Drzava");
            while (rs.next()) {
                System.out.printf("%d %s\n", rs.getInt("IDDrzava"), rs.getString("Naziv"));
            }
            rs.close();
            stmt.close();
        } catch(SQLException e){
            System.err.println("Database connection establishment failed");
            e.printStackTrace();
        }
    }

    //Metoda za stvaranje DataSource objekta
    private static DataSource createDataSource() {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName("localhost");
        ds.setDatabaseName("AdventureWorksOBP");
        ds.setUser("sa");
        ds.setPassword("SQL");
        ds.setEncrypt(false);
        return ds;
    }
}