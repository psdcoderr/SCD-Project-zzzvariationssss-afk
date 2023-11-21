package BLL;

import java.util.List;

import DTO.BooksDTO;

public interface BusinessLayerInterface {
	public void addData(String btitle, String a, String yp);
    public boolean checkBook(String bname);
    public void updateBook(String btitle, String ubtitle, String a, String yp);
    public void delBook(String title);
    public List<BooksDTO> ShowAllBooks();
    public BooksDTO showSingleBook(String b_title);
    public List<String> show_poems(String b_Name);
}
