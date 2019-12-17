package chgPass_sys;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import login_sys.login_wind;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class chgPass_wind {

	private JFrame frame;
	private JTextField txtUname;
	private JPasswordField txtPass;
	private JPasswordField txtCnfrm_pass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					chgPass_wind window = new chgPass_wind();
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
	public chgPass_wind() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 467);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblChangePassword = new JLabel("Change Password");
		lblChangePassword.setBounds(153, 32, 133, 20);
		frame.getContentPane().add(lblChangePassword);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(27, 76, 93, 20);
		frame.getContentPane().add(lblUsername);
		
		txtUname = new JTextField();
		txtUname.setBounds(26, 112, 371, 26);
		frame.getContentPane().add(txtUname);
		txtUname.setColumns(10);
		
		JLabel lblEnterNewPassword = new JLabel("Enter New Password");
		lblEnterNewPassword.setBounds(27, 154, 157, 20);
		frame.getContentPane().add(lblEnterNewPassword);
		
		txtPass = new JPasswordField();
		txtPass.setBounds(27, 190, 371, 26);
		frame.getContentPane().add(txtPass);
		
		JLabel lblConfirmNewPassword = new JLabel("Confirm New Password ");
		lblConfirmNewPassword.setBounds(27, 232, 179, 20);
		frame.getContentPane().add(lblConfirmNewPassword);
		
		txtCnfrm_pass = new JPasswordField();
		txtCnfrm_pass.setBounds(27, 268, 371, 26);
		frame.getContentPane().add(txtCnfrm_pass);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (txtUname.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Please insert Username");
//					txtUname.setText(null);
				}
				else if (!txtUname.getText().isEmpty()) {
					
					try {
						Class.forName("com.mysql.jdbc.Driver");
						Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/note_organiser","root","");
						Statement smt = con.createStatement();
						Statement smt1 = con.createStatement();
						String sqlck1 = "SELECT * FROM `users` WHERE uname = '"+txtUname.getText()+"';";
						ResultSet ck1= smt.executeQuery(sqlck1);
						String uid= null;
						
						if (ck1.next()) {
							uid = ck1.getString("u_id");
							System.out.print(uid);
//							SELECT * FROM `passwords` WHERE u_id = 1 AND pass = MD5("1234")
							String sqlck2 = "SELECT * FROM `passwords` WHERE u_id = '"+uid+"' AND pass = MD5('"+txtPass.getText()+"');";
							ResultSet ck2= smt.executeQuery(sqlck2);
							
							if (ck2.next()) {
								JOptionPane.showMessageDialog(null, "Password already used once");
								txtPass.setText(null);
							}
								
//								if (!txtPass.getText().equals(txtCnfrm_pass.getText())) {
//									JOptionPane.showMessageDialog(null, "Password and Confirm Password miss match please re-enter");
//									txtPass.setText(null);
//									txtCnfrm_pass.setText(null);
//								
//								}
								else {
									String sql = "INSERT INTO `passwords`( `u_id`, `pass`) VALUES ('"+uid+"', MD5('"+txtPass.getText()+"'));";
									smt.executeUpdate(sql);
									JOptionPane.showMessageDialog(null, "Password sussessfully changed");
									login_wind u1 = new login_wind();
									u1.main(null);
                                                                        frame.dispose();
								}
								
							}
							
							
						else {
							JOptionPane.showMessageDialog(null, "Username doesnot exist");
							txtUname.setText(null);
						}
					
					} 
					
					
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
		});
		btnConfirm.setBounds(153, 334, 115, 29);
		frame.getContentPane().add(btnConfirm);
	}
}
