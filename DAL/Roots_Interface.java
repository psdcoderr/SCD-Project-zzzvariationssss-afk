package DAL;

import java.util.List;
import java.util.Map;

public interface Roots_Interface {
    void addRoot(String root,int t_id);
    boolean checkRoot(String rName);
    void updateRoot(String root, String newRoot);
    void deleteRoot(String root);
    Map<Integer,String> showAllRoots();
    List<String> showRootData(String root);
    //Made the interface of roots 
  
}