package BLL;

import DAL.EditTokenDAL;
import DAL.EditTokenDALStub;

import java.util.List;

public class EditTokenBLL {

    private EditTokenDAL editTokenDal;

    public EditTokenBLL() {
        this.editTokenDal = new EditTokenDAL();
    }

    public List<String> getAllTokensInVerse(String verse) {
        return editTokenDal.showAllTokensInVerse(verse);
    }

    public boolean updateToken(String verse,String oldToken, String newToken) {
        try {

            editTokenDal.updateToken(verse,oldToken, newToken);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void updateVerse(String verse, String oldToken, String newToken)
    {
    	editTokenDal.updateVerseWithNewToken(verse,oldToken,newToken);
    }

    public void setEditTokenDal(EditTokenDALStub editTokenDALStub) {
        this.editTokenDal = editTokenDALStub;
    }

	
}
