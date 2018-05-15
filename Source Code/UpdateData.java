
/*
 * Author:
 * 	Sanidhya Singal 2015085
 * 	Saurabh Kapur 2015087
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

public class UpdateData {
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();

	public UpdateData() {
		MyFrame f = new MyFrame();

		Font myFont = new Font("Cambria", Font.PLAIN, 16);
		JLabel firstLine = new JLabel("Choose any one among the following: ");
		firstLine.setFont(myFont);
		firstLine.setBorder(new MatteBorder(0, 0, 1, 0, Color.black));

		JLabel beforeQuery78 = new JLabel(
				"<html>1. You are a doctor. Your patient has somehow got some complications or side effects of a <br>medicine that you gave. You want to change the treatment.</html>");
		beforeQuery78.setFont(myFont);

		MyRadioButton query7 = new MyRadioButton("Change in Treatment due to Side Effects of a Medicine");
		MyRadioButton query8 = new MyRadioButton("Change in Treatment due to some Complications");

		JLabel beforeQuery9 = new JLabel(
				"<html>2. Due to some co-morbidity, a patient is advised to visit another doctor in the Hospital.</html>");
		beforeQuery9.setFont(myFont);
		MyRadioButton query9 = new MyRadioButton("Add another Doctor in the list of Doctors for a given Patient");

		ButtonGroup group = new ButtonGroup();
		group.add(query7);
		group.add(query8);
		group.add(query9);

		Box hbox = Box.createHorizontalBox();
		hbox.add(Box.createHorizontalStrut(50));
		Box vbox = Box.createVerticalBox();
		vbox.add(Box.createVerticalStrut(50));
		vbox.add(firstLine);
		vbox.add(Box.createVerticalStrut(10));
		vbox.add(beforeQuery78);
		vbox.add(Box.createVerticalStrut(5));
		vbox.add(query7);
		vbox.add(query8);
		vbox.add(Box.createVerticalStrut(10));
		vbox.add(beforeQuery9);
		vbox.add(Box.createVerticalStrut(5));
		vbox.add(query9);
		hbox.add(vbox);

		query7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				newTreatment();
			}
		});

		query8.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				newTreatment();
			}
		});

		query9.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				enterDoctor();
			}
		});

		MyFrame.rightPanel.add(hbox, BorderLayout.NORTH);
	}

	public void newTreatment() {
		// TODO Auto-generated method stub
		JFrame addTreatmentFrame = new JFrame();
		addTreatmentFrame.setTitle("Modify Treatment");
		addTreatmentFrame.setSize(366, 268);
		addTreatmentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		addTreatmentFrame.setLocation(screenSize.width / 3, screenSize.height / 3);
		addTreatmentFrame.setResizable(false);

		Font myFont = new Font("Agency FB", Font.PLAIN, 24);
		Font myFont2 = new Font("Cambria", Font.PLAIN, 16);

		Container pane = addTreatmentFrame.getContentPane();
		pane.setBackground(Color.orange);
		pane.setForeground(Color.black);

		JLabel patientID = new JLabel("PatientID:   ");
		JLabel treatment = new JLabel("Treatment: ");
		patientID.setFont(myFont);
		treatment.setFont(myFont);

		JTextField patientIDF = new JTextField();
		patientIDF.setFont(myFont2);
		patientIDF.setMaximumSize(new Dimension(500, 35));

		JTextField treatmentF = new JTextField();
		treatmentF.setFont(myFont2);
		treatmentF.setMaximumSize(new Dimension(500, 35));

		JButton OK = new JButton("OK");
		OK.setFont(myFont);
		OK.setBackground(Color.darkGray);
		OK.setForeground(Color.white);
		OK.setFocusable(false);

		Box vbox = Box.createVerticalBox();
		Box hbox = Box.createHorizontalBox();
		hbox.add(Box.createHorizontalStrut(20));
		hbox.add(patientID);
		hbox.add(Box.createHorizontalStrut(20));
		hbox.add(patientIDF);
		hbox.add(Box.createHorizontalStrut(20));
		vbox.add(Box.createVerticalStrut(20));
		vbox.add(hbox);

		hbox = Box.createHorizontalBox();
		hbox.add(Box.createHorizontalStrut(20));
		hbox.add(treatment);
		hbox.add(Box.createHorizontalStrut(20));
		hbox.add(treatmentF);
		hbox.add(Box.createHorizontalStrut(20));
		vbox.add(hbox);
		vbox.add(Box.createVerticalStrut(20));
		hbox = Box.createHorizontalBox();
		hbox.add(Box.createHorizontalStrut(10));
		hbox.add(OK);
		vbox.add(hbox);
		vbox.add(Box.createVerticalStrut(20));

		pane.add(vbox);

		OK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String pID = patientIDF.getText();
				String newTreat = treatmentF.getText();

				if (pID.isEmpty() == true || newTreat.isEmpty() == true) {
					JOptionPane.showMessageDialog(addTreatmentFrame, "Kindly Enter some Value");
				} else {
					Statement stmt = null;
					try {
						stmt = View.con.createStatement();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String Select_stmt = "update patientMedical set Treatment_Test_Medicine = \"" + newTreat
							+ "\" where PatientID = \"" + pID + "\";";
					String Select_stmt2 = " select * from patientMedical where PatientID = \"" + pID + "\";";
					try {
						stmt.execute(Select_stmt);

						int count = 0;
						ResultSet tmp = stmt.executeQuery(Select_stmt2);
						while (tmp.next()) {
							count++;
						}

						ResultSet rs = stmt.executeQuery(Select_stmt2);
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
						addTreatmentFrame.dispose();
						new Table(columns, rows);

					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			}
		});

		addTreatmentFrame.setVisible(true);
	}

	public void enterDoctor() {
		// TODO Auto-generated method stub
		JFrame enterDoctorFrame = new JFrame();
		enterDoctorFrame.setTitle("Addition of Doctor");
		enterDoctorFrame.setSize(366, 268);
		enterDoctorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		enterDoctorFrame.setLocation(screenSize.width / 3, screenSize.height / 3);
		enterDoctorFrame.setResizable(false);

		Font myFont = new Font("Agency FB", Font.PLAIN, 24);
		Font myFont2 = new Font("Cambria", Font.PLAIN, 16);

		Container pane = enterDoctorFrame.getContentPane();
		pane.setBackground(Color.orange);
		pane.setForeground(Color.black);

		JLabel patientID = new JLabel("PatientID: ");
		patientID.setFont(myFont);

		JLabel doctorID = new JLabel("DoctorID: ");
		doctorID.setFont(myFont);

		JTextField doctorIDF = new JTextField();
		doctorIDF.setFont(myFont2);
		doctorIDF.setMaximumSize(new Dimension(200, 35));

		JTextField patientIDF = new JTextField();
		patientIDF.setFont(myFont2);
		patientIDF.setMaximumSize(new Dimension(200, 35));

		JButton OK = new JButton("OK");
		OK.setFont(myFont);
		OK.setBackground(Color.darkGray);
		OK.setForeground(Color.white);
		OK.setFocusable(false);

		Box vbox = Box.createVerticalBox();
		Box hbox = Box.createHorizontalBox();
		hbox.add(Box.createHorizontalStrut(20));
		hbox.add(patientID);
		hbox.add(Box.createHorizontalStrut(20));
		hbox.add(patientIDF);
		hbox.add(Box.createHorizontalStrut(20));
		vbox.add(Box.createVerticalStrut(20));
		vbox.add(hbox);

		hbox = Box.createHorizontalBox();
		hbox.add(Box.createHorizontalStrut(20));
		hbox.add(doctorID);
		hbox.add(Box.createHorizontalStrut(20));
		hbox.add(doctorIDF);
		hbox.add(Box.createHorizontalStrut(20));
		vbox.add(Box.createVerticalStrut(20));
		vbox.add(hbox);
		hbox = Box.createHorizontalBox();
		hbox.add(Box.createHorizontalStrut(10));
		hbox.add(OK);
		vbox.add(hbox);
		vbox.add(Box.createVerticalStrut(20));

		pane.add(vbox);

		OK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String pID = patientIDF.getText();
				String dID = doctorIDF.getText();
				if (dID.isEmpty() == true || pID.isEmpty() == true) {
					JOptionPane.showMessageDialog(enterDoctorFrame, "Kindly Enter some Value");
				} else {
					Statement stmt = null;
					try {
						stmt = View.con.createStatement();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ResultSet rs = null;
					ResultSetMetaData rsmd = null;
					try {
						int flag = 0;
						rs = stmt.executeQuery("select DoctorID from patientMedical where PatientID = \"" + pID + "\"");
						rs.next();
						String Doc = rs.getString(1);
						String[] DocList = Doc.split(",");
						for (String str : DocList) {
							if (str.equals(dID) == true) {
								flag = 1;

							}
						}
						if (flag == 0) {
							Doc = Doc + "," + dID;
							stmt.execute("update patientMedical set DoctorID = \"" + Doc + "\" where PatientID = \""
									+ pID + "\"");

							int count = 0;
							ResultSet tmp = stmt
									.executeQuery("select * from patientMedical where PatientID = \"" + pID + "\";");
							while (tmp.next()) {
								count++;
							}

							rs = stmt.executeQuery("select * from patientMedical where PatientID = \"" + pID + "\";");
							rsmd = rs.getMetaData();

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
							enterDoctorFrame.dispose();
							new Table(columns, rows);

						} else {
							JOptionPane.showMessageDialog(enterDoctorFrame,
									"The Patient is already alloted to the Doctor");
						}
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}

				}
			}
		});

		enterDoctorFrame.setVisible(true);

	}

}

class MyRadioButton extends JRadioButton {
	Font myFont = new Font("Cambria", Font.PLAIN, 16);

	public MyRadioButton(String text) {
		setText(text);
		setBackground(MyFrame.rightPanel.getBackground());
		setFont(myFont);
		setFocusable(false);
	}
}