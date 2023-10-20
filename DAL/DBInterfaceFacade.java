package DAL;

import java.util.List;

public interface DBInterfaceFacade {
    void addData(String btitle, String a, String yp);
    boolean checkBook(String bname);
    void updateBook(String btitle, String ubtitle, String a, String yp);
}