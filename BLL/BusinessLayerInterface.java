package BLL;

import java.util.List;

import DTO.BooksDTO;

public interface BusinessLayerInterface {
    public void addData(String bookTitle, String author, String yearPassed);
    public boolean checkBook(String bookname);
    public void updateBook(String bookTitle, String updatedBookTitle, String author, String yearPassed);
    public void deleteBook(String title);
    public List<BooksDTO> showAllBooks();
    public BooksDTO showSingleBook(String bookTitle);
    public List<String> show_poems(String bookName);
}
