package BLL;

import java.util.List;

import DAL.Roots_Interface;

public class Roots_BO {
	Roots_Interface DAO;
	
	public Roots_BO(Roots_Interface DAO)
	{
		this.DAO=DAO;
	}
	public void addRoot(String root) {
        DAO.addRoot(root);
    }

    public boolean checkRoot(String rName) {
        return DAO.checkRoot(rName);
    }

    public void updateRoot(String root, String newRoot) {
        DAO.updateRoot(root, newRoot);
    }

    public void deleteRoot(String root) {
        DAO.deleteRoot(root);
    }

    public List<String> showAllRoots() {
        return DAO.showAllRoots();
    }
    //This is business layer for Roots 
}
