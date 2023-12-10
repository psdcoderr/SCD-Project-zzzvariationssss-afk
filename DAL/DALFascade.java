package DAL;

import java.util.List;

import DTO.BooksDTO;

public class DALFascade implements DBInterfaceFacade{

	@Override
	public void addData(String btitle, String a, String yp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean checkBook(String bname) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void updateBook(String btitle, String ubtitle, String a, String yp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<BooksDTO> showAllBooks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BooksDTO showSingleBook(String b_title) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteBook(String title) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<String> show_poems(String b_Name) {
		// TODO Auto-generated method stub
		return null;
	}

}
