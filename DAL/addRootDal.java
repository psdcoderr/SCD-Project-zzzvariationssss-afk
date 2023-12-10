package DAL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DB.DbConnection;

public class addRootDal {

	public void addrootfortoken(int t_id,String root)
	{
		String suggested = ManualSuggestRoot(root);
		String x = root;
		
		if(root!=null)
		{
			addNewRootInDB(t_id,x);
		}else {
			addNewRootInDB(t_id,suggested);			
		}
			
	}

	public void addNewRootInDB(int t_id, String root) {
		try (Connection connection = DbConnection.getConnection()) {
	        String insertQuery = "INSERT INTO Roots (t_id, root) VALUES (?, ?)";
	        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
	            preparedStatement.setInt(1, t_id);
	            preparedStatement.setString(2, root);
	            preparedStatement.executeUpdate();
	            System.out.println("Root added successfully!");
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	public String selectroot(String root)
	{
		String roott=null;
		
		return roott;
	}
	public String ManualSuggestRoot(String words) {
	
			String root = net.oujda_nlp_team.AlKhalil2Analyzer.getInstance().processToken(words).getAllRootString();
			root = root.replaceAll("[-:]", "").trim();
		
		return root;
	}
}
