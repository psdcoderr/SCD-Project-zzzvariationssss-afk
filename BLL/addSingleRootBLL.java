package BLL;

import DAL.addSingleRootDAL;

import java.util.List;

public class addSingleRootBLL {
    private addSingleRootDAL rootDAL;

    public void setRootDAL(addSingleRootDAL rootDAL) {
        this.rootDAL = rootDAL;
    }

    public List<String> getSelectedSuggestedRoots(int[] selectedRows) {
        return rootDAL.getSelectedSuggestedRoots(selectedRows);
    }

    public List<String> getSuggestedRoots(String verse) {
        return rootDAL.suggestedRoots(verse);
    }

    public addSingleRootBLL() {
        this.rootDAL = new addSingleRootDAL();
    }

    public List<String> getAllVerses() {
        return rootDAL.getAllVerses();
    }

    public List<String> getTokensForVerses(List<String> verses) {
        return rootDAL.getTokensForVerses(verses);
    }

    public List<String> getSelectedVerses(int[] selectedRows) {
        return rootDAL.getSelectedVerses(selectedRows);
    }

    public List<String> getSelectedTokens(int[] selectedRows) {
        return rootDAL.getSelectedTokens(selectedRows);
    }

    public void addRootsToTokens(List<String> tokens, String root) {
        rootDAL.addRootsToTokens(tokens, root);
    }
}
