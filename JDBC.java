import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBC {

    public static void main(String[] args) {

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        PreparedStatement pst = null;

        String url = "jdbc:postgresql://localhost/testdb";
        String user = "zhanglizeyi";
        String password = "123";

        try {
        	/*
			 * DriverManager.getConnection("URL/Address","USER_NAME", "PASSWORD"):
			 * 
			 * Each driver has a different sysntax for the URL,
			 *	In this case, a host, a port and a database name
			 *
			 * throw: SQLException
        	 */
            con = DriverManager.getConnection(url, user, password);
            //String stm = "INSERT INTO db VALUES('Z', '123456')";
            //start to insert 100 times
            for(int i=0; i<100; i++){
                char c = (char)i;
                st = con.createStatement();
            	String stmNew = "INSERT INTO db (name, phone) VALUES ("+i+", '12345')";
                st.executeUpdate(stmNew);
                // try{
                //     pst.executeUpdate(stmNew);
                // }catch(Exception x){
                //     System.out.println("x message: " + x.getMessage());
                // }
            }
            // pst.executeUpdate();
            /*
			 * createStatement():
			 *
			 * method of "connection object" creates a statement obj 
			 * 		for sending the SQL statement to the database 
             */
            // st = con.createStatement();
            /*
			 * executeQuery():
			 *
			 * executes the given SQL statment
			 * @return: a single ResultSet object, a specific SQL statement	
             */
            rs = st.executeQuery("SELECT * FROM db");

            //while loop will read if table has next values
            while (rs.next()) {
                System.out.println(rs.getString(1)+" "+rs.getString(2));
            }
        //Inside the finallly block, might get nullpointerexception
        //which would terminate the application and might leave the resources
        //not clean up
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(JDBC.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {

            try {
            	if(pst != null) {
            		pst.close();
            	}
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }
            //log the error message 
            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(JDBC.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }
    }
}