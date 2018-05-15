
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

public class PatientEnquiry {
	public PatientEnquiry() {
		MyFrame f = new MyFrame();

		Font myFont = new Font("Cambria", Font.PLAIN, 16);
		JLabel topText = new JLabel(
				"<html><br><br>You are at the reception. Being some patient's attendee, you want to enquire about the patient. <br>Fill out the following:</html>");
		topText.setFont(myFont);

		JLabel name = new JLabel("<html><br>Enter Patient's Name: </html>");
		name.setFont(myFont);

		JTextField patientNameF = new JTextField();
		patientNameF.setFont(myFont);

		JLabel DV = new JLabel("<html><br>Enter Patient's Date of Visit: </html>");
		DV.setFont(myFont);

		JTextField DVF = new JTextField();
		DVF.setFont(myFont);

		MyButton search = new MyButton("Search");
		search.setFont(new Font("Agency FB", Font.BOLD, 16));
		search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String patientName = patientNameF.getText();
				String patientDoV = DVF.getText();
				Statement stmt = null;
				if ((patientName.isEmpty() == true && patientDoV.isEmpty() == true)
						|| (patientName.isEmpty() == true)) {
					JOptionPane.showMessageDialog(f, "Kindly Enter some Value");
				} else {
					try {
						stmt = View.con.createStatement();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						String Select_stmt = "select patientMedical.PatientID, patientMedical.DoctorID, Doctor.Name, Doctor.Speciality, patientMedical.Indoor_Outdoor, patientMedical.Diagnosis_Disease, patientRoom.RoomNo ";
						String From_stmt = " from patientMedical, patientInfo, doctor, patientRoom";
						String Where_stmt = "";
						if (patientDoV.isEmpty() == true) {
							Where_stmt = " where patientInfo.Name = \"" + patientName
									+ "\" and patientInfo.PatientID = patientMedical.PatientID "
									+ " and patientMedical.DoctorID = doctor.DoctorID "
									+ " and patientMedical.PatientID = patientRoom.PatientID; ";
						} else {
							Where_stmt = " where patientInfo.Name = \"" + patientName
									+ "\" and patientInfo.DateofVisit = \"" + patientDoV
									+ "\" and patientInfo.PatientID = patientMedical.PatientID "
									+ " and patientMedical.DoctorID = doctor.DoctorID "
									+ " and patientMedical.PatientID = patientRoom.PatientID; ";
						}
						int count = 0;
						ResultSet tmp = stmt.executeQuery(Select_stmt + From_stmt + Where_stmt);
						while (tmp.next()) {
							count++;
						}

						ResultSet rs = stmt.executeQuery(Select_stmt + From_stmt + Where_stmt);
						ResultSetMetaData rsmd = rs.getMetaData();

						String[] columns = new String[rsmd.getColumnCount()];
						String[][] rows = new String[count][rsmd.getColumnCount()];
						// System.out.println(rs.getRow());

						for (int i = 1; i <= rsmd.getColumnCount(); i++) {
							columns[i - 1] = rsmd.getColumnName(i);
						}

						int j = 0;
						while (rs.next()) {
							for (int i = 1; i <= rsmd.getColumnCount(); i++) {
								rows[j][i - 1] = rs.getString(i);
							}
							j++;
						}

						new Table(columns, rows);

					} catch (SQLException e1) {
						e1.printStackTrace();
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
		panel.add(patientNameF, gbc);

		gbc.gridx = 0;
		gbc.gridy++;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.SOUTH;
		panel.add(DV, gbc);

		gbc.gridx = 1;
		gbc.gridwidth = 2;
		panel.add(DVF, gbc);

		gbc.gridy++;
		gbc.gridx = 3;
		gbc.gridwidth = 2;
		panel.add(search, gbc);

		MyFrame.rightPanel.add(panel, BorderLayout.NORTH);

	}
}
