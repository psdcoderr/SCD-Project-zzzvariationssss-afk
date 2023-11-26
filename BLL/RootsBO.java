package BLL;

import java.util.List;
import java.util.Map;

import DAL.Roots_Interface;

public class RootsBO {
	Roots_Interface DAO;
	
	public RootsBO(Roots_Interface DAO)
	{
		this.DAO=DAO;
	}
	public void addRoot(String root) {
        DAO.addRoot(root, 0);
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

    public Map<Integer,String> showAllRoots() {
        return DAO.showAllRoots();
    }
    
    
    
    public List<String> showRootDataa(String Root)
    {
    	return DAO.showRootData(Root);
    }
    
    
}
