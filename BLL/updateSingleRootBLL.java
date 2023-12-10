package BLL;

import java.util.List;

import DAL.updateSingleRootDAL;

public class updateSingleRootBLL {
    private updateSingleRootDAL rootDAL;

    public updateSingleRootBLL() {
        this.rootDAL = new updateSingleRootDAL();
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

    public List<String> getRootsForSelectedVerses(List<String> verses) {
        return rootDAL.getRootsForSelectedVerses(verses);
    }
    
    public List<String> getRootsForToken(String token) {
        return rootDAL.getRootsForToken(token);
    }


    public void addRootsToTokens(List<String> tokens, String root) {
        rootDAL.addRootsToTokens(tokens, root);
    }

    public void updateRoot(String oldRoot, String newRoot) {
        rootDAL.updateRoot(oldRoot, newRoot);
    }
}
