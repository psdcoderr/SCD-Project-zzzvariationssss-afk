package DAL;

import java.util.List;

public interface Roots_Interface {
    void addRoot(String root);
    boolean checkRoot(String rName);
    void updateRoot(String root, String newRoot);
    void deleteRoot(String root);
    List<String> showAllRoots();
    //Made the interface of roots 
  
}

