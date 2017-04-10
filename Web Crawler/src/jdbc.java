import java.sql.*;

public class jdbc {
	
	public static void main(String[] args){
		Connection conn = null;
		
		try{
			Class.forName("org.postgresql.Driver");
			conn = DriverManager
		            .getConnection("jdbc:postgresql://localhost:5432/testdb",
		                    "zhanglizeyi", "123");
		}catch(Exception e){
			e.printStackTrace();
			System.err.println(e.getClass().getName()+": "+e.getMessage());
			System.exit(0);
		}
		System.out.println("Opened database successfully");
	}
}
