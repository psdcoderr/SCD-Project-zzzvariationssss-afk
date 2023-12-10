package RootsPLOverall;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import BLL.RootsBO;

public class PL_ViewAll extends JFrame {
    private final JTable rootsTable;
    private final RootsBO rootsBO;

    public PL_ViewAll(RootsBO rootsBO) {
        super("View All Roots");
        this.rootsBO = rootsBO;

       // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        rootsTable = new JTable();
        add(new JScrollPane(rootsTable), BorderLayout.CENTER);

        loadRootsData();
    }

    private void loadRootsData() {
        Map<Integer, String> allRootsData = rootsBO.showAllRoots();

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Root");
        model.addColumn("Verse Count");

        for (Map.Entry<Integer, String> entry : allRootsData.entrySet()) {
            String root = entry.getValue();
            int verseCount = entry.getKey();
            model.addRow(new Object[]{root, verseCount});
        }

        rootsTable.setModel(model);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                RootsBO rootsBO = new RootsBO(new DAL.DataLayerRoots());
                new PL_ViewAll(rootsBO).setVisible(true);
            }
        });
    }
}
