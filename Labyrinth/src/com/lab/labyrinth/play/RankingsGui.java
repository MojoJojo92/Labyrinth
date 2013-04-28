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

import com.lab.labyrinth.input.InputHandler;
import com.lab.labyrinth.level.Level;
import com.lab.labyrinth.level.LevelSerialization;

public class RankingsGui extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static int WIDTH = 900;
	public static int HEIGHT = 650;
	private Thread thread;
	private boolean running = false;
	private JFrame frame;
	private Graphics g;
	private BufferedImage background,backBtn,arrowUp,arrowDown;
	private ArrayList<String> levelNames;
	private int nameIndex;
	private Level[] levels;

	public RankingsGui() {

		frame = new JFrame();
		frame.setTitle("Labyrinth");
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
		
		nameIndex = 0;
		loadLevelNames();
		loadLevels();
		loadImages();
		startCustomLevelsGui();
		clickCheck();
		
		

		frame.repaint();
	}

	public void startCustomLevelsGui() {
		running = true;
		thread = new Thread(this, "Ranking");
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


		renderBackBtn();
		renderArrows();
		renderList();

		g.dispose();
		bs.show();
	}

	private void renderList() {
		for (int i = 0; i < 2; i++) {
			g.setFont(new Font("New Times Roman", 4, 22));
			g.setColor(Color.orange);
			g.drawString("Level Name: " + levels[i + nameIndex].getName().substring(0, levels[i + nameIndex].getName().lastIndexOf("_")), 325, 120 + 180 * i);
			g.drawString("By: " + levels[i + nameIndex].getUsername(), 325, 145 + 180 * i);
			g.drawString("1st : " + levels[i + nameIndex].getRankings().get(0), 325, 180 + 180 * i);
			g.drawString("2nd: " + levels[i + nameIndex].getRankings().get(1), 325, 205 + 180 * i);
			g.drawString("3rd : " + levels[i + nameIndex].getRankings().get(2), 325, 230 + 180 * i);
		}
	}

	private void loadLevelNames() {

		levelNames = new ArrayList<String>();
		LevelSerialization serialize = new LevelSerialization();
		levelNames  = serialize.deserializeNames();
		for(int i=0;i<levelNames.size();i++) {
			System.out.println(levelNames.get(i));
		}
	}

	private void loadLevels() {
		levels = new Level[100];
		for(int i = 0; i < 100; i++)
			levels[i] = new Level("__m", " ", new int[0][0],0,0,0,0,0,0,0,0,0, new ArrayList<String>());
		for(int i = 0;i<levelNames.size();i++){
			LevelSerialization serialize = new LevelSerialization();
			levels[i] = serialize.deserializeLevel(levelNames.get(i)).get(0);	
		}

	}

	private void renderBackBtn() {
		if (mouseIn(305, 305 + backBtn.getWidth(), 548, 548 + backBtn.getHeight())) {
			g.drawImage(backBtn, 305, 548, backBtn.getWidth(), backBtn.getHeight(), null);
			if (InputHandler.MousePressed == 1) {
				clickCheck();
				new PlayMenuGui();
				frame.dispose();
				stopCustomLevelsGui();
			}
		}
	}

	private void renderArrows() {


		if (mouseIn(405, 405 + 100, 470, 470 + 50)) {
			g.drawImage(arrowUp, 405, 470, 100, 50, null);
			if (InputHandler.MousePressed == 1 && nameIndex<levelNames.size()-2) {

				nameIndex++;
				clickCheck();
			}

		}

		if (mouseIn(405, 405 + 100, 40, 40 + 50)) {
			g.drawImage(arrowDown, 405, 40, 100, 50, null);
			if (InputHandler.MousePressed == 1 && nameIndex > 0) {
				nameIndex--;
				clickCheck();
			}

		}


	}

	private void loadImages() {
		try {
			background = ImageIO.read(RankingsGui.class.getResource("/textures/rankingsBackground.png"));
			backBtn = ImageIO.read(RankingsGui.class.getResource("/textures/back.png"));
			arrowUp = ImageIO.read(RankingsGui.class.getResource("/create/arrow_d.png"));
			arrowDown = ImageIO.read(RankingsGui.class.getResource("/create/arrow_u.png"));

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
