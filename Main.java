import java.util.Scanner;

import BLL.BusinessLayer;
import DAL.DataLayerDB;
import PL.PresentationLayer;
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static PresentationLayer pl;
	//objects created for strict dependency injection.
    DataLayerDB DAO = new DataLayerDB();
	BusinessLayer BO=new BusinessLayer(DAO);
   
	
	public static void main(String[] args) {

    	pl = new PresentationLayer(new BusinessLayer(new DataLayerDB()));
        
    }
}
