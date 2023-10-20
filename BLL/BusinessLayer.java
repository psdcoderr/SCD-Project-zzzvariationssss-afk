package BLL;

import java.util.List;

import DAL.DataLayerDB;

public class BusinessLayer {
    private final DataLayerDB DAO;
//(Moiz)
    public BusinessLayer(DataLayerDB DAO) {
        this.DAO = DAO;
    }

    public void addData(String btitle, String a, String yp) {
        DAO.addData(btitle, a, yp);
    }
}