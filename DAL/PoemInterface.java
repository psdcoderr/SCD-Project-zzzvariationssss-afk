package DAL;

import java.util.List;

import DTO.BooksDTO;

public interface PoemInterface {

	public void addData(String btitle, String a, String yp);

	public int insertPoem(String title, int bookId);

	public void insertVerse(String text, int poemId);

	public int CheckBookk(String title, String author);

	public List<String> ParsePoems(String fileName);

	public List<BooksDTO> showAllBooks();

	public int CheckBookByNameAndAuthor(String title, String author);

	public void updateBook(String btitle, String ubtitle, String a, String yp);

	public void updatePoem(String oldTitle, String newTitle, String newAuthor, String newYearPassed);

	public void deletePoem(String title);

	public List<String> viewAllPoems();

	public List<String> viewSinglePoem(String title);

	public List<String> insertPoems(List<String> poems, String bookTitle, String author, String yearPassed);

	int bookcheckk();

	List<String> getAllVerses();

}