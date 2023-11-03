package BLL;
import DAL.Import_Interface;

public class Import_BO {
    Import_Interface DAO;

    public Import_BO(Import_Interface DAO) {
        this.DAO = DAO;
    }

    public void importData(String btitle, String author, String yp, String poemTitle, String[] verses) {
        int bookId = DAO.bookcheckk(btitle, author, yp);
        int poemId = DAO.insertPoem(poemTitle, bookId);

        for (String verse : verses) {
            DAO.insertVerse(verse, poemId);
        }
    }
}
