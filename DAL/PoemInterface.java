package DAL;

public interface PoemInterface {

	public void addData(String btitle, String a, String yp);

	public int insertPoem(String title, int bookId);

	public void insertVerse(String text, int poemId);

	public int CheckBookByNameAndAuthor(String title, String author);

	public int bookcheckk();

	public void ParsePoems(String fileName);

}
