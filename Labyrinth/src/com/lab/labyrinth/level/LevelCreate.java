package com.lab.labyrinth.level;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.lab.labyrinth.account.AccountGui;
import com.lab.labyrinth.input.InputHandler;
import com.lab.labyrinth.launcher.LauncherGui;

public class LevelCreate extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private static int WIDTH = 990, HEIGHT = 640;
	private JFrame frame;
	private Thread thread;
	private BufferedImage mainGui, wall, player, border, saveBtn, arrowL, arrowR, arrowU, arrowD, doneBtn, newBtn, deleteBtn, select, finish;
	private Graphics g;
	private String[] names;
	private ArrayList<String> nameList;
	private boolean[] selected;
	private boolean running = false, draw = false, go = true;
	private int nameIndex, selectedIndex;

	private String levelName;
	private int lvlWidth, lvlHeight, spawnX, spawnY, blocks, minTime, secTime, minBest, secBest;
	private int[][] flag;
	private ArrayList<String> rankings;

	public LevelCreate() {
		frame = new JFrame();
		frame.setTitle("Custom Level");
		frame.setSize(new Dimension(WIDTH, HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		InputHandler input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);

		flag = new int[500][500];
		names = new String[100];
		selected = new boolean[100];
		for (int i = 0; i < 100; i++) {
			names[i] = " ";
			selected[i] = false;
		}
		nameList = new ArrayList<String>();
		rankings = new ArrayList<String>();
		blocks = 0;
		nameIndex = 0;
		selectedIndex = 0;

		updateNames();
		loadImages();
		clickCheck();
		startCreate();

		frame.repaint();
	}

	public void startCreate() {
		running = true;
		thread = new Thread(this, "Create");
		thread.start();
	}

	public void stopCreate() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (running)
			renderCreate();
	}

	private void renderCreate() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		g = bs.getDrawGraphics();
		g.drawImage(mainGui, 0, 0, getWidth(), getHeight(), null);

		renderSaveBtn();
		renderDoneBtn();
		renderNewBtn();
		renderDeleteBtn();
		renderList();
		renderArrows();
		if (draw) {
			renderMap();
			renderNumbers();
		}
		g.dispose();
		bs.show();
	}

	private void renderMap() {
		for (int i = spawnX; i < spawnX + 20; i++)
			for (int j = spawnY; j < spawnY + 20; j++)
				renderSquare(i, j);
	}

	private void renderSaveBtn() {
		if (mouseIn(50, 50 + saveBtn.getWidth(), 480, 480 + saveBtn.getHeight())) {
			g.drawImage(saveBtn, 41, 473, saveBtn.getWidth(), saveBtn.getHeight(), null);
			if (draw)
				saveBtnListener();
		}
	}

	private void renderDoneBtn() {
		if (mouseIn(41, 41 + doneBtn.getWidth(), 520, 520 + doneBtn.getHeight())) {
			g.drawImage(doneBtn, 41, 518, doneBtn.getWidth(), doneBtn.getHeight(), null);
			if (InputHandler.MousePressed == 1) {
				clickCheck();
				frame.dispose();
				new LauncherGui();
				stopCreate();
			}
		}
	}

	private void renderNewBtn() {
		if (mouseIn(50, 50 + newBtn.getWidth(), 422, 422 + newBtn.getHeight())) {
			g.drawImage(newBtn, 42, 422, newBtn.getWidth(), newBtn.getHeight(), null);
			newBtnListener();
		}
	}

	private void renderDeleteBtn() {
		if (mouseIn(210, 210 + deleteBtn.getWidth(), 422, 422 + deleteBtn.getHeight())) {
			g.drawImage(deleteBtn, 202, 422, deleteBtn.getWidth(), deleteBtn.getHeight(), null);
			deleteBtnListener();
		}
	}

	private void renderList() {
		for (int i = 0; i < 9; i++) {
			g.setFont(new Font("New Times Roman", 4, 20));
			g.setColor(Color.orange);
			g.drawString(names[nameIndex + i], 65, 72 + 36 * i);
		}
		renderSelect();
	}

	private void renderArrows() {
		renderUpArrow();
		renderDownArrow();
		renderLeftArrow();
		renderRightArrow();
		renderUpListArrow();
		renderDownListArrow();
	}

	private void renderUpArrow() {
		if (mouseIn(651, 651 + 55, 23, 23 + 20)) {
			g.drawImage(arrowU, 643, 15, 70, 35, null);
			if (InputHandler.MousePressed == 1)
				if (spawnY > 0)
					spawnY--;
		}
	}

	private void renderDownArrow() {
		if (mouseIn(651, 651 + 55, 573, 573 + 20)) {
			g.drawImage(arrowD, 643, 566, 70, 35, null);
			if (InputHandler.MousePressed == 1)
				if (spawnY < lvlHeight - 20)
					spawnY++;
		}
	}

	private void renderLeftArrow() {
		if (mouseIn(390, 390 + 20, 281, 281 + 55)) {
			g.drawImage(arrowL, 385, 275, 35, 70, null);
			if (InputHandler.MousePressed == 1)
				if (spawnX > 0)
					spawnX--;
		}
	}

	private void renderRightArrow() {
		if (mouseIn(950, 950 + 20, 281, 281 + 55)) {
			g.drawImage(arrowR, 940, 275, 35, 70, null);
			if (InputHandler.MousePressed == 1)
				if (spawnX < lvlWidth - 20)
					spawnX++;
		}
	}

	private void renderUpListArrow() {
		if (mouseIn(89, 88 + 55, 24, 24 + 20)) {
			g.drawImage(arrowU, 81, 15, 70, 35, null);
			if (InputHandler.MousePressed == 1) {
				if (nameIndex > 0) {
					nameIndex--;
					clickCheck();
				}
			}
		}
	}

	private void renderDownListArrow() {
		if (mouseIn(89, 88 + 55, 365, 365 + 20)) {
			g.drawImage(arrowD, 81, 365, 70, 35, null);
			if (InputHandler.MousePressed == 1) {
				if (nameIndex < 99) {
					nameIndex++;
					clickCheck();
				}
			}
		}
	}

	private void renderNumbers() {
		g.setFont(new Font("Verdana", 1, 12));
		g.setColor(Color.orange);
		g.drawString(Integer.toString(spawnX - Math.abs(lvlWidth / 2)), 385, 70);
		g.drawString(Integer.toString(spawnX - Math.abs(lvlWidth / 2) + 20), 945, 70);
		g.drawString(Integer.toString(spawnX - Math.abs(lvlWidth / 2)), 385, 560);
		g.drawString(Integer.toString(spawnX - Math.abs(lvlWidth / 2) + 20), 945, 560);
		g.drawString(Integer.toString(spawnY - Math.abs(lvlHeight / 2)), 420, 40);
		g.drawString(Integer.toString(spawnY - Math.abs(lvlHeight / 2) + 20), 420, 590);
		g.drawString(Integer.toString(spawnY - Math.abs(lvlHeight / 2)), 915, 40);
		g.drawString(Integer.toString(spawnY - Math.abs(lvlHeight / 2) + 20), 915, 590);
		g.setFont(new Font("Verdana", 1, 18));
		g.drawString(Integer.toString(blocks), 260, 260);
		g.drawString(Integer.toString(minTime), 245, 100);
		g.drawString(Integer.toString(secTime), 315, 100);
		g.drawString(Integer.toString(minBest), 245, 180);
		g.drawString(Integer.toString(secBest), 315, 180);
	}

	private void renderSelect() {
		for (int i = 0; i < 9; i++) {
			if (mouseIn(48, 48 + 138, 48 + 36 * i, 48 + 36 * (i + 1))) {
				g.drawImage(select, 48, 48 + 36 * i, 138, 36, null);
				selected(nameIndex + i);

			} else if (selected[nameIndex + i]) {
				g.drawImage(select, 48, 48 + 36 * i, 138, 36, null);
			}
		}
	}

	private void renderSquare(int i, int j) {
		if (flag[i][j] == 2)
			g.drawImage(border, 417 + (i - spawnX) * 26, 47 + (j - spawnY) * 26, wall.getWidth() - 43, wall.getHeight() - 43, null);
		else if (flag[i][j] == 3)
			g.drawImage(player, 419 + (i - spawnX) * 26, 49 + (j - spawnY) * 26, wall.getWidth() - 46, wall.getHeight() - 47, null);
		else if (drawFinish(i, j))
			g.drawImage(finish, 419 + (i - spawnX) * 26, 49 + (j - spawnY) * 26, wall.getWidth() - 46, wall.getHeight() - 47, null);
		else if (drawButton(i, j))
			g.drawImage(wall, 414 + (i - spawnX) * 26, 44 + (j - spawnY) * 26, wall.getWidth() - 37, wall.getHeight() - 37, null);
	}

	private boolean drawButton(int i, int j) {
		if (mouseIn(414 + (i - spawnX) * 26, 414 + ((i - spawnX) + 1) * 26, 44 + (j - spawnY) * 26, 44 + ((j - spawnY) + 1) * 26)) {
			if (InputHandler.MousePressed == 1) {
				if (flag[i][j] == 1) {
					flag[i][j] = 0;
					blocks--;
					clickCheck();
					return false;
				} else if (flag[i][j] == 0) {
					flag[i][j] = 1;
					blocks++;
					clickCheck();
					return true;
				}
			} else if (flag[i][j] == 0) {
				return true;
			}
		} else if (flag[i][j] == 1) {
			return true;
		}
		return false;
	}

	private boolean drawFinish(int i, int j) {
		if (mouseIn(414 + (i - spawnX) * 26, 414 + ((i - spawnX) + 1) * 26, 44 + (j - spawnY) * 26, 44 + ((j - spawnY) + 1) * 26)) {
			if (InputHandler.MousePressed == 3) {
				if (flag[i][j] == 4) {
					flag[i][j] = 0;
					go = true;
					clickCheck();
					return false;
				} else if (flag[i][j] == 0 && go) {
					flag[i][j] = 4;
					go = false;
					clickCheck();
					return true;
				}
			} else if (flag[i][j] == 4) {
				return true;
			}
		} else if (flag[i][j] == 4) {
			return true;
		}
		return false;
	}

	private boolean mouseIn(int xS, int xF, int yS, int yF) {
		if (InputHandler.MouseX <= xS)
			return false;
		if (InputHandler.MouseX >= xF)
			return false;
		if (InputHandler.MouseY <= yS)
			return false;
		if (InputHandler.MouseY >= yF)
			return false;
		return true;
	}

	private void newBtnListener() {
		if (InputHandler.MousePressed == 1) {
			flag = new int[500][500];
			new LevelSettingsGui(this);
			clickCheck();
			selected[selectedIndex] = false;
		}
	}

	private void saveBtnListener() {
		if (InputHandler.MousePressed == 1) {
			clickCheck();
			if (contains(4)) {
				File file = new File("res/levels/" + levelName + "_" + AccountGui.Username + ".ser");
				if (file.exists())
					file.delete();
				Level level = new Level(AccountGui.Username, levelName, flag, minTime, secTime, minBest, secBest, blocks, spawnX, spawnY, lvlWidth, lvlHeight, rankings);
				new LevelSerialization(level);
				updateNames();
				selected(lastName());
			} else {
				JOptionPane.showMessageDialog(frame, "Must add a finish block (right click)", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void deleteBtnListener() {
		if (InputHandler.MousePressed == 1) {
			File file = new File("res/levels/" + names[selectedIndex] + "_" + AccountGui.Username + ".ser");
			file.delete();
			nameList.remove(names[selectedIndex] + "_" + AccountGui.Username);
			LevelSerialization serialize = new LevelSerialization();
			serialize.serializeNames(nameList);
			selected[selectedIndex] = false;
			updateNames();
			clickCheck();
		}
	}

	private void loadImages() {
		try {
			mainGui = ImageIO.read(LevelCreate.class.getResource("/create/background.png"));
			wall = ImageIO.read(LevelCreate.class.getResource("/create/wall.png"));
			player = ImageIO.read(LevelCreate.class.getResource("/create/player.png"));
			border = ImageIO.read(LevelCreate.class.getResource("/create/border.png"));
			saveBtn = ImageIO.read(LevelCreate.class.getResource("/create/save.png"));
			arrowR = ImageIO.read(LevelCreate.class.getResource("/create/arrow_r.png"));
			arrowU = ImageIO.read(LevelCreate.class.getResource("/create/arrow_u.png"));
			arrowD = ImageIO.read(LevelCreate.class.getResource("/create/arrow_d.png"));
			arrowL = ImageIO.read(LevelCreate.class.getResource("/create/arrow_l.png"));
			doneBtn = ImageIO.read(LevelCreate.class.getResource("/create/done.png"));
			newBtn = ImageIO.read(LevelCreate.class.getResource("/create/new.png"));
			deleteBtn = ImageIO.read(LevelCreate.class.getResource("/create/delete.png"));
			select = ImageIO.read(LevelCreate.class.getResource("/create/select.png"));
			finish = ImageIO.read(LevelCreate.class.getResource("/create/finish.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void clickCheck() {
		if (InputHandler.MousePressed == 1)
			InputHandler.MousePressed = -1;
		if (InputHandler.MousePressed == 3)
			InputHandler.MousePressed = -1;
	}

	private void selected(int selectedIndex) {
		if (InputHandler.MousePressed == 1 && names[selectedIndex] != " ") {
			selected[this.selectedIndex] = false;
			selected[selectedIndex] = true;
			this.selectedIndex = selectedIndex;
			updateMap();
			clickCheck();
		}
	}

	private void updateNames() {
		int j = 0;
		for (int i = 0; i < 99; i++) {
			names[i] = " ";
		}
		LevelSerialization serialize = new LevelSerialization();
		nameList = serialize.deserializeNames();
		for (int i = 0; i < nameList.size(); i++)
			if (nameList.get(i).endsWith("_" + AccountGui.Username)) {
				names[j] = nameList.get(i).substring(0, nameList.get(i).lastIndexOf("_"));
				j++;
			}
	}

	private void updateMap() {
		LevelSerialization serialize = new LevelSerialization();
		ArrayList<Level> level = serialize.deserializeLevel(names[selectedIndex] + "_" + AccountGui.Username);
		flag = level.get(0).getFlag();
		levelName = level.get(0).getName();
		minTime = level.get(0).getMinTimeLimit();
		secTime = level.get(0).getSecTimeLimit();
		minBest = level.get(0).getMinBestTime();
		secBest = level.get(0).getSecBestTime();
		blocks = level.get(0).getBlocks();
		spawnX = level.get(0).getSpawnX();
		spawnY = level.get(0).getSpawnY();
		lvlWidth = level.get(0).getLvlWidth();
		lvlHeight = level.get(0).getLvlHeight();
		draw = true;
		if (contains(4))
			go = false;
		else
			go = true;
	}

	private int lastName() {
		for (int i = 0; i < 100; i++)
			if (names[i] == " ")
				return i - 1;
		return -1;
	}

	private boolean contains(int item) {
		for (int i = 0; i < lvlWidth; i++) {
			for (int j = 0; j < lvlHeight; j++) {
				if (flag[i][j] == item)
					return true;
			}
		}
		return false;
	}

	public void setLvlWidth(int lvlWidth) {
		this.lvlWidth = lvlWidth;
	}

	public void setLvlHeight(int lvlHeight) {
		this.lvlHeight = lvlHeight;
	}

	public void setSpawnX(int spawnX) {
		this.spawnX = spawnX;
	}

	public void setSpawnY(int spawnY) {
		this.spawnY = spawnY;
	}

	public void setDraw(boolean draw) {
		this.draw = draw;
	}

	public void setFlag(int[][] flag) {
		this.flag = flag;
	}

	public void setMinTime(int min) {
		this.minTime = min;
	}

	public void setSecTime(int sec) {
		this.secTime = sec;
	}

	public void setMinBest(int minBest) {
		this.minBest = minBest;
	}

	public void setSecBest(int secBest) {
		this.secBest = secBest;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	public void setGo(boolean go) {
		this.go = go;
	}

	public void setRankings(ArrayList<String> rankings) {
		this.rankings = rankings;
	}
}
