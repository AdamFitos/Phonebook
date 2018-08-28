package phonebook;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ádám
 */
public class DB {

    final String URL = "jdbc:derby:sampleDB;create=true";
    final String USERNAME = "";
    final String PASSWORD = "";
    Connection conn = null;
    Statement createStatement = null;
    DatabaseMetaData dbmd = null;

    public DB() {
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("a híd létre jött");
        } catch (SQLException ex) {
            System.out.println("Valami baj van a drive-val");

            System.out.println("" + ex);
        }

        if (conn != null) {

            try {
                createStatement = conn.createStatement();
            } catch (SQLException ex) {
                System.out.println("Valami baj van a ");

                Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            dbmd = conn.getMetaData();
        } catch (SQLException ex) {
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ResultSet rss = dbmd.getTables(null, "APP", "CONTACTS", null);
            if (!rss.next()) {
                createStatement.execute("create table Contacts (id INT not null primary key GENERATED ALWAYS AS IDENTITY"
                        + "(START WITH 1, INCREMENT BY 1), firstname varchar(20) not null, lastname varchar(20), "
                        + "emailaddress varchar(30))");
            }
        } catch (SQLException ex) {
            System.out.println("Valami baj van a adatbázi lekérdezésével");
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public ArrayList<Person> showAllContacts() {

        String sql = "select * from Contacts";
        ArrayList<Person> users = null;

        try {

            ResultSet rs = createStatement.executeQuery(sql);
            users = new ArrayList<>();
            
            while (rs.next()) {
                Person actualperson = new Person(rs.getInt("id"),rs.getString("firstname"),rs.getString("lastname"),
                        rs.getString("emailaddress"));
                users.add(actualperson);
  
            }
        } catch (SQLException ex) {
            System.out.println("Baj van a nevek lekérdezése közben.");
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
return users;
        
    }
    
    
    public void addContacts(Person person ){
        try {
//            String sql="insert into users value ('" +  name + "'),('" + address + "')";
//            createStatement.execute(sql);

              String sql="insert into Contacts (firstname, lastname, emailaddress) values (?,?,?)" ;
              PreparedStatement pstm = conn.prepareStatement(sql);
              pstm.setString(1, person.getFirstName());
              pstm.setString(2, person.getLastName());
              pstm.setString(3, person.getEmailAddress());
              pstm.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact hozzáadásávál!");
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }

        }


public void updateContacts(Person person ){
        try {
//            String sql="insert into users value ('" +  name + "'),('" + address + "')";
//            createStatement.execute(sql);
              String sql="update Contacts set firstname = ?, lastname = ?, emailaddress = ? where id = ?" ;
              PreparedStatement pstm = conn.prepareStatement(sql);
              pstm.setString(1, person.getFirstName());
              pstm.setString(2, person.getLastName());
              pstm.setString(3, person.getEmailAddress());
              pstm.setInt(4, Integer.parseInt(person.getID()));
              pstm.execute();
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact update-elésével hozzáadásávál!");
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
}
public void removeContact(Person person ){
        try {
//            
              String sql="delete  from  Contacts where id = ?" ;
              PreparedStatement pstm = conn.prepareStatement(sql);
              pstm.setInt(1, Integer.parseInt(person.getID()));
              pstm.execute();
              System.out.println("delete");
        } catch (SQLException ex) {
            System.out.println("Valami baj van a contact eltávolításával");
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }}




  public ArrayList<Person> searching(String name) {

        String sql = "select * from Contacts where firstname like '%"+name+"%'"
                + "or lastname like '%"+name+"%'"
                + "or emailaddress like '%"+name+"%'";
        ArrayList<Person> users = null;

        try {

            ResultSet rs = createStatement.executeQuery(sql);
            users = new ArrayList<>();
            System.out.println("result set");
            
            while (rs.next()) {
                Person actualperson = new Person(rs.getInt("id"),rs.getString("firstname"),rs.getString("lastname"),
                        rs.getString("emailaddress"));
                users.add(actualperson);
                System.out.println(users);
                
  
            }
        } catch (SQLException ex) {
            System.out.println("Baj van a nevek lekérdezése közben.");
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
        }
      
         
          return users;
  }
          
//     public ArrayList<Person> shoAllContacts() {
//
//        String sql = "select * from Contacts";
//        ArrayList<Person> users = null;
//
//        try {
//
//            ResultSet rs = createStatement.executeQuery(sql);
//            users = new ArrayList<>();
//            
//            while (rs.next()) {
//                Person actualperson = new Person(rs.getInt("id"),rs.getString("firstname"),rs.getString("lastname"),
//                        rs.getString("emailaddress"));
//                users.add(actualperson);
//  
//            }
//        } catch (SQLException ex) {
//            System.out.println("Baj van a nevek lekérdezése közben.");
//            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//return 
//        users.toArray();
//    }
        
//  
}


