package com.lab.labyrinth.launcher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class OptionsConfiguration {

	private Properties properties = new Properties();
	private String volume, sensitivity, brightness, forward, back, right, turnRight, turnLeft, run, jump, left;

	public OptionsConfiguration() {
		volume = "";
		sensitivity = "";
		brightness = "";
		forward = "";
		back = "";
		right = "";
		turnRight = "";
		turnLeft = "";
		run = "";
		jump = "";
		left = "";
	}

	public void saveConfiguration(int volume, int sensitivity, int brightness, String forward, String back, String right, String left, String turnRight, String turnLeft, String run, String jump, String path) {
		try {
			File file = new File(path);
			if (!file.exists())
				file.createNewFile();
			OutputStream write = new FileOutputStream(path);
			properties.setProperty("Volume", Integer.toString(volume));
			properties.setProperty("Sensitivity", Integer.toString(sensitivity));
			properties.setProperty("Brightness", Integer.toString(brightness));
			properties.setProperty("Forward", forward);
			properties.setProperty("Back", back);
			properties.setProperty("Right", right);
			properties.setProperty("Left", left);
			properties.setProperty("TurnRight", turnRight);
			properties.setProperty("TurnLeft", turnLeft);
			properties.setProperty("Run", run);
			properties.setProperty("Jump", jump);
			properties.storeToXML(write, "Options");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loadConfiguration(String path) {
		try {
			File file = new File("res/settings/config.xml");
			if (!file.exists())
				saveConfiguration(50, 50, 50, "W", "S", "D", "A", "RIGHT", "LEFT", "SHIFT", "SPACE", "res/settings/config.xml");
			InputStream read = new FileInputStream(path);
			properties.loadFromXML(read);
			volume = properties.getProperty("Volume");
			sensitivity = properties.getProperty("Sensitivity");
			brightness = properties.getProperty("Brightness");
			forward = properties.getProperty("Forward");
			back = properties.getProperty("Back");
			right = properties.getProperty("Right");
			turnRight = properties.getProperty("TurnRight");
			turnLeft = properties.getProperty("TurnLeft");
			run = properties.getProperty("Run");
			jump = properties.getProperty("Jump");
			left = properties.getProperty("Left");
			read.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getVolume() {
		return Integer.parseInt(volume);
	}

	public int getSensitivity() {
		return Integer.parseInt(sensitivity);
	}

	public int getBrightness() {
		return Integer.parseInt(brightness);
	}

	public String getForward() {
		return forward;
	}

	public String getBack() {
		return back;
	}

	public String getRight() {
		return right;
	}

	public String getTurnRight() {
		return turnRight;
	}

	public String getTurnLeft() {
		return turnLeft;
	}

	public String getRun() {
		return run;
	}

	public String getJump() {
		return jump;
	}

	public String getLeft() {
		return left;
	}

}
