
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
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

public class PatientStatistics {
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();

	public PatientStatistics() {
		MyFrame f = new MyFrame();

		JLabel firstLine = new JLabel("Choose any one among the following: ");
		firstLine.setFont(new Font("Cambria", Font.PLAIN, 16));
		firstLine.setBorder(new MatteBorder(0, 0, 1, 0, Color.black));

		MyRadioButton query2 = new MyRadioButton(
				"No. of Patients admitted in the Hostpital of a particular Disease/speciality");
		MyRadioButton query3 = new MyRadioButton("Average No. of Patients per Month");
		MyRadioButton query4 = new MyRadioButton("Month of the Year with the Highest No. of Patients");
		MyRadioButton query6 = new MyRadioButton("No. of Outdoor/Indoor Patients in a particular Month");
		MyRadioButton query10 = new MyRadioButton("Total No. of Beds in the Hospital");
		MyRadioButton query12 = new MyRadioButton(
				"List all the Patients in the Hospital (in non-increasing order by Date of Visit)");

		ButtonGroup group = new ButtonGroup();
		group.add(query2);
		group.add(query3);
		group.add(query4);
		group.add(query6);
		group.add(query10);
		group.add(query12);

		Box hbox = Box.createHorizontalBox();
		hbox.add(Box.createHorizontalStrut(50));

		Box buttonBox = Box.createVerticalBox();
		buttonBox.add(Box.createVerticalStrut(50));
		buttonBox.add(firstLine);
		buttonBox.add(Box.createVerticalStrut(10));
		buttonBox.add(query2);
		buttonBox.add(query3);
		buttonBox.add(query4);
		buttonBox.add(query6);
		buttonBox.add(query10);
		buttonBox.add(query12);

		hbox.add(buttonBox);

		query2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				getNoofPatients();
			}
		});

		query3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Statement stmt = null;
				try {
					stmt = View.con.createStatement();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String Select_stmt = "select avg(NoofPatients) ";
				String From_stmt = " from (select count(patientID) as NoofPatients, DateofVisit from patientInfo group by MONTH(DateofVisit), YEAR(DateofVisit))as T; ";
				try {
					int count = 0;
					ResultSet tmp = stmt.executeQuery(Select_stmt + From_stmt);
					while (tmp.next()) {
						count++;
					}

					ResultSet rs = stmt.executeQuery(Select_stmt + From_stmt);
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

				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});

		query4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
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
					stmt.execute("drop table T1;");
					stmt.execute(
							"create table T1 select count(PatientID) as NoofPatients, MONTH(DateofVisit) as Month from patientInfo group by MONTH(DateofVisit);");
					int count = 0;
					ResultSet tmp = stmt.executeQuery(
							"select * from T1 where ( select max(NoofPatients) from T1) = T1.NoofPatients;");
					while (tmp.next()) {
						count++;
					}

					rs = stmt.executeQuery(
							"select * from T1 where ( select max(NoofPatients) from T1) = T1.NoofPatients;");
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

					new Table(columns, rows);

				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

			}
		});

		query6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				enterMonth();
			}
		});

		query10.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Statement stmt = null;
				try {
					stmt = View.con.createStatement();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String Select_stmt = "select sum(NoofBeds) as TotalNoofBeds from Room; ";
				try {

					int count = 0;
					ResultSet tmp = stmt.executeQuery(Select_stmt);
					while (tmp.next()) {
						count++;
					}

					ResultSet rs = stmt.executeQuery(Select_stmt);
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

				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

			}
		});

		query12.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Statement stmt = null;
				try {
					stmt = View.con.createStatement();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				String Select_stmt = "select * from patientInfo order by DateofVisit desc; ";
				try {

					int count = 0;
					ResultSet tmp = stmt.executeQuery(Select_stmt);
					while (tmp.next()) {
						count++;
					}

					ResultSet rs = stmt.executeQuery(Select_stmt);
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

				} catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

			}
		});

		MyFrame.rightPanel.add(hbox, BorderLayout.NORTH);

	}

	public void getNoofPatients() {
		// TODO Auto-generated method stub
		JFrame enterDisease = new JFrame();
		enterDisease.setTitle("Query 2");
		enterDisease.setSize(366, 268);
		enterDisease.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		enterDisease.setLocation(screenSize.width / 3, screenSize.height / 3);
		enterDisease.setResizable(false);

		Font myFont = new Font("Agency FB", Font.PLAIN, 24);
		Font myFont2 = new Font("Cambria", Font.PLAIN, 16);

		Container pane = enterDisease.getContentPane();
		pane.setBackground(Color.orange);
		pane.setForeground(Color.black);

		JLabel name = new JLabel("Disease Name: ");
		name.setFont(myFont);

		JTextField nameF = new JTextField();
		nameF.setFont(myFont2);
		nameF.setMaximumSize(new Dimension(200, 35));

		JButton OK = new JButton("OK");
		OK.setFont(myFont);
		OK.setBackground(Color.darkGray);
		OK.setForeground(Color.white);
		OK.setFocusable(false);

		Box vbox = Box.createVerticalBox();
		Box hbox = Box.createHorizontalBox();
		hbox.add(Box.createHorizontalStrut(20));
		hbox.add(name);
		hbox.add(Box.createHorizontalStrut(20));
		hbox.add(nameF);
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
				String disease = nameF.getText();
				if (disease.isEmpty() == true) {
					JOptionPane.showMessageDialog(enterDisease, "Kindly Enter some Value");
				} else {
					Statement stmt = null;
					try {
						stmt = View.con.createStatement();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String Select_stmt = "select count(patientID) as NoofPatients ";
					String From_stmt = " from patientMedical ";
					String Where_stmt = " group by Diagnosis_Disease having Diagnosis_Disease = \"" + disease + "\";";
					try {
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

						enterDisease.dispose();
						new Table(columns, rows);
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			}
		});

		enterDisease.setVisible(true);

	}

	public void enterMonth() {
		// TODO Auto-generated method stub
		JFrame enterMonthFrame = new JFrame();
		enterMonthFrame.setTitle("Query 6");
		enterMonthFrame.setSize(366, 268);
		enterMonthFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		enterMonthFrame.setLocation(screenSize.width / 3, screenSize.height / 3);
		enterMonthFrame.setResizable(false);

		Font myFont = new Font("Agency FB", Font.PLAIN, 24);
		Font myFont2 = new Font("Cambria", Font.PLAIN, 16);

		Container pane = enterMonthFrame.getContentPane();
		pane.setBackground(Color.orange);
		pane.setForeground(Color.black);

		JLabel no = new JLabel("Month No.: ");
		no.setFont(myFont);

		JTextField noF = new JTextField();
		noF.setFont(myFont2);
		noF.setMaximumSize(new Dimension(200, 35));

		JButton OK = new JButton("OK");
		OK.setFont(myFont);
		OK.setBackground(Color.darkGray);
		OK.setForeground(Color.white);
		OK.setFocusable(false);

		Box vbox = Box.createVerticalBox();
		Box hbox = Box.createHorizontalBox();
		hbox.add(Box.createHorizontalStrut(20));
		hbox.add(no);
		hbox.add(Box.createHorizontalStrut(20));
		hbox.add(noF);
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
				String mon = noF.getText();
				if (mon.isEmpty() == true) {
					JOptionPane.showMessageDialog(enterMonthFrame, "Kindly Enter some Value");
				} else {
					Integer month = Integer.parseInt(mon);
					if (month <= 12 && month >= 1) {
						Statement stmt = null;
						try {
							stmt = View.con.createStatement();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						String Select_stmt = "select Indoor_Outdoor, count(Indoor_Outdoor) as NoofPatients, MONTH(DateofVisit) as Month  ";
						String From_stmt = " from patientMedical, patientInfo ";
						String Where_stmt = " where patientMedical.PatientID = patientInfo.PatientID and MONTH(DateofVisit) = "
								+ mon + " group by Indoor_Outdoor, MONTH(DateofVisit); ";
						try {
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

							enterMonthFrame.dispose();
							new Table(columns, rows);

						} catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(enterMonthFrame, "Enter a valid Month");
					}
				}
			}
		});

		enterMonthFrame.setVisible(true);

	}
}
