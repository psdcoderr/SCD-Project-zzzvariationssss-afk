package BLL;

import java.util.List;

import DAL.DBInterfaceFacade;

import DTO.BooksDTO;

public class BusinessLayer implements BusinessLayerInterface{
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
    public List<BooksDTO> ShowAllBooks() {
        return DAO.showAllBooks();
    }
    
    public BooksDTO showSingleBook(String b_title) {
        return DAO.showSingleBook(b_title);
    }
    @Override
    public List<String> show_poems(String bookName) {
        
        return DAO.show_poems(bookName);

        
    }
}