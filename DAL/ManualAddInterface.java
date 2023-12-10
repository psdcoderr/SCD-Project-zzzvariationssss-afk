package DAL;
public interface ManualAddInterface {

    //done
	public void addData(String btitle, String a, String yp);

	//done
	public int insertPoem(String title, int bookId);

    //done
	public void insertVerse(String text, int poemId);

    //done
	public int CheckBookByNameAndAuthor(String title, String author);

    //done
	public int bookcheckk(String btitle, String author, String yp);


}
