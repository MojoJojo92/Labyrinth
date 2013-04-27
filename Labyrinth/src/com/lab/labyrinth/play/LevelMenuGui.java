package com.lab.labyrinth.play;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.lab.labyrinth.graphics.Display;
import com.lab.labyrinth.input.InputHandler;
import com.lab.labyrinth.level.Level;
import com.lab.labyrinth.level.LevelSerialization;

public class LevelMenuGui extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static int WIDTH = 900;
	public static int HEIGHT = 650;
	private Thread thread;
	private boolean running = false;
	private JFrame frame;
	private Graphics g;
	private BufferedImage background, playBtn, backBtn, arrowUp, arrowDown, select;
	private String username;
	private int choice;
	private String[] levelNames;
	private ArrayList<String> nameList;
	private ArrayList<Level> level;
	private int nameIndex, selectedIndex;
	private boolean[] selected;

	public LevelMenuGui(String username, int choice) {
		this.username = username;
		this.choice = choice;

		frame = new JFrame();
		frame.setTitle("Labyrinth");
		frame.setSize(new Dimension(WIDTH, HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

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
		if (this.choice == 1) {
			
		} else {
			updateCustomNames();
		}
		loadImages();
		startCustomLevelsGui();
		clickCheck();

		frame.repaint();
	}

	public void startCustomLevelsGui() {
		running = true;
		thread = new Thread(this, "Custom");
		thread.start();
	}

	public void stopCustomLevelsGui() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (running)
			renderPlayMenu();
	}

	private void renderPlayMenu() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);

		renderPlayBtn();
		renderBackBtn();
		renderArrows();
		renderList();

		g.dispose();
		bs.show();
	}

	private void renderPlayBtn() {
		if (mouseIn(305, 305 + playBtn.getWidth(), 500, 500 + playBtn.getHeight())) {
			g.drawImage(playBtn, 305, 500, playBtn.getWidth(), playBtn.getHeight(), null);
			if (InputHandler.MousePressed == 1) {
				clickCheck();
				LevelSerialization serialize = new LevelSerialization();
				level = serialize.deserializeLevel(levelNames[selectedIndex]);
				new Display(level.get(0));
				frame.dispose();
				stopCustomLevelsGui();

			}
		}
	}

	private void renderBackBtn() {
		if (mouseIn(305, 305 + backBtn.getWidth(), 560, 560 + backBtn.getHeight())) {
			g.drawImage(backBtn, 305, 560, backBtn.getWidth(), backBtn.getHeight(), null);
			if (InputHandler.MousePressed == 1) {
				clickCheck();
				new PlayMenuGui(username);
				frame.dispose();
				stopCustomLevelsGui();
			}
		}
	}

	private void renderArrows() {
		if (mouseIn(405, 405 + 100, 435, 435 + 50)) {
			g.drawImage(arrowUp, 405, 435, 100, 50, null);
			if (InputHandler.MousePressed == 1 && nameIndex < 100) {
				nameIndex++;
				clickCheck();
			}
		}
		if (mouseIn(410, 410 + 100, 20, 20 + 50)) {
			g.drawImage(arrowDown, 410, 20, 100, 50, null);
			if (InputHandler.MousePressed == 1 && nameIndex > 0) {
				nameIndex--;
				clickCheck();
			}
		}
	}

	private void renderList() {
		for (int i = 0; i < 6; i++) {
			g.setFont(new Font("New Times Roman", 4, 22));
			g.setColor(Color.orange);
			g.drawString(levelNames[nameIndex + i], 420, 110 + 62 * i);
		}
		renderSelect();
	}

	private void renderSelect() {
		if (mouseIn(320, 320 + 275, 68, 68 + 65)) {
			g.drawImage(select, 320, 68, 275, 65, null);
			selected(nameIndex + 0);
		} else if (selected[nameIndex + 0]) {
			g.drawImage(select, 320, 68, 275, 65, null);
		}
		if (mouseIn(320, 320 + 275, 130, 130 + 64)) {
			g.drawImage(select, 320, 130, 275, 64, null);
			selected(nameIndex + 1);
		} else if (selected[nameIndex + 1]) {
			g.drawImage(select, 320, 130, 275, 64, null);
		}
		if (mouseIn(320, 320 + 275, 190, 190 + 63)) {
			g.drawImage(select, 320, 190, 275, 63, null);
			selected(nameIndex + 2);
		} else if (selected[nameIndex + 2]) {
			g.drawImage(select, 320, 190, 275, 63, null);
		}
		if (mouseIn(320, 320 + 275, 249, 249 + 65)) {
			g.drawImage(select, 320, 249, 275, 65, null);
			selected(nameIndex + 3);
		} else if (selected[nameIndex + 3]) {
			g.drawImage(select, 320, 249, 275, 65, null);
		}
		if (mouseIn(320, 320 + 275, 313, 313 + 63)) {
			g.drawImage(select, 320, 313, 275, 63, null);
			selected(nameIndex + 4);
		} else if (selected[nameIndex + 4]) {
			g.drawImage(select, 320, 313, 275, 63, null);
		}
		if (mouseIn(320, 320 + 275, 374, 374 + 65)) {
			g.drawImage(select, 320, 374, 275, 65, null);
			selected(nameIndex + 5);
		} else if (selected[nameIndex + 5]) {
			g.drawImage(select, 320, 374, 275, 65, null);
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
			background = ImageIO.read(LevelMenuGui.class.getResource("/textures/customBackground.png"));
			playBtn = ImageIO.read(LevelMenuGui.class.getResource("/textures/play.png"));
			backBtn = ImageIO.read(LevelMenuGui.class.getResource("/textures/back.png"));
			arrowUp = ImageIO.read(LevelMenuGui.class.getResource("/create/arrow_d.png"));
			arrowDown = ImageIO.read(LevelMenuGui.class.getResource("/create/arrow_u.png"));
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
		for (int i = 0; i < 100; i++) {
			levelNames[i] = " ";
		}
		LevelSerialization serialize = new LevelSerialization();
		nameList = serialize.deserializeNames();
		for (int i = 0; i < nameList.size(); i++)
			levelNames[i] = nameList.get(i);
	}
}
