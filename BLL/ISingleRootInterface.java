package BLL;

import java.util.List;

public interface ISingleRootInterface {

    List<String> getSelectedSuggestedRoots(int[] selectedRows);

    List<String> getSuggestedRoots(String verse);

    List<String> getAllVerses();

    List<String> getTokensForVerses(List<String> verses);

    List<String> getSelectedVerses(int[] selectedRows);

    List<String> getSelectedTokens(int[] selectedRows);

    void addRootsToTokens(List<String> tokens, String root);
}
