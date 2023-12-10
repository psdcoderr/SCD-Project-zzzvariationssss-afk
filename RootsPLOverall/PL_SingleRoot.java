package RootsPLOverall;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import BLL.RootsBO;

public class PL_SingleRoot extends JFrame {
	private final JTextField rootTextField;
	private final JButton searchButton;
	private final JTable versesTable;

	private final RootsBO rootsBO;

	public PL_SingleRoot(RootsBO rootsBO) {
		super("Root Search App");
		this.rootsBO = rootsBO;
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 300);
		setLayout(new BorderLayout());

		rootTextField = new JTextField(20);
		searchButton = new JButton("Search Root");
		versesTable = new JTable();
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout());
		topPanel.add(new JLabel("Search Root:"));
		topPanel.add(rootTextField);
		topPanel.add(searchButton);

		add(topPanel, BorderLayout.NORTH);
		add(new JScrollPane(versesTable), BorderLayout.CENTER);

		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String root = rootTextField.getText();
				List<String> versesList = showRootDataa(root);
				updateTable(versesList);
			}
		});
	}

	public List<String> showRootDataa(String root) {
		return rootsBO.showRootDataa(root);
	}

	private void updateTable(List<String> versesList) {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Verses");

		for (String verse : versesList) {
			model.addRow(new Object[] { verse });
		}

		versesTable.setModel(model);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				RootsBO rootsBO = new RootsBO(new DAL.DataLayerRoots());
				new PL_SingleRoot(rootsBO).setVisible(true);
			}
		});
	}
}
