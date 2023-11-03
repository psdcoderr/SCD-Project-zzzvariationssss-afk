package PL;

import BLL.Import_BO;
import DAL.DataLayerImport;
import DAL.Import_Interface;

public class Import_PL {
    public static void main(String[] args) {
        String filename ="C:\\Users\\MOSHIN\\Downloads\\21f_9454\\src\\PL\\Poem.txt";

        DataLayerImport DAO = new DataLayerImport();
        Import_BO obj = new Import_BO(DAO);
        obj.addData(filename);
    }
}
//pl layer
//The provided code appears to be a Java program for importing data from a text file (Poem.txt)