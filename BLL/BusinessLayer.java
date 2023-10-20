package BLL;

import DAL.DBInterfaceFacade;

public class BusinessLayer {
    private final DBInterfaceFacade DAO;
//(Moiz)
    public BusinessLayer(DBInterfaceFacade DAO) {
        this.DAO = DAO;
    }
//(Checking other commit)
    public void addData(String btitle, String a, String yp) {
        DAO.addData(btitle, a, yp);
    }
    //check book to see if it exists
    //before updation.
    public boolean checkBook(String bname) {
        return DAO.checkBook(bname);
    }
    public void updateBook(String btitle, String ubtitle, String a, String yp) {
        DAO.updateBook(btitle, ubtitle, a, yp);
    }
    public void delBook(String title) {
        DAO.deleteBook(title);
    }
}