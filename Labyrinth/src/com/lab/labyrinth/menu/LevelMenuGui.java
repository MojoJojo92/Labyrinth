package com.lab.labyrinth.menu;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.lab.labyrinth.graphics.Display;
import com.lab.labyrinth.input.InputHandler;
import com.lab.labyrinth.level.Level;
import com.lab.labyrinth.level.LevelSerialization;

public class LevelMenuGui extends Canvas {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private BufferedImage playBtnOn, playBtnOff, backBtnOn, backBtnOff, arrowUpOn, arrowUpOff, arrowDownOn, arrowDownOff, select, levelList;
	private String[] levelNames;
	private ArrayList<String> nameList;
	private ArrayList<Level> level;
	private int nameIndex, selectedIndex;
	private boolean[] selected;
	private MainMenuGui menu;

	public LevelMenuGui(MainMenuGui menu, int choice) {
		this.menu = menu;
		frame = this.menu.getFrame();

		levelNames = new String[100];
		selected = new boolean[100];
		for (int i = 0; i < 100; i++) {
			levelNames[i] = " ";
			selected[i] = false;
		}
		nameList = new ArrayList<String>();
		nameIndex = 0;
		selectedIndex = 0;

		InputHandler input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);

		if (choice == 1)
			updateStandardNames();
		else if (choice == 2)
			updateCustomNames();

		loadImages();
		clickCheck();

		frame.repaint();
	}

	public void renderLevelMenu(Graphics g) {
		renderPlayBtn(g);
		renderBackBtn(g);
		renderArrows(g);
		renderList(g);
	}

	private void renderPlayBtn(Graphics g) {
		if (mouseIn(frame.getWidth() / 2 - playBtnOn.getWidth() / 2, frame.getWidth() / 2 - playBtnOn.getWidth() / 2 + playBtnOn.getWidth(), 430, 430 + playBtnOn.getHeight())) {
			g.drawImage(playBtnOn, frame.getWidth() / 2 - playBtnOn.getWidth() / 2, 430, playBtnOn.getWidth(), playBtnOn.getHeight(), null);
			if (InputHandler.MousePressed == 1) {
				clickCheck();
				LevelSerialization serialize = new LevelSerialization();
				level = serialize.deserializeLevel(levelNames[selectedIndex]);
				new Display(level.get(0));
				frame.dispose();
				menu.stopPlayMenu();
			}
		} else {
			g.drawImage(playBtnOff, frame.getWidth() / 2 - playBtnOff.getWidth() / 2, 435, playBtnOff.getWidth(), playBtnOff.getHeight(), null);
		}
	}

	private void renderBackBtn(Graphics g) {
		if (mouseIn(frame.getWidth() / 2 - backBtnOn.getWidth() / 2, frame.getWidth() / 2 - backBtnOn.getWidth() / 2 + backBtnOn.getWidth(), 500, 500 + backBtnOn.getHeight())) {
			g.drawImage(backBtnOn, frame.getWidth() / 2 - backBtnOn.getWidth() / 2, 500, backBtnOn.getWidth(), backBtnOn.getHeight(), null);
			if (InputHandler.MousePressed == 1) {
				clickCheck();
				menu.setChoice(0);
			}
		} else {
			g.drawImage(backBtnOff, frame.getWidth() / 2 - backBtnOff.getWidth() / 2, 505, backBtnOff.getWidth(), backBtnOff.getHeight(), null);
		}
	}

	private void renderArrows(Graphics g) {
		if (mouseIn(frame.getWidth() / 2 - arrowUpOn.getWidth() / 2 / 2, frame.getWidth() / 2 - arrowUpOn.getWidth() / 2 / 2 + arrowUpOn.getWidth() / 2, 50, 50 + arrowUpOn.getHeight() / 2)) {
			g.drawImage(arrowUpOn, frame.getWidth() / 2 - arrowUpOn.getWidth() / 2 / 2, 50, arrowUpOn.getWidth() / 2, arrowUpOn.getHeight() / 2, null);
			if (InputHandler.MousePressed == 1 && nameIndex > 0) {
				nameIndex--;
				clickCheck();
			}
		} else {
			g.drawImage(arrowUpOff, frame.getWidth() / 2 - arrowUpOff.getWidth() / 2 / 2, 55, arrowUpOff.getWidth() / 2, arrowUpOff.getHeight() / 2, null);
		}

		if (mouseIn(frame.getWidth() / 2 - arrowDownOn.getWidth() / 2 / 2, frame.getWidth() / 2 - arrowDownOn.getWidth() / 2 / 2 + arrowDownOn.getWidth() / 2, 390, 390 + arrowDownOn.getHeight() / 2)) {
			g.drawImage(arrowDownOn, frame.getWidth() / 2 - arrowDownOn.getWidth() / 2 / 2, 390, arrowDownOn.getWidth() / 2, arrowDownOn.getHeight() / 2, null);
			if (InputHandler.MousePressed == 1 && nameIndex < 100) {
				nameIndex++;
				clickCheck();
			}
		} else {
			g.drawImage(arrowDownOff, frame.getWidth() / 2 - arrowDownOff.getWidth() / 2 / 2, 395, arrowDownOff.getWidth() / 2, arrowDownOff.getHeight() / 2, null);
		}
	}

	private void renderList(Graphics g) {
		g.drawImage(levelList, frame.getWidth() / 2 - levelList.getWidth() / 2 / 2, 90, levelList.getWidth() / 2, levelList.getHeight() / 2, null);
		for (int i = 0; i < 6; i++) {
			g.setFont(new Font("New Times Roman", 4, 22));
			g.setColor(Color.orange);
			g.drawString(levelNames[nameIndex + i], frame.getWidth() / 2 - levelNames[nameIndex + i].length() * 11 / 2, 122 + 48 * i);
		}
		renderSelect(g);
	}

	private void renderSelect(Graphics g) {
		for (int i = 0; i < 6; i++) {
			if (mouseIn(frame.getWidth() / 2 - 268 / 2, frame.getWidth() / 2 - 268 / 2 + 268, 91 + 48 * i, 91 + (48 * (i + 1)))) {
				g.drawImage(select, frame.getWidth() / 2 - 268 / 2, 91 + 48 * i, 268, 48, null);
				selected(nameIndex + i);
			} else if (selected[nameIndex + i]) {
				g.drawImage(select, frame.getWidth() / 2 - 268 / 2, 91 + 48 * i, 268, 48, null);
			}
		}
	}

	private void selected(int selectedIndex) {
		if (InputHandler.MousePressed == 1 && levelNames[selectedIndex] != " ") {
			selected[this.selectedIndex] = false;
			selected[selectedIndex] = true;
			this.selectedIndex = selectedIndex;
		}
	}

	private void loadImages() {
		try {
			playBtnOn = ImageIO.read(LevelMenuGui.class.getResource("/textures/play.png"));
			playBtnOff = ImageIO.read(LevelMenuGui.class.getResource("/textures/playOff.png"));
			backBtnOn = ImageIO.read(LevelMenuGui.class.getResource("/textures/back.png"));
			backBtnOff = ImageIO.read(LevelMenuGui.class.getResource("/textures/backOff.png"));
			arrowUpOn = ImageIO.read(LevelMenuGui.class.getResource("/textures/arrowUpOn.png"));
			arrowUpOff = ImageIO.read(LevelMenuGui.class.getResource("/textures/arrowUp.png"));
			arrowDownOn = ImageIO.read(LevelMenuGui.class.getResource("/textures/arrowDownOn.png"));
			arrowDownOff = ImageIO.read(LevelMenuGui.class.getResource("/textures/arrowDown.png"));
			levelList = ImageIO.read(LevelMenuGui.class.getResource("/textures/levelList.png"));
			select = ImageIO.read(LevelMenuGui.class.getResource("/create/select.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
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

	private void clickCheck() {
		if (InputHandler.MousePressed == 1)
			InputHandler.MousePressed = -1;
		if (InputHandler.MousePressed == 3)
			InputHandler.MousePressed = -1;
	}

	private void updateCustomNames() {
		for (int i = 0; i < 100; i++)
			levelNames[i] = " ";
		LevelSerialization serialize = new LevelSerialization();
		nameList = serialize.deserializeNames();
		for (int i = 0; i < nameList.size(); i++)
			levelNames[i] = nameList.get(i);
	}

	private void updateStandardNames() {
		for (int i = 0; i < 100; i++)
			levelNames[i] = " ";
		levelNames[0] = "Easy";
	}
}
