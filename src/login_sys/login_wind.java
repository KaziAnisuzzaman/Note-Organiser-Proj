package login_sys;

import java.awt.EventQueue;

import signup_sys.signup_wind;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import chgPass_sys.chgPass_wind;
import com.sun.glass.ui.Cursor;
import userPanel_sys.userPanel;

import javax.swing.JSeparator;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import userPanel_sys.userPanel;

public class login_wind {

	private JFrame frame;
	private JTextField txtUname;
	private JPasswordField txtPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login_wind window = new login_wind();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public login_wind() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 506);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblLogin = new JLabel("User Login");
		lblLogin.setBounds(179, 40, 94, 20);
		frame.getContentPane().add(lblLogin);
		
		txtUname = new JTextField();
		txtUname.setBounds(217, 130, 146, 26);
		frame.getContentPane().add(txtUname);
		txtUname.setColumns(10);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(83, 133, 79, 20);
		frame.getContentPane().add(lblUsername);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(83, 205, 79, 20);
		frame.getContentPane().add(lblPassword);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(31, 81, 382, 9);
		frame.getContentPane().add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(31, 293, 382, 9);
		frame.getContentPane().add(separator_1);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/note_organiser","root","");
					Statement smt = con.createStatement();
					
					String sql1 = "SELECT `u_id`FROM `users` WHERE uname = '"+txtUname.getText()+"';"; 
					System.out.print(sql1);
					smt.executeQuery(sql1);
					ResultSet pin =smt.executeQuery(sql1);
					String uid = null;
					if (pin.next()) {
					uid = pin.getString("u_id");
					System.out.print(uid);
					}
					
					else {
						JOptionPane.showMessageDialog(null, "Invalid Username");
						txtPass.setText(null);
						txtUname.setText(null);
//						System.exit(0);
						
					}
					
					
					String sql2 = "SELECT * FROM `passwords` WHERE u_id = '"+uid+ "' AND pass = MD5('"+txtPass.getText()+"');";
					ResultSet ck = smt.executeQuery(sql2);
					if (ck.next()) {
						JOptionPane.showMessageDialog(null, "Sign In Successful");
                                                userPanel home_wind = new userPanel();
                                                home_wind.main(null);
                                                frame.dispose();
					}
					else {
						JOptionPane.showMessageDialog(null, "Invalid Password");
					}
					
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
		});
		btnLogin.setBounds(151, 318, 115, 29);
		frame.getContentPane().add(btnLogin);
		
		JButton btnSignUp = new JButton("Sign Up");
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
                                
				signup_wind s1 = new signup_wind();
				s1.main(null);
                                frame.dispose();
                                
			}
		});
		btnSignUp.setBounds(47, 363, 115, 29);
		frame.getContentPane().add(btnSignUp);
		
		txtPass = new JPasswordField();
		txtPass.setBounds(218, 202, 145, 26);
		frame.getContentPane().add(txtPass);
		
		JButton btnForgotPassword = new JButton("Forgot Password");
		btnForgotPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chgPass_wind fgtPass = new chgPass_wind ();
				fgtPass.main(null);
                                frame.dispose();
                                
			}
		});
		btnForgotPassword.setBounds(217, 363, 177, 29);
		frame.getContentPane().add(btnForgotPassword);
	}
}
