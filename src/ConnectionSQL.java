import java.sql.*;
import java.time.LocalDate;


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


    public  String generateCode_update(String matricule, int validity){

        String udpdate_code = matricule+new Timestamp(System.currentTimeMillis()).toString()+getvalid(matricule);
        updateCodeCurr(matricule, udpdate_code);
        return udpdate_code;

    }
    public  int getvalid(String matricule){
        String sql="SELECT validity FROM personnel WHERE  ID_pers = ?";

        PreparedStatement prepStmt = null;




        try {
            prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, matricule);

            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                 return rs.getInt("validity");
            }


        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return -1;
    }


    public int get_last_content(String cont){
        char  p =cont.charAt(cont.length()-1);
        return Integer.valueOf(p);
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
    public void updateCodeCurr(String matricule, String s){
        String rqt="UPDATE personnel SET code_curr=  ? where ID_carte = ?";
        PreparedStatement prepStmt = null;
        try {
            prepStmt = conn.prepareStatement(rqt);
            prepStmt.setString(1,s );
            prepStmt.setString(2,matricule);

            prepStmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public String insertHCEfirsttime(String matricule,String code){
        String sql = "SELECT ID_pers,validity FROM personnel WHERE code_andro_app = ? && ID_Pers = ?";





        try {

            PreparedStatement prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, code);
            prepStmt.setString(2, matricule);

            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()) {
                String time = String.valueOf(LocalDate.now());
                String code_sur_tel = rs.getString("ID_pers")+""+time+rs.getString("validity");

               return code_sur_tel;

            }

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    return "false profil";}


    public boolean searchProfil( String content) {
        String sql="SELECT * FROM personnel where code_curr=?";
        try {

            PreparedStatement prepStmt = conn.prepareStatement(sql);
            prepStmt.setString(1, content);

            ResultSet rs = prepStmt.executeQuery();
            while (rs.next()){
                return true;
            }

        } catch (SQLException e1) {
            e1.printStackTrace();

        }
        finally {
            return false;
        }

    }
}
