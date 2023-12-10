package BLL;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import BLL.ManualAddBO;
import DAL.ManualAddInterface;
import DAL.ManualAddDataLayerDAOStub;

class ManualAddBOTest {

    private ManualAddBO manualAddBO;
    private ManualAddInterface daoStub;

    @BeforeEach
    void setUp() {
        daoStub = new ManualAddDataLayerDAOStub();
        manualAddBO = new ManualAddBO(daoStub);
    }

    @Test
    void testImportData() {
        String btitle = "TestBook";
        String author = "TestAuthor";
        String yp = "2022";
        String poemTitle = "TestPoem";
        String[] verses = {"Verse1", "Verse2"};

        manualAddBO.importData(btitle, author, yp, poemTitle, verses);
        int bookId = ((ManualAddDataLayerDAOStub) daoStub).getBookIdMap().get(btitle + author);
        int poemId = ((ManualAddDataLayerDAOStub) daoStub).getPoemIdMap().get(poemTitle + bookId);
        assertEquals(2, ((ManualAddDataLayerDAOStub) daoStub).getVersesMap().size());
        assertTrue(((ManualAddDataLayerDAOStub) daoStub).getVersesMap().containsKey(poemId));
    }
}
