package DAL;

import java.util.HashMap;
import java.util.Map;

public class ManualAddDataLayerDAOStub implements ManualAddInterface {

    private Map<String, Integer> bookIdMap = new HashMap<>();
    private Map<String, Integer> poemIdMap = new HashMap<>();
    private Map<Integer, String> versesMap = new HashMap<>();
    
    // Override methods to provide stubbed behavior

    @Override
    public void addData(String btitle, String a, String yp) {
        // Implement stubbed behavior
    }

    @Override
    public int CheckBookByNameAndAuthor(String title, String author) {
        // Implement stubbed behavior
        return bookIdMap.getOrDefault(title + author, -1);
    }

    @Override
    public int bookcheckk(String btitle, String author, String yp) {
        // Implement stubbed behavior
        return CheckBookByNameAndAuthor(btitle, author);
    }

    @Override
    public int insertPoem(String title, int bookId) {
        // Implement stubbed behavior
        return poemIdMap.getOrDefault(title + bookId, -1);
    }

    @Override
    public void insertVerse(String text, int poemId) {
        // Implement stubbed behavior
        versesMap.put(poemId, text);
    }
    public Map<String, Integer> getBookIdMap() {
        return bookIdMap;
    }

    public Map<String, Integer> getPoemIdMap() {
        return poemIdMap;
    }

    public Map<Integer, String> getVersesMap() {
        return versesMap;
    }
}

