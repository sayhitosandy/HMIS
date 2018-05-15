
/*
 * Author:
 * 	Sanidhya Singal 2015085
 * 	Saurabh Kapur 2015087
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Table {
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	DefaultTableModel model = new DefaultTableModel();
	GridBagConstraints gbc2 = new GridBagConstraints();
	int count = 0, size = 0, clicks = 0;

	public Table(String[] columns, String[][] rows) {
		JFrame frame = new JFrame();
		frame.setTitle("Query Result");
		frame.setSize(screenSize.width - 342, screenSize.height - 168);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(screenSize.width / 10, screenSize.height / 10);
		frame.setResizable(false);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.getContentPane().setBackground(Color.white);

		Font myFont = new Font("Agency FB", Font.PLAIN, 48);
		Font myFont2 = new Font("Agency FB", Font.PLAIN, 24);

		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		JTable table = new JTable(model);
		JButton next = new MyButton("Next");

		table.setPreferredScrollableViewportSize(new Dimension(900, 450));
		table.setFillsViewportHeight(true);

		JScrollPane scrollPane = new JScrollPane(table);
		gbc2.gridx = 0;
		gbc2.gridy = 0;
		gbc2.fill = GridBagConstraints.BOTH;
		panel.add(scrollPane, gbc2);

		gbc2.gridy++;
		gbc2.fill = GridBagConstraints.HORIZONTAL;
		gbc2.anchor = GridBagConstraints.CENTER;
		gbc2.insets = new Insets(0, 800, 0, 0);
		next.setEnabled(false);
		panel.add(next, gbc2);

		count = rows.length;

		if (count == 0) {
			JOptionPane.showMessageDialog(frame, "No Result Found");
			frame.dispose();
			return;
		}

		for (int i = 0; i < columns.length; i++) {
			model.addColumn(columns[i]);
		}

		size = (count > 20) ? 20 : count;
		for (int i = 0; i < size; i++) {
			model.insertRow(i, rows[i]);
		}

		if (count > 20) {
			next.setEnabled(true);
		}

		next.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clicks++;
				for (int i = (size - ((clicks - 1) * 20)) - 1; i >= 0; i--) {
					model.removeRow(i);
				}
				size = ((size + 20) > count) ? count : (size + 20);
				for (int i = 0; i < (size - (clicks * 20)); i++) {
					model.insertRow(i, rows[i + (clicks * 20)]);
				}
				if (size == count) {
					next.setEnabled(false);
				}
			}
		});

		if (size == count) {
			next.setEnabled(false);
		}

		frame.add(panel);
		frame.setVisible(true);

	}
}
