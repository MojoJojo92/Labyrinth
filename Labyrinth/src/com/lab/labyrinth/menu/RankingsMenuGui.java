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

import com.lab.labyrinth.input.InputHandler;
import com.lab.labyrinth.level.Level;
import com.lab.labyrinth.level.LevelSerialization;

public class RankingsMenuGui extends Canvas {

	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private BufferedImage backBtnOn, backBtnOff, arrowUpOn, arrowUpOff, arrowDownOn, arrowDownOff, listImg;
	private ArrayList<String> levelNames;
	private int nameIndex;
	private Level[] levels;
	private MainMenuGui menu;

	public RankingsMenuGui(MainMenuGui menu) {
		this.menu = menu;
		frame = this.menu.getFrame();

		InputHandler input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);

		nameIndex = 0;
		loadLevelNames();
		loadLevels();
		loadImages();
		clickCheck();
	}

	public void renderRankingsMenu(Graphics g) {
		g.setFont(new Font("New Times Roman", 4, 22));
		g.setColor(Color.orange);
		renderBackBtn(g);
		renderArrows(g);
		renderList(g);
	}

	private void renderList(Graphics g) {
		g.drawImage(listImg, frame.getWidth() / 2 - listImg.getWidth() / 2, 90, listImg.getWidth(), listImg.getHeight(), null);
		for (int i = 0; i < 2; i++) {
			g.drawString("Level Name: " + levels[i + nameIndex].getName().substring(0, levels[i + nameIndex].getName().lastIndexOf("_")), frame.getWidth() / 2 - ("Level Name: " + levels[i + nameIndex].getName().substring(0, levels[i + nameIndex].getName().lastIndexOf("_"))).length() * 11 / 2, 130 + 180 * i);
			g.drawString("By: " + levels[i + nameIndex].getUsername(), frame.getWidth() / 2 - ("By: " + levels[i + nameIndex].getUsername()).length() * 11 / 2, 155 + 180 * i);
			g.drawString("1st: " + levels[i + nameIndex].getRankings().get(0), frame.getWidth() / 2 - ("1st: " + levels[i + nameIndex].getRankings().get(0)).length() * 11 / 2, 195 + 180 * i);
			g.drawString("2nd: " + levels[i + nameIndex].getRankings().get(1), frame.getWidth() / 2 - ("2nd: " + levels[i + nameIndex].getRankings().get(1)).length() * 11 / 2, 220 + 180 * i);
			g.drawString("3rd: " + levels[i + nameIndex].getRankings().get(2), frame.getWidth() / 2 - ("3rd: " + levels[i + nameIndex].getRankings().get(2)).length() * 11 / 2, 245 + 180 * i);
		}
	}

	private void loadLevelNames() {
		levelNames = new ArrayList<String>();
		LevelSerialization serialize = new LevelSerialization();
		levelNames = serialize.deserializeNames();
		for (int i = 0; i < levelNames.size(); i++) {
			System.out.println(levelNames.get(i));
		}
	}

	private void loadLevels() {
		levels = new Level[100];
		for (int i = 0; i < 100; i++)
			levels[i] = new Level("__m", " ", new int[0][0], 0, 0, 0, 0, 0, 0, 0, 0, 0, new ArrayList<String>());
		for (int i = 0; i < levelNames.size(); i++) {
			LevelSerialization serialize = new LevelSerialization();
			levels[i] = serialize.deserializeLevel(levelNames.get(i)).get(0);
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

		if (mouseIn(frame.getWidth() / 2 - arrowDownOn.getWidth() / 2 / 2, frame.getWidth() / 2 - arrowDownOn.getWidth() / 2 / 2 + arrowDownOn.getWidth() / 2, 460, 460 + arrowDownOn.getHeight() / 2)) {
			g.drawImage(arrowDownOn, frame.getWidth() / 2 - arrowDownOn.getWidth() / 2 / 2, 460, arrowDownOn.getWidth() / 2, arrowDownOn.getHeight() / 2, null);
			if (InputHandler.MousePressed == 1 && nameIndex < levelNames.size() - 2) {
				nameIndex++;
				clickCheck();
			}
		} else {
			g.drawImage(arrowDownOff, frame.getWidth() / 2 - arrowDownOff.getWidth() / 2 / 2, 465, arrowDownOff.getWidth() / 2, arrowDownOff.getHeight() / 2, null);
		}
	}

	private void loadImages() {
		try {
			backBtnOn = ImageIO.read(RankingsMenuGui.class.getResource("/textures/back.png"));
			backBtnOff = ImageIO.read(RankingsMenuGui.class.getResource("/textures/backOff.png"));
			arrowUpOn = ImageIO.read(RankingsMenuGui.class.getResource("/textures/arrowUpOn.png"));
			arrowUpOff = ImageIO.read(RankingsMenuGui.class.getResource("/textures/arrowUp.png"));
			arrowDownOn = ImageIO.read(RankingsMenuGui.class.getResource("/textures/arrowDownOn.png"));
			arrowDownOff = ImageIO.read(RankingsMenuGui.class.getResource("/textures/arrowDown.png"));
			listImg = ImageIO.read(RankingsMenuGui.class.getResource("/textures/rankingList.png"));
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
}
