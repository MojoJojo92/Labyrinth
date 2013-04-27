package com.lab.labyrinth.account;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class AccountSerialization {

	private ArrayList<Account> accounts = new ArrayList<Account>();
	private Account account;
	private String username;
	private String password;
	private String path = "res/accounts/accounts.ser";
	private int index;

	public AccountSerialization(String username, String password) {
		this.username = username;
		this.password = password;
		account = new Account(username, password);
		// serializing();
	}

	private void serializing() {
		try {
			File file = new File(path);
			if (!file.exists())
				file.createNewFile();
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(accounts);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		} finally {
			System.out.println("Serialization Attempted...");
		}

	}

	@SuppressWarnings({ "unchecked", "finally" })
	private ArrayList<Account> deserializing() {
		ArrayList<Account> temp = null;
		try {
			File file = new File(path);
			if (!file.exists())
				file.createNewFile();
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			temp = (ArrayList<Account>) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException c) {
			c.printStackTrace();
		} finally {
			System.out.println("De-Serialization Attempted...");
			return temp;
		}

	}

	private boolean Exists() {
		boolean exists = false;
		int i = 0;
		while (!exists && i < accounts.size()) {
			if (accounts.get(i).getUsername().equals(username)) {
				exists = true;
				index = i;
			}
			i++;
		}
		return exists;
	}

	public boolean Create() {
		boolean ok = true;
		accounts = deserializing();
		if (!Exists()) {
			for (Account e : accounts) {
				System.out.println("Username: " + e.getUsername());
			}
			accounts.add(account);
			serializing();
		} else {
			JOptionPane.showMessageDialog(null, "Username already exists", "Error", JOptionPane.ERROR_MESSAGE);
			ok = false;
		}
		return ok;
	}

	public boolean LogIn() {
		boolean ok = true;
		accounts = deserializing();
		if (Exists()) {
			if (!accounts.get(index).getPassword().equals(password)) {
				JOptionPane.showMessageDialog(null, "Wrong username or password", "Error", JOptionPane.ERROR_MESSAGE);
				ok = false;
			}
		} else {
			JOptionPane.showMessageDialog(null, "Wrong username or password", "Error", JOptionPane.ERROR_MESSAGE);
			ok = false;
		}
		return ok;
	}
}
