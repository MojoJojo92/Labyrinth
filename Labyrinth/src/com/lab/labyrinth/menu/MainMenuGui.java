package com.lab.labyrinth.menu;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.lab.labyrinth.input.InputHandler;

public class MainMenuGui extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private static int WIDTH = 900;
	private static int HEIGHT = 650;
	private Thread thread;
	private boolean running = false;
	private JFrame frame;
	private Graphics g;
	private BufferedImage background, side;
	private double k = 0, j = 0;
	private int x, y;
	private PlayMenuGui playMenu;
	private LevelMenuGui levelMenu;
	private RankingsMenuGui rankingsMenu;
	private int choice;

	public MainMenuGui() {

		frame = new JFrame();
		frame.setTitle("Labyrinth");
		frame.setSize(new Dimension(WIDTH, HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.setLocationRelativeTo(null);
		// frame.setResizable(false);
		frame.setVisible(true);

		InputHandler input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);

		choice = 0;
		playMenu = new PlayMenuGui(this);
		levelMenu = new LevelMenuGui(this);
		rankingsMenu = new RankingsMenuGui(this);

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
			renderMenu();
	}

	private void renderMenu() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		k = k + 0.002;
		j = j + 0.001;
		x = (int) (Math.sin(k) * 350);
		y = (int) (Math.cos(j) * 280);

		g.drawImage(background, x - 350, y - 300, background.getWidth(), background.getHeight(), null);
		g.drawImage(side, 0, 0, side.getWidth() / 2, frame.getHeight(), null);
		g.drawImage(side, frame.getWidth() - side.getWidth() / 2, 0, side.getWidth() / 2, frame.getHeight(), null);

		if (choice == 0)
			playMenu.renderPlayMenu(g);
		if (choice == 1 || choice == 2)
			levelMenu.renderLevelMenu(g);
		if (choice == 3)
			rankingsMenu.renderRankingsMenu(g);

		g.dispose();
		bs.show();
	}

	private void loadImages() {
		try {
			background = ImageIO.read(MainMenuGui.class.getResource("/textures/background3.png"));
			side = ImageIO.read(MainMenuGui.class.getResource("/textures/side.png"));
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

	public JFrame getFrame() {
		return frame;
	}

	public int getChoice() {
		return choice;
	}

	public void setChoice(int choice) {
		this.choice = choice;
	}
}