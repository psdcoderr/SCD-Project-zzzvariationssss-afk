//updated
//Function names enough to not use comments
package BLL;
import java.util.List;
import DAL.DBInterfaceFacade;
import DTO.BooksDTO;

public class BusinessLayer implements BusinessLayerInterface{
    private final DBInterfaceFacade DAO;
    
    public BusinessLayer(DBInterfaceFacade DAO) {
        this.DAO = DAO;
    }
    
    @Override
    public void addData(String bookTitle, String author, String yearPassed) {
        DAO.addData(bookTitle, author, yearPassed);
    }
    
    @Override
    public boolean checkBook(String bookname) {
        return DAO.checkBook(bookname);
    }
    
    @Override
    public void updateBook(String bookTitle, String updatedBookTitle, String author, String yearPassed) {
        DAO.updateBook(bookTitle, updatedBookTitle, author, yearPassed);
    }
    
    @Override
    public void deleteBook(String title) {
        DAO.deleteBook(title);
    }
    
    @Override
    public List<BooksDTO> showAllBooks() {
        return DAO.showAllBooks();
    }
    
    @Override    
    public BooksDTO showSingleBook(String bookTitle) {
        return DAO.showSingleBook(bookTitle);
    }
    @Override
    public List<String> show_poems(String bookName) {
        return DAO.show_poems(bookName);
    }
}