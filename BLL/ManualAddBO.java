package BLL;
import DAL.ManualAddInterface;

public class ManualAddBO {
    ManualAddInterface DAO;

    public ManualAddBO(ManualAddInterface DAO) {
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
