package com.lab.labyrinth.launcher;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.lab.labyrinth.Main;
import com.lab.labyrinth.input.InputHandler;
import com.lab.labyrinth.level.LevelCreate;
import com.lab.labyrinth.menu.MainMenuGui;

public class LauncherGui extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private static int WIDTH = 800;
	private static int HEIGHT = 400;
	private boolean running = false;
	private Thread thread;
	private JFrame frame;
	private Graphics g;
	private BufferedImage background, pointer, playOn, playOff, createOn, createOff, optionsOn, optionsOff, exitOn, exitOff;

	public LauncherGui() {

		frame = new JFrame();
		frame.setUndecorated(true);
		frame.setTitle("Labyrinth Launcher");
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
		startLauncher();
		clickCheck();

		frame.repaint();
	}

	public void updateFrame() {
		if (InputHandler.dragged) {
			Point p = frame.getLocation();
			frame.setLocation(p.x + InputHandler.MouseDX - InputHandler.MousePX, p.y + InputHandler.MouseDY - InputHandler.MousePY);
		}
	}

	public void startLauncher() {
		running = true;
		thread = new Thread(this, "Launcher");
		thread.start();
	}

	public void stopLauncher() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (running) {
			renderLauncher();
			updateFrame();
		}
	}

	private void renderLauncher() throws IllegalStateException {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);

		renderPlay();
		renderCreate();
		renderOptions();
		renderExit();

		g.dispose();
		bs.show();
	}

	private void renderPlay() {
		if (mouseIn(610, 620 + 170, 90, 100 + 50)) {
			g.drawImage(playOn, 620, 100, 160, 40, null);
			g.drawImage(pointer, 740, 100, 40, 40, null);
			if (InputHandler.MousePressed == 1) {
				Main.game.getSound().playButton();
				frame.dispose();
				new MainMenuGui();
				stopLauncher();
				clickCheck();
			}
		} else {
			g.drawImage(playOff, 620, 100, 160, 40, null);
		}
	}

	private void renderCreate() {
		if (mouseIn(590, 600 + 170, 150, 160 + 50)) {
			g.drawImage(createOn, 600, 160, 160, 40, null);
			g.drawImage(pointer, 740, 160, 40, 40, null);
			if (InputHandler.MousePressed == 1) {
				Main.game.getSound().playButton();
				frame.dispose();
				new LevelCreate();
				stopLauncher();
				clickCheck();
			}
		} else {
			g.drawImage(createOff, 600, 160, 160, 40, null);
		}
	}

	private void renderOptions() {
		if (mouseIn(590, 600 + 170, 210, 220 + 50)) {
			g.drawImage(optionsOn, 600, 220, 160, 40, null);
			g.drawImage(pointer, 740, 220, 40, 40, null);
			if (InputHandler.MousePressed == 1) {
				Main.game.getSound().playButton();
				new OptionsGui();
				clickCheck();
			}
		} else {
			g.drawImage(optionsOff, 600, 220, 160, 40, null);
		}
	}

	private void renderExit() {
		if (mouseIn(610, 620 + 170, 270, 280 + 50)) {
			g.drawImage(exitOn, 620, 280, 160, 40, null);
			g.drawImage(pointer, 740, 280, 40, 40, null);
			if (InputHandler.MousePressed == 1) {
				Main.game.getSound().playButton();
				System.exit(0);
			}
		} else {
			g.drawImage(exitOff, 620, 280, 160, 40, null);
		}
	}

	private void loadImages() {
		try {
			background = ImageIO.read(LauncherGui.class.getResource("/launcher/Launcher_Img.jpg"));
			pointer = ImageIO.read(LauncherGui.class.getResource("/launcher/pointer.png"));
			playOn = ImageIO.read(LauncherGui.class.getResource("/launcher/play_on.png"));
			playOff = ImageIO.read(LauncherGui.class.getResource("/launcher/play_off.png"));
			createOn = ImageIO.read(LauncherGui.class.getResource("/launcher/create_on.png"));
			createOff = ImageIO.read(LauncherGui.class.getResource("/launcher/create_off.png"));
			optionsOn = ImageIO.read(LauncherGui.class.getResource("/launcher/options_on.png"));
			optionsOff = ImageIO.read(LauncherGui.class.getResource("/launcher/options_off.png"));
			exitOn = ImageIO.read(LauncherGui.class.getResource("/launcher/exit_on.png"));
			exitOff = ImageIO.read(LauncherGui.class.getResource("/launcher/exit_off.png"));
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
