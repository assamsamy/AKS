import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;



public class ConnectionSQL {

    private Connection conn;

    public ConnectionSQL() {

        String myDriver = "com.mysql.jdbc.Driver";


        try {
            Class.forName(myDriver);
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/aaskeydb?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertAcess(String ID_carte, int ScannerID, int validity){
        String sql="INSERT INTO acces(ID_carte, ID_scanner, acces_validity) VALUES (?, ?, ?)";

        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, ID_carte);
            prepStmt.setInt(2, ScannerID);
           // prepStmt.setDate(3, Date.valueOf(LocalDate.now()));
            prepStmt.setInt(3, validity);

            prepStmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
