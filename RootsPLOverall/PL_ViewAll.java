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

        // Set up the JFrame
       // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Create components
        rootsTable = new JTable();

        // Add components to the JFrame
        add(new JScrollPane(rootsTable), BorderLayout.CENTER);

        // Load and display roots data
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
                // Create an instance of Roots_BO and pass it to PL_ViewAll
                RootsBO rootsBO = new RootsBO(new DAL.DataLayerRoots());
                new PL_ViewAll(rootsBO).setVisible(true);
            }
        });
    }
}
