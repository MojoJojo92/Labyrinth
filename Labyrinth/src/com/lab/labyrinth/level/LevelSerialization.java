package com.lab.labyrinth.level;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class LevelSerialization {

	private Level level;
	private ArrayList<Level> levels;
	private ArrayList<String> levelNames = new ArrayList<String>();
	private String levelPath = "res/levels/";
	private String namesPath = "res/levels/levelNames.ser";

	public LevelSerialization(Level level) {
		this.level = level;
		levels = new ArrayList<Level>();
		levels.add(level);
		levelPath += level.getName() + ".ser";
		serializeLevel();
	}

	public LevelSerialization() {

	}

	public LevelSerialization(String levelPath) {
		this.levelPath = levelPath;
	}

	public void serializeLevel() {
		try {
			File file = new File(levelPath);
			if (file.exists())
				file.createNewFile();
			FileOutputStream fileOut = new FileOutputStream(levelPath);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(levels);
			out.close();
			fileOut.close();
			serializeNames(level.getName());
		} catch (IOException i) {
			i.printStackTrace();
		} finally {
			System.out.println("Serialization Attempted...");
		}

	}

	public void serializeNames(String name) {
		try {
			File file = new File(namesPath);
			if (!file.exists()) {
				file.createNewFile();
			} else {
				levelNames = deserializeNames();
				levelNames.remove(name);
				remove(" ", levelNames);
				levelNames.add(name);
			}
			FileOutputStream fileOut = new FileOutputStream(namesPath);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(levelNames);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		} finally {
			System.out.println("Serialization Attempted...");
		}

	}

	public void serializeNames(ArrayList<String> names) {
		try {
			File file = new File(namesPath);
			if (!file.exists())
				file.createNewFile();
			else
				remove(" ", names);
			FileOutputStream fileOut = new FileOutputStream(namesPath);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(names);
			out.close();
			fileOut.close();
		} catch (IOException i) {
			i.printStackTrace();
		} finally {
			System.out.println("Serialization Attempted...");
		}

	}

	@SuppressWarnings({ "unchecked", "finally" })
	public ArrayList<Level> deserializeLevel(String levelName) {
		ArrayList<Level> temp = null;
		levelPath += levelName + ".ser";
		System.out.println(levelPath);
		try {
			File file = new File(levelPath);
			if (!file.exists())
				file.createNewFile();
			FileInputStream fileIn = new FileInputStream(levelPath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			temp = (ArrayList<Level>) in.readObject();
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

	@SuppressWarnings({ "unchecked", "finally" })
	public ArrayList<String> deserializeNames() {
		ArrayList<String> temp = new ArrayList<String>();
		temp.add(" ");
		try {
			File file = new File(namesPath);
			if (!file.exists())
				file.createNewFile();
			FileInputStream fileIn = new FileInputStream(namesPath);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			temp = (ArrayList<String>) in.readObject();
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

	private void remove(String item, ArrayList<String> array) {
		for (int i = 0; i < array.size(); i++) {
			if (array.get(i).contains(" ")) {
				array.remove(i);
			}
		}
	}
}
