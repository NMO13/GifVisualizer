package gui;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTable;

public class InfoDialog extends JDialog{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JFrame frame;

	public InfoDialog(JFrame frame, String title, String[][] data) {
		super(frame, title, true);
		this.frame = frame;
		Object[] columnNames = {"Property",
        "Value"};
		
		JTable table;
		if(data == null) {
			table = new JTable();
		}
		else
			table = new JTable(data, columnNames);
			
		this.add(table);
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
}
