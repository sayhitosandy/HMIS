
/*
 * Author:
 * 	Sanidhya Singal 2015085
 * 	Saurabh Kapur 2015087
 */

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MedicalHistory {

	public MedicalHistory() {
		MyFrame f = new MyFrame();

		Font myFont = new Font("Cambria", Font.PLAIN, 16);
		JLabel topText = new JLabel(
				"<html><br><br>You are a Doctor. You want to know whether a particular Patient has a Medical History, <br>i.e., whether he/she is a New Patient or an Old Patient?</html>");
		topText.setFont(myFont);

		JLabel name = new JLabel("<html><br>Enter Patient's Name: </html>");
		name.setFont(myFont);

		JTextField patientHistoryF = new JTextField();
		patientHistoryF.setFont(myFont);

		MyButton search = new MyButton("Search");
		search.setFont(new Font("Agency FB", Font.BOLD, 16));
		search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String patientName = patientHistoryF.getText();
				if (patientName.isEmpty() == true) {
					JOptionPane.showMessageDialog(f, "Kindly Enter some Value");
				} else {
					Statement stmt = null;
					try {
						stmt = View.con.createStatement();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String Select_stmt = "select patientInfo.Name, count(receptionist.PatientID) as NoofVisits ";
					String From_stmt = " from receptionist, patientInfo ";
					String Where_stmt = " where receptionist.PatientID = patientInfo.PatientID and patientInfo.Name = \""
							+ patientName + "\"" + " group by receptionist.PatientID;";
					try {
						ResultSet rs = stmt.executeQuery(Select_stmt + From_stmt + Where_stmt);
						ResultSetMetaData rsmd = rs.getMetaData();

						rs.next();
						if (Integer.parseInt(rs.getString(2)) > 1) {
							JOptionPane.showMessageDialog(f, "Old Patient (No. of Visits: " + rs.getString(2) + ")");
						} else {
							JOptionPane.showMessageDialog(f, "New Patient");
						}
					} catch (SQLException e2) {
						e2.printStackTrace();
					}
				}
			}
		});

		JPanel panel = new JPanel();
		panel.setBackground(MyFrame.rightPanel.getBackground());
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 10, 0, 0); // top, left, bottom, right
		gbc.gridwidth = 3;
		panel.add(topText, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.SOUTH;
		panel.add(name, gbc);

		gbc.gridx = 1;
		gbc.gridwidth = 2;
		panel.add(patientHistoryF, gbc);

		gbc.gridy++;
		gbc.gridx = 3;
		gbc.gridwidth = 1;
		panel.add(search, gbc);

		MyFrame.rightPanel.add(panel, BorderLayout.NORTH);

	}
}
