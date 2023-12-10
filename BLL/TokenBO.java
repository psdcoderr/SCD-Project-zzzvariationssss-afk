package BLL;

import DAL.TokenDAL;


import java.util.List;

public class TokenBO {
    private TokenDAL tokenDAL;

    public TokenBO() {
        this.tokenDAL = new TokenDAL();
    }

    public List<String> getAllVerses() {
        return tokenDAL.getAllVerses();
    }

    public void addNewToken(String verse, String token) {
        tokenDAL.addNewToken(verse, token);
    }


}
 