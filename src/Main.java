import com.microsoft.sqlserver.jdbc.SQLServerDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataSource dataSource = createDataSource();
        boolean petlja = true;
        try(Connection connection = dataSource.getConnection(); Statement stmt = connection.createStatement()){
            System.out.println("Database connection established");
            Scanner sc = new Scanner(System.in);
            do {
                System.out.println("\nDATABAZA ADVENTUREWORKS\n1. Nova drzava\n2. Izmjena postojece drzave\n3. Brisanje postojece drzave\n4. Prikaz drzava sortiranih po nazivu\n5. Izlaz");
                try {
                    String unos = sc.nextLine();
                    switch (unos) {
                        case "1":
                        case "Nova drzava":
                            System.out.println("Unesite naziv nove drzave: ");
                            String naziv = sc.nextLine();
                            stmt.executeUpdate("INSERT INTO Drzava(Naziv) VALUES ('" + naziv + "')");
                            System.out.println("Uspjesno ste dodali drzavu " + naziv + " u databazu!");
                            break;
                        case "2":
                        case "Izmjena postojece drzave":
                            IspisDrzava();
                            System.out.println("Odaberite ID Drzave koju zelite izmjeniti: ");
                            int id = Integer.parseInt(sc.nextLine());
                            System.out.println("Unesite naziv u koji joj zelite izmjeniti: ");
                            String ime = sc.nextLine();
                            stmt.executeUpdate("UPDATE Drzava SET Naziv='" + ime + "' WHERE IDDrzava=" + id);
                            System.out.println("Drzavi pod ID-em " + id + " ste promjenili ime u " + ime);
                            break;
                        case "3":
                        case "Brisanje postojece drzave":
                            IspisDrzava();
                            System.out.println("Odaberite ID Drzave koju zelite obrisati: ");
                            id = Integer.parseInt(sc.nextLine());
                            stmt.executeUpdate("DELETE FROM Drzava WHERE IDDrzava =" + id);
                            System.out.println("Drzava je obrisana!");
                            break;
                        case "4":
                        case "Prikaz drzava sortiranih po nazivu":
                            IspisDrzavaSORT();
                            break;
                        case "5":
                        case "Izlaz":
                            petlja = false;
                            System.out.println("Izasli ste iz databaze!");
                            break;
                        default:
                            throw new Exception("Pogresan unos!");
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            } while (petlja);
            sc.close();
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
    private static void IspisDrzava(){
        DataSource dataSource = createDataSource();
    try(Connection connection = dataSource.getConnection(); Statement stmt2 = connection.createStatement()){
        ResultSet rs = stmt2.executeQuery("SELECT IDDrzava, Naziv FROM Drzava");
        while (rs.next()) {
            System.out.printf("%d %s\n", rs.getInt("IDDrzava"), rs.getString("Naziv"));
        }
    }
    catch(SQLException e){
        System.err.println("Database connection establishment failed");
    }
    }
    private static void IspisDrzavaSORT() {
        System.out.println("Prikaz drzava sortiranih po nazivu: ");
        DataSource dataSource2 = createDataSource();
        try (Connection connection = dataSource2.getConnection(); Statement stmt2 = connection.createStatement()) {
            ResultSet rs = stmt2.executeQuery("SELECT IDDrzava, Naziv FROM Drzava ORDER BY Naziv ASC");
            while (rs.next()) {
                System.out.printf("%d %s\n", rs.getInt("IDDrzava"), rs.getString("Naziv"));
            }
        } catch (SQLException e) {
            System.err.println("Database connection establishment failed");
        }
    }
}