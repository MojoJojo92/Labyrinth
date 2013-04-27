package com.lab.labyrinth.account;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.border.BevelBorder;

import com.lab.labyrinth.launcher.LauncherGui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountGui extends JFrame {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JPanel panel_0, panel_1, panel_2;
	private JButton btnLogIn, btnCreate;
	private JTextField textField_0, textField_1;
	private JPasswordField passwordField_0, passwordField_1, passwordField_2;
	private JLabel lblPassword_0, lblUsername_0, lblLogIn, lblNewAccount, lblUsername_1, lblPassword_1, lblRetypePassword;

	public AccountGui() {

		frame = new JFrame("Account");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setSize(330, 286);
		frame.setLocationRelativeTo(null);

		panel_0 = new JPanel(null);
		frame.getContentPane().add(panel_0);

		panel_1 = new JPanel(null);
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_1.setBounds(10, 11, 294, 97);
		panel_0.add(panel_1);

		panel_2 = new JPanel(null);
		panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_2.setBounds(10, 119, 294, 119);
		panel_0.add(panel_2);

		btnLogIn = new JButton("Log In");
		btnLogIn.setBounds(211, 62, 73, 23);
		panel_1.add(btnLogIn);

		passwordField_0 = new JPasswordField();
		passwordField_0.setBounds(110, 63, 91, 20);
		panel_1.add(passwordField_0);

		lblPassword_0 = new JLabel("Password");
		lblPassword_0.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblPassword_0.setBounds(10, 66, 65, 14);
		panel_1.add(lblPassword_0);

		lblUsername_0 = new JLabel("Username");
		lblUsername_0.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblUsername_0.setBounds(10, 41, 65, 14);
		panel_1.add(lblUsername_0);

		lblLogIn = new JLabel("Log In");
		lblLogIn.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblLogIn.setBounds(10, 11, 46, 19);
		panel_1.add(lblLogIn);

		textField_0 = new JTextField();
		textField_0.setBounds(110, 38, 91, 20);
		textField_0.setColumns(10);
		panel_1.add(textField_0);

		btnCreate = new JButton("Create");
		btnCreate.setBounds(211, 80, 73, 23);
		panel_2.add(btnCreate);

		lblNewAccount = new JLabel("New Account");
		lblNewAccount.setFont(new Font("Times New Roman", Font.BOLD, 15));
		lblNewAccount.setBounds(10, 11, 90, 14);
		panel_2.add(lblNewAccount);

		lblUsername_1 = new JLabel("Username");
		lblUsername_1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblUsername_1.setBounds(10, 36, 65, 14);
		panel_2.add(lblUsername_1);

		lblPassword_1 = new JLabel("Password");
		lblPassword_1.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblPassword_1.setBounds(10, 61, 65, 14);
		panel_2.add(lblPassword_1);

		lblRetypePassword = new JLabel("Retype Password");
		lblRetypePassword.setFont(new Font("Times New Roman", Font.PLAIN, 12));
		lblRetypePassword.setBounds(10, 84, 90, 14);
		panel_2.add(lblRetypePassword);

		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(110, 81, 91, 20);
		panel_2.add(passwordField_2);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(110, 57, 91, 20);
		panel_2.add(passwordField_1);

		textField_1 = new JTextField();
		textField_1.setBounds(110, 33, 91, 20);
		textField_1.setColumns(10);
		panel_2.add(textField_1);

		btnLogIn.addActionListener(new ButtonListenerLogin());
		btnCreate.addActionListener(new ButtonListenerCreate());

		frame.repaint();
	}

	public class ButtonListenerLogin implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			String username = textField_0.getText(), pass = "";
			char[] password = passwordField_0.getPassword();

			for (int i = 0; i < password.length; i++) {
				pass += password[i];
			}
			AccountSerialization serialize = new AccountSerialization(username, pass);
			if (serialize.LogIn()) {
				new LauncherGui(username);
				frame.dispose();
			}
		}
	}

	public class ButtonListenerCreate implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {

			String username = textField_1.getText(), pass1 = "", pass2 = "";
			char[] password1 = passwordField_1.getPassword();
			char[] password2 = passwordField_2.getPassword();

			for (int i = 0; i < password1.length; i++) {
				pass1 += password1[i];
			}
			for (int i = 0; i < password2.length; i++) {
				pass2 += password2[i];
			}

			if (username.equals("")) {
				JOptionPane.showMessageDialog(frame, "You must fill all the gaps", "Error", JOptionPane.ERROR_MESSAGE);
			} else if (pass1.length() < 5) {
				JOptionPane.showMessageDialog(frame, "Password must be at least 5 characters", "Error", JOptionPane.ERROR_MESSAGE);
			} else if (!(pass1.equals(pass2))) {
				JOptionPane.showMessageDialog(frame, "Passwords don't match", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				AccountSerialization serialize = new AccountSerialization(username, pass1);
				if (serialize.Create()) {
					new LauncherGui(username);
					frame.dispose();
				}
			}
		}
	}
}
