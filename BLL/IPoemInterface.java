package BLL;



import DTO.BooksDTO;

import java.util.List;

public interface IPoemInterface {

    List<BooksDTO> getAllBooks();

    void updateBook(String btitle, String ubtitle, String a, String yp);

    List<String> addData(String filename, String bookTitle, String author, String yearPassed);

    void updatePoem(String oldTitle, String newTitle, String newAuthor, String newYearPassed);

    void deletePoem(String title);

    List<String> ParsePoems(String fileName, String bookTitle, String author);

    List<String> viewAllPoems();

    List<String> viewSinglePoem(String title);

    List<String> getAllVerses();

    List<BooksDTO> loadBooks();
}
