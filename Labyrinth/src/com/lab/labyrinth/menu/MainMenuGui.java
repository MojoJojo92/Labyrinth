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
	private int WIDTH = 900, HEIGHT = 650;
	private Thread thread;
	private boolean running = false;
	private JFrame frame;
	private Graphics g;
	private BufferedImage background, side;
	private double z, v;
	private int choice, x, y;
	private PlayMenuGui playMenu;
	private LevelMenuGui standardLevelMenu, customLevelMenu;
	private RankingsMenuGui rankingsMenu;

	public MainMenuGui() {

		frame = new JFrame();
		frame.setTitle("Labyrinth");
		frame.setSize(new Dimension(WIDTH, HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		InputHandler input = new InputHandler();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);

		z = 0;
		v = 0;
		choice = 0;
		playMenu = new PlayMenuGui(this);
		standardLevelMenu = new LevelMenuGui(this, 1);
		customLevelMenu = new LevelMenuGui(this, 2);
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

		setImageBorders();

		g.drawImage(background, x - 350, y - 300, background.getWidth(), background.getHeight(), null);
		g.drawImage(side, 0, 0, side.getWidth() / 2, frame.getHeight(), null);
		g.drawImage(side, frame.getWidth() - side.getWidth() / 2, 0, side.getWidth() / 2, frame.getHeight(), null);

		if (choice == 0)
			playMenu.renderPlayMenu(g);
		if (choice == 1)
			standardLevelMenu.renderLevelMenu(g);
		if (choice == 2)
			customLevelMenu.renderLevelMenu(g);
		if (choice == 3)
			rankingsMenu.renderRankingsMenu(g);

		g.dispose();
		bs.show();
	}

	private void setImageBorders() {
		z += 0.002;
		v += 0.001;
		x = (int) (Math.sin(z) * 350);
		y = (int) (Math.cos(v) * 280);

		if (x > 350)
			x = 350;
		if (x < 350 - (background.getWidth() - frame.getWidth()))
			x = 350 - (background.getWidth() - frame.getWidth());
		if (y > 300)
			y = 300;
		if (y < 300 - (background.getHeight() - frame.getHeight()))
			y = 300 - (background.getHeight() - frame.getHeight());
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
