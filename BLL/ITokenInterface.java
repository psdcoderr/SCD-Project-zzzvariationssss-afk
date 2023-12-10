package BLL;


import java.util.List;

public interface ITokenInterface {

    List<String> getAllVerses();

    void addNewToken(String verse, String token);
}
