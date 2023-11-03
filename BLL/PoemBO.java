package BLL;

import DAL.DataLayerPoemDB;
import DAL.PoemInterface;

public class PoemBO {
	PoemInterface DAO;
	public PoemBO(PoemInterface DAO)
	{
		this.DAO=DAO;	
	}
	public void addData(String filename)
	{
		DAO.ParsePoems(filename);
	}
}
