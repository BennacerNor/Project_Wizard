package Application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 
public class connect{


	
	public Connection connect() {
        // SQLite connection string
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
        String url = "jdbc:sqlite:/C:\\Users\\BIGmatik\\Downloads\\testr.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
          
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	  public void selectt(int i) throws SQLException {
		  
		  String sql = 
				 " SELECT products.ProductID, Products.ProductName, Bons.BonID , Bons.Suppliername FROM products JOIN Bon_produit on (products.ProductID=Bon_produit.ProductID  )"
				 + "JOIN Bons on (Bons.BonID=Bon_produit.BonID) WHERE Bons.BonID= "+i;// i= BonID
				 
			Connection conn = connect();
			Statement statement = conn.createStatement();
			ResultSet result = statement.executeQuery(sql);
			try {
				
				while(result.next()) {
					int BonnID  = result.getInt("BonID");
					String ProductName = result.getString("ProductName");
					 int ProductID = result.getInt("ProductID");
					
					System.out.println(BonnID+ " | "+ProductName+" | " +ProductID);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }

   
    public void insert(int BonID ,int ProductID) {
        String sql = "INSERT INTO Bon_produit(ProductID,BonID) VALUES(?,?)";

        try (
        		Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setInt(1, ProductID); 
            pstmt.setInt(2, BonID);
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	    }
    public void delete(double ProductPrice) {
        String sql = "DELETE FROM products WHERE ProductId = ?";
       
        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setDouble(1, ProductPrice);
          
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) throws SQLException {
   
         connect app = new connect();
        // insert three new rows
       app.insert(1, 2);
       app.insert(1, 1);
       app.insert(2, 1);
        app.selectt(2);//i=2 bonID 
    }
}

