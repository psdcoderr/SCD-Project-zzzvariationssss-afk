package BLL;

import DAL.PoemInterface;
import DTO.BooksDTO;

import java.util.List;

public class PoemBO {
	PoemInterface DAO;

	public PoemBO(PoemInterface DAO) {
		this.DAO = DAO;
	}

	public List<BooksDTO> getAllBooks() {
		return DAO.showAllBooks();
	} 

	public void updateBook(String btitle, String ubtitle, String a, String yp) {
		DAO.updateBook(btitle, ubtitle, a, yp);
	}

	public List<String> addData(String filename, String bookTitle, String author, String yearPassed) {
		List<String> poems = DAO.ParsePoems(filename);
		List<String> verseList = DAO.insertPoemsManually(poems, bookTitle, author, yearPassed);
		return verseList;
	}

	public void updatePoem(String oldTitle, String newTitle, String newAuthor, String newYearPassed) {
		DAO.updatePoem(oldTitle, newTitle, newAuthor, newYearPassed);
	}

	public void deletePoem(String title) {
		DAO.deletePoem(title);
	}

	public List<String> ParsePoems(String fileName, String bookTitle, String author)
	{
		List<String> parsePoems = DAO.ParsePoems(fileName);
		return parsePoems;
		
	}
	public List<String> viewAllPoems() {
		return DAO.viewAllPoems();
	}

	public List<String> viewSinglePoem(String title) {
		return DAO.viewSinglePoem(title);
	}
	public List<String> getAllVerses() {
	    return DAO.getAllVerses();
	}

	public List<BooksDTO> loadBooks() {
		return DAO.showAllBooks();
	}

}