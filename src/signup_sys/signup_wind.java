package signup_sys;

import java.awt.EventQueue;
import java.sql.*;

import java.security.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import login_sys.login_wind;

import javax.swing.JButton;
import javax.swing.JSeparator;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class signup_wind {
  
  private JFrame frame;
  private JTextField txtUname;
  private JTextField txtEmail;
  private JTextField txtCntc_no;
  private JPasswordField txtPass;
  private JPasswordField textCnfrm_pass;
  
  /**
   * Launch the application.
   */
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          signup_wind window = new signup_wind();
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
  public signup_wind() {
    initialize();
  }
  
  /**
   * Initialize the contents of the frame.
   */
  private void initialize() {
    frame = new JFrame();
    frame.setBounds(100, 100, 576, 595);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(null);
    
    JLabel lblSignUp = new JLabel("Sign Up Form");
    lblSignUp.setBounds(239, 35, 115, 20);
    frame.getContentPane().add(lblSignUp);
    
    JLabel lblUsername = new JLabel("Username");
    lblUsername.setBounds(76, 111, 99, 20);
    frame.getContentPane().add(lblUsername);
    
    JLabel lblEmail = new JLabel("Email");
    lblEmail.setBounds(76, 158, 79, 20);
    frame.getContentPane().add(lblEmail);
    
    JLabel lblPassword = new JLabel("Password");
    lblPassword.setBounds(76, 208, 69, 20);
    frame.getContentPane().add(lblPassword);
    
    JLabel lblConfirmPassword = new JLabel("Confirm Password");
    lblConfirmPassword.setBounds(76, 262, 145, 20);
    frame.getContentPane().add(lblConfirmPassword);
    
    JLabel lblContactNo = new JLabel("Contact No.");
    lblContactNo.setBounds(76, 314, 99, 20);
    frame.getContentPane().add(lblContactNo);
    
    txtUname = new JTextField(35);
    txtUname.setBounds(251, 108, 183, 26);
    frame.getContentPane().add(txtUname);
    txtUname.setColumns(10);
    
    txtEmail = new JTextField();
    txtEmail.setBounds(251, 155, 183, 26);
    frame.getContentPane().add(txtEmail);
    txtEmail.setColumns(10);
    
    txtCntc_no = new JTextField(11);
    txtCntc_no.setBounds(251, 314, 183, 26);
    frame.getContentPane().add(txtCntc_no);
    txtCntc_no.setColumns(10);
    
    JButton btnSignUp = new JButton("Sign Up");
    btnSignUp.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        
        String check = txtUname.getText();
        System.out.print(check);
        if(txtUname.getText().isEmpty() ||
           txtEmail.getText().isEmpty() ||
           txtPass.getText().isEmpty() ||
           textCnfrm_pass.getText().isEmpty() ||
           txtCntc_no.getText().isEmpty()) {
          JOptionPane.showMessageDialog(null, "Please fill all fields");
        }
        
        else if (!check.isEmpty()){
//     JOptionPane.showMessageDialog(null, "Fields not empty");
          
          
          
//    JOptionPane.showMessageDialog(null, "Success");
          if (txtPass.getText().length()<7) {
            JOptionPane.showMessageDialog(null, "Password length must be atleast 7");
            txtPass.setText(null);
            textCnfrm_pass.setText(null);
          } 
          
          else if (!txtPass.getText().equals(textCnfrm_pass.getText())) {
            JOptionPane.showMessageDialog(null, "Password and Confirm Password miss match please re-enter");
            txtPass.setText(null);
            textCnfrm_pass.setText(null);
//     
          }
          
          
          else {
            try {
              Class.forName("com.mysql.jdbc.Driver");
              Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/note_organiser","root","");
              Statement smt = con.createStatement();
              Statement smt1 = con.createStatement();
              String sqlck1 = "SELECT * FROM `users` WHERE uname = '"+txtUname.getText()+"';";
              String sqlck2 = "SELECT * FROM `users` WHERE email = '"+txtEmail.getText()+"';";
              ResultSet ck1= smt.executeQuery(sqlck1);
              ResultSet ck2= smt1.executeQuery(sqlck2);
              
              if (ck1.next()) {
                JOptionPane.showMessageDialog(null, "Username already used please use another");
                txtUname.setText(null);
              }
              else if (ck2.next()) {
                JOptionPane.showMessageDialog(null, "Email already used please use another");
                txtEmail.setText(null);
              }
              
              
              else if(!txtEmail.getText().endsWith(".com")||
                      !txtEmail.getText().contains("@")){
                JOptionPane.showMessageDialog(null, "Please enter a valid Email");
                txtEmail.setText(null);
                
              }
              
              else if (!ck1.next()&&!ck2.next()){
                String sql = "INSERT INTO `users`(`uname`, `email`, `p_no`) VALUES ('"+txtUname.getText()+"','"+txtEmail.getText()+"','"+txtCntc_no.getText()+"');";
                String sql1 = "SELECT `u_id`FROM `users` WHERE uname = '"+txtUname.getText()+"';"; 
                System.out.print(sql);
                smt.executeUpdate(sql);
                ResultSet pin =smt.executeQuery(sql1);
                String uid = null;
                if (pin.next()) {
                  uid = pin.getString("u_id");
                  System.out.print(uid);
                }
                String sql2 = "INSERT INTO `passwords`(`u_id`, `pass`) VALUES ('"+uid+"', MD5('"+txtPass.getText()+"'));";
                smt.executeUpdate(sql2);
                JOptionPane.showMessageDialog(null, "Sign Up Successful");
                
                
                
                login_wind u1 = new login_wind();
                u1.main(null);
                frame.dispose();
                
//      System.exit(0);
              }
              
            } catch (Exception e) {
              // TODO Auto-generated catch block
//     JOptionPane.showMessageDialog(null, "Incorrect! Please recheck the fields");
              e.printStackTrace();
              
            }
          }
        }
        
        
        
      } 
    });
    btnSignUp.setBounds(219, 403, 115, 29);
    frame.getContentPane().add(btnSignUp);
    
    JSeparator separator = new JSeparator();
    separator.setBounds(45, 378, 480, 9);
    frame.getContentPane().add(separator);
    
    JSeparator separator_1 = new JSeparator();
    separator_1.setBounds(45, 71, 480, 9);
    frame.getContentPane().add(separator_1);
    
    txtPass = new JPasswordField();
    txtPass.setBounds(251, 205, 183, 26);
    frame.getContentPane().add(txtPass);
    
    textCnfrm_pass = new JPasswordField();
    textCnfrm_pass.setBounds(251, 259, 183, 26);
    frame.getContentPane().add(textCnfrm_pass);
  }
}
