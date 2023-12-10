package DAL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

	public class TokenDALStub extends TokenDAL {

	    private Map<String, Integer> verseIdMap;
	    private List<String> allVerses;
	    private boolean addNewTokenCalled;
	    private String addedVerse;
	    private String addedToken;

	    public TokenDALStub() {
	        this.verseIdMap = new HashMap<>();
	        this.allVerses = new ArrayList<>();
	        this.addNewTokenCalled = false;
	        this.addedVerse = null;
	        this.addedToken = null;

	        List<String> dummyVerses = new ArrayList<>(Arrays.asList("Verse1", "Verse2", "Verse3", "Verse4", "Verse5"));
	        setAllVerses(dummyVerses);

	    }

	    public void setAllVerses(List<String> allVerses) {
	        this.allVerses = allVerses;
	        updateVerseIdMap();
	    }

	    public boolean isAddNewTokenCalled() {
	        return addNewTokenCalled;
	    }

	    public String getAddedVerse() {
	        return addedVerse;
	    }

	    public String getAddedToken() {
	        return addedToken;
	    }

	    private void updateVerseIdMap() {
	        for (int i = 0; i < allVerses.size(); i++) {
	            verseIdMap.put(allVerses.get(i), i + 1); // Simulating database auto-increment
	        }
	    }

	    @Override
	    public List<String> getAllVerses() {
	        return allVerses;
	    }

	    @Override
	    public int getVerseId(String verse) {
	        return verseIdMap.getOrDefault(verse, -1);
	    }

	    @Override
	    public void addNewToken(String verse, String token) {
	        addNewTokenCalled = true;
	        addedVerse = verse;
	        addedToken = token;
	    }
	}


