package com.lab.labyrinth.play;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.lab.labyrinth.input.InputHandler;
import com.lab.labyrinth.launcher.LauncherGui;

public class PlayMenuGui extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static int WIDTH = 900;
	public static int HEIGHT = 650;
	private Thread thread;
	private boolean running = false;
	private JFrame frame;
	private Graphics g;
	private BufferedImage background, standardLvlBtn, customLvlBtn, rankingsBtn, backBtn;

	public PlayMenuGui() {
		
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
		loadImages();
		startPlayMenu();
		clickCheck();

		frame.repaint();
	}

	public void startPlayMenu() {
		running = true;
		thread = new Thread(this, "Play");
		thread.start();
	}

	public void stopPlayMenu() {
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

		renderStandardLevels();
		renderCustomLevels();
		renderRankings();
		renderBack();

		g.dispose();
		bs.show();
	}

	private void renderStandardLevels() {
		if (mouseIn(305, 305 + standardLvlBtn.getWidth(), 243, 243 + standardLvlBtn.getHeight())) {
			g.drawImage(standardLvlBtn, 305, 243, standardLvlBtn.getWidth(), standardLvlBtn.getHeight(), null);
			if (InputHandler.MousePressed == 1) {
				clickCheck();
				new LevelMenuGui(1);	
				frame.dispose();
				stopPlayMenu();
			}
		}
	}

	private void renderCustomLevels() {
		if (mouseIn(305, 305 + customLvlBtn.getWidth(), 315, 315 + customLvlBtn.getHeight())) {
			g.drawImage(customLvlBtn, 305, 315, customLvlBtn.getWidth(), customLvlBtn.getHeight(), null);
			if (InputHandler.MousePressed == 1) {
				clickCheck();
				new LevelMenuGui(2);	
				frame.dispose();
				stopPlayMenu();
			}
		}
	}

	private void renderRankings() {
		if (mouseIn(305, 305 + rankingsBtn.getWidth(), 315+72, 315+72 + rankingsBtn.getHeight())) {
			g.drawImage(rankingsBtn, 305, 315+72, rankingsBtn.getWidth(), rankingsBtn.getHeight(), null);
			if (InputHandler.MousePressed == 1) {
				clickCheck();
				new RankingsGui();	
				frame.dispose();
				stopPlayMenu();
				
			}
		}
	}

	private void renderBack() {
		if (mouseIn(305, 305 + backBtn.getWidth(), 315+144, 315+144 + backBtn.getHeight())) {
			g.drawImage(backBtn, 305,315+144, backBtn.getWidth(), backBtn.getHeight(), null);
			if (InputHandler.MousePressed == 1) {
				clickCheck();
				new LauncherGui();
				stopPlayMenu();
				frame.dispose();
				
			}
		}
	}

	private void loadImages() {
		try {
			background = ImageIO.read(PlayMenuGui.class.getResource("/textures/inGameMenu.png"));
			standardLvlBtn = ImageIO.read(PlayMenuGui.class.getResource("/textures/standardLevels.png"));
			customLvlBtn = ImageIO.read(PlayMenuGui.class.getResource("/textures/customLevels.png"));
			rankingsBtn = ImageIO.read(PlayMenuGui.class.getResource("/textures/rankings.png"));
			backBtn = ImageIO.read(PlayMenuGui.class.getResource("/textures/back.png"));
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
