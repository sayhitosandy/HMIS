
/*
 * Author:
 * 	Sanidhya Singal 2015085
 * 	Saurabh Kapur 2015087
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

public class View {

	public static Connection con;

	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/HIMS?verifyServerCertificate=false&useSSL=true", "root",
					"sananiroh28723");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new MyWelcomeFrame();
	}
}

class MyWelcomeFrame extends JFrame {
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();

	public MyWelcomeFrame() {
		setTitle("HIMS : Welcome");
		setSize(screenSize.width - 342, screenSize.height - 168); // 1024 x
		// 600
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(screenSize.width / 10, screenSize.height / 10);
		// setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setResizable(false);

		Container c = getContentPane();
		c.setBackground(Color.darkGray);

		Box vbox = Box.createVerticalBox();

		ImageIcon img = new ImageIcon("hospital.jpg");
		JLabel imgLabel = new JLabel(img);
		imgLabel.setAlignmentX(CENTER_ALIGNMENT);
		imgLabel.setBorder(new BevelBorder(BevelBorder.RAISED));
		// imgLabel.setBorder(new EtchedBorder());

		JLabel label = new JLabel("Hospital Information Management System");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setBorder(new MatteBorder(2, 0, 2, 0, Color.orange));

		Font myFont = new Font("Agency FB", Font.PLAIN, 66);
		label.setFont(myFont);
		label.setForeground(Color.orange);

		JButton wb1 = new MyWelcomeButton("LOGIN");
		wb1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				login();
				// dispose();
				// new MyFrame();
			}
		});

		JButton wb2 = new MyWelcomeButton(" EXIT ");
		wb2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0); // Exit
			}
		});

		vbox.add(Box.createVerticalStrut(20));
		vbox.add(imgLabel);
		vbox.add(Box.createVerticalStrut(30));
		vbox.add(label);

		Box box2 = Box.createHorizontalBox();
		box2.add(wb1);
		box2.add(Box.createHorizontalStrut(20));
		box2.add(wb2);

		vbox.add(box2);

		JLabel footnote = new JLabel("\u00A9 Sanidhya-Saurabh");
		footnote.setFont(new Font("Cambria", Font.PLAIN, 16));
		footnote.setForeground(Color.white);
		footnote.setAlignmentX(CENTER_ALIGNMENT);
		vbox.add(footnote);

		add(vbox);

		setVisible(true);
	}

	public void login() {
		JFrame loginFrame = new JFrame();
		loginFrame.setTitle("Login");
		loginFrame.setSize(366, 268);
		loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		loginFrame.setLocation(screenSize.width / 3, screenSize.height / 3);
		loginFrame.setResizable(false);

		Font myFont = new Font("Agency FB", Font.PLAIN, 24);
		Font myFont2 = new Font("Cambria", Font.PLAIN, 16);

		Container pane = loginFrame.getContentPane();
		pane.setBackground(Color.orange);
		pane.setForeground(Color.black);

		JLabel name = new JLabel("Username: ");
		JLabel pass = new JLabel("Password:  ");
		name.setFont(myFont);
		pass.setFont(myFont);

		JTextField nameF = new JTextField();
		nameF.setFont(myFont2);
		nameF.setMaximumSize(new Dimension(500, 35));

		JPasswordField passF = new JPasswordField();
		passF.setFont(myFont2);
		passF.setMaximumSize(new Dimension(500, 35));
		passF.setEchoChar('*');

		JButton OK = new JButton("OK");
		OK.setAlignmentX(CENTER_ALIGNMENT);
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
		vbox.add(Box.createVerticalStrut(10));

		hbox = Box.createHorizontalBox();
		hbox.add(Box.createHorizontalStrut(20));
		hbox.add(pass);
		hbox.add(Box.createHorizontalStrut(20));
		hbox.add(passF);
		hbox.add(Box.createHorizontalStrut(20));
		vbox.add(hbox);
		vbox.add(Box.createVerticalStrut(20));
		vbox.add(OK);
		vbox.add(Box.createVerticalStrut(20));

		pane.add(vbox);

		OK.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String userName = nameF.getText();
				char[] password = passF.getPassword();
				String pwd = "";
				for (int i = 0; i < password.length; i++) {
					pwd += password[i];
				}
				if (userName.equals("admin") && pwd.equals("1234")) {
					loginFrame.dispose();
					dispose();
					new MyFrame(); // Start
				} else {
					JOptionPane.showMessageDialog(loginFrame, "Invalid UserName/Password");
				}

			}
		});

		loginFrame.setVisible(true);
	}
}

class MyWelcomeButton extends JButton {
	public MyWelcomeButton(String title) {
		Font myFont = new Font("Agency FB", Font.PLAIN, 32);
		setFocusable(false);
		setText(title);
		setFont(myFont);
		setForeground(Color.white);
		setBackground(Color.darkGray);
		setAlignmentX(Component.CENTER_ALIGNMENT);
		// setBorderPainted(false);
	}
}

class MyFrame extends JFrame {
	Toolkit kit = Toolkit.getDefaultToolkit();
	Dimension screenSize = kit.getScreenSize();
	public static JPanel panel, leftPanel, rightPanel;
	public static MyButton b1, b2, b3, b4, exit;

	public MyFrame() {
		setTitle("HIMS : Welcome Admin");
		setSize(screenSize.width - 342, screenSize.height - 168); // 1024 x 600
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(screenSize.width / 10, screenSize.height / 10);
		// setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
		setResizable(false);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		getContentPane().setBackground(Color.black);

		JLabel label = new JLabel("Hospital Information Management System");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);

		Font myFont = new Font("Agency FB", Font.PLAIN, 48);
		Font myFont2 = new Font("Agency FB", Font.PLAIN, 24);
		label.setFont(myFont);
		label.setForeground(Color.white);
		// add(Box.createVerticalStrut(10));
		add(label);

		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.white);

		leftPanel = new JPanel();
		leftPanel.setLayout(new BorderLayout());
		// leftPanel.setBackground(panel.getBackground());
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.setBackground(panel.getBackground());

		Box hbox = Box.createHorizontalBox();
		hbox.add(Box.createHorizontalStrut(20));
		Box vbox = Box.createVerticalBox();

		JLabel txt = new JLabel("Select Query Type:");
		txt.setFont(myFont2);
		txt.setBorder(new MatteBorder(0, 0, 1, 0, Color.black));
		txt.setBackground(panel.getBackground());

		ImageIcon img = new ImageIcon("Caduceus.jpg");
		JLabel imgLabel = new JLabel(img);
		imgLabel.setBorder(new EmptyBorder(0, 30, 0, 0));

		b1 = new MyButton("Patient Enquiry    ");
		b2 = new MyButton("Patient Statistics ");
		b3 = new MyButton("Medical History   ");
		b4 = new MyButton("Update Data        ");
		exit = new MyButton("Exit");

		Box rvbox = Box.createVerticalBox();

		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new PatientEnquiry();
			}
		});

		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new PatientStatistics();
			}
		});

		b3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MedicalHistory();
			}
		});

		b4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new UpdateData();
			}
		});

		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				System.exit(0); // Exit
			}
		});

		vbox.add(Box.createVerticalStrut(50));
		vbox.add(txt);
		vbox.add(Box.createVerticalStrut(10));
		vbox.add(b1);
		vbox.add(Box.createVerticalStrut(10));
		vbox.add(b2);
		vbox.add(Box.createVerticalStrut(10));
		vbox.add(b3);
		vbox.add(Box.createVerticalStrut(10));
		vbox.add(b4);
		vbox.add(Box.createVerticalStrut(30));
		vbox.add(imgLabel);

		hbox.add(vbox);
		hbox.add(Box.createHorizontalStrut(50));
		leftPanel.add(hbox, BorderLayout.NORTH);

		hbox = Box.createHorizontalBox();
		hbox.add(Box.createHorizontalStrut(20));
		vbox = Box.createVerticalBox();
		vbox.add(exit);
		vbox.add(Box.createVerticalStrut(20));
		hbox.add(vbox);

		leftPanel.add(hbox, BorderLayout.SOUTH);
		leftPanel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.add(leftPanel, BorderLayout.WEST);

		rightPanel.setBorder(new LineBorder(Color.lightGray));
		panel.add(rightPanel);

		getContentPane().add(panel);
		setVisible(true);
	}
}

class MyButton extends JButton {
	Font myFont = new Font("Agency FB", Font.PLAIN, 24);

	public MyButton(String title) {
		setText(title);
		setBackground(Color.WHITE);
		setForeground(Color.BLACK);
		setFocusable(false);
		setFont(myFont);
	}
}