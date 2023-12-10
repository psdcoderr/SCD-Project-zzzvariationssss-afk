package DAL;

import java.util.List;

import DTO.BooksDTO;

public interface DBInterfaceFacade {
    void addData(String btitle, String a, String yp);
    boolean checkBook(String bname);
    void updateBook(String btitle, String ubtitle, String a, String yp);
    List<BooksDTO> showAllBooks();
    BooksDTO showSingleBook(String b_title);
	void deleteBook(String title);
    public List<String> show_poems(String b_Name);
}