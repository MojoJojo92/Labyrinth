package com.lab.labyrinth.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.lab.labyrinth.input.Controller;
import com.lab.labyrinth.input.Game;
import com.lab.labyrinth.input.InputHandler;
import com.lab.labyrinth.launcher.OptionsGui;
import com.lab.labyrinth.level.Level;
import com.lab.labyrinth.play.PlayMenuGui;

public class Display extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private static int width = 900;
	private static int height = 650;
	public static int mouseSpeed;
	public static boolean finish;
	public static boolean pause;
	private Thread thread;
	private Screen screen;
	public static Game game;
	private InputHandler input;
	private BufferedImage img, resumeOff, resumeOn, restartOff, restartOn, optionsOff, optionsOn, quitOff, quitOn, cursor;
	private Graphics g;
	private JFrame frame;
	private Level level;
	private Cursor blank;
	private boolean running = false;
	private int[] pixels;
	private int newX = 0, oldX = 0, fps;
	private int gameTime, minutes, seconds, fullTime;

	private int frames, tickCount;
	private double unprossesedSeconds;
	private long previousTime, currentTime, passedTime;
	private boolean ticked;
	private int time;

	public Display(Level level) {
		this.level = level;

		frame = new JFrame("Labyrinth");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setSize(width, height);
		frame.getContentPane().add(this);
		frame.setLocationRelativeTo(null);

		Dimension size = new Dimension(width, height);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);

		game = new Game();
		screen = new Screen(this.level, width, height);
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		input = new InputHandler();
		previousTime = System.nanoTime();
		unprossesedSeconds = 0;
		ticked = false;
		tickCount = 0;
		frames = 0;
		minutes = level.getMinTimeLimit();
		seconds = level.getSecTimeLimit();
		fullTime = minutes * 60 + seconds;
		gameTime = 0;

		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		loadImages();
		blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");
		start();
	}

	public void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this, "game");
		thread.start();
	}

	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public synchronized void run() {
		requestFocus();
		while (running) {
			fps();
			if (ticked) {
				renderGame();
				frames++;
			}
		}
	}

	private void tick() {
		game.tick(input.key);
		newX = InputHandler.MouseX;
		if (newX > oldX) {
			Controller.turnRight = true;
		}
		if (newX < oldX) {
			Controller.turnLeft = true;
		}
		if (newX == oldX) {
			Controller.turnLeft = false;
			Controller.turnRight = false;
		}
		mouseSpeed = Math.abs(newX - oldX);
		oldX = newX;
	}

	private void renderGame() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();

		if (game.isPlay())
			renderPlay();
		if (game.isFinish())
			renderFinish();
		if (game.isPause())
			renderPause();

		g.dispose();
		bs.show();

	}

	private void renderPlay() {
		frame.getContentPane().setCursor(blank);
		screen.render();
		for (int i = 0; i < width * height; i++)
			pixels[i] = screen.pixels[i];
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		g.setFont(new Font("Verdana", 3, 20));
		g.setColor(Color.orange);
		g.drawString(fps + "fps", 810, 70);
		g.drawString("Best Time " + level.getMinBestTime() + ":" + level.getSecBestTime(), 700, 30);
		timer();
	}

	private void renderFinish() {
	}

	private void renderPause() {
		frame.getContentPane().setCursor(null);
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		timer();
		renderResume();
		renderRestart();
		renderOptions();
		renderQuit();
	}

	private void renderResume() {
		if (mouseIn(width / 2 - (resumeOn.getWidth()) / 2, width / 2 - (resumeOn.getWidth()) / 2 + resumeOn.getWidth(), 200, 200 + resumeOn.getHeight())) {
			g.drawImage(resumeOn, width / 2 - (resumeOn.getWidth()) / 2, 190, resumeOn.getWidth(), resumeOn.getHeight(), null);
			if (InputHandler.MousePressed == 1){
				clickCheck();
				game.setPause(false);
				game.setPlay(true);
				game.loadOptions();
			}
		} else {
			g.drawImage(resumeOff, width / 2 - (resumeOff.getWidth()) / 2, 200, resumeOff.getWidth(), resumeOff.getHeight(), null);
		}
	}

	private void renderRestart() {
		if (mouseIn(width / 2 - (restartOn.getWidth()) / 2, width / 2 - (restartOn.getWidth()) / 2 + restartOn.getWidth(), 270, 270 + restartOn.getHeight())){
			g.drawImage(restartOn, width / 2 - (restartOn.getWidth()) / 2, 260, restartOn.getWidth(), restartOn.getHeight(), null);
			if(InputHandler.MousePressed == 1){
				clickCheck();
				game = new Game();
				tickCount = 0;
			}
		}else{
			g.drawImage(restartOff, width / 2 - (restartOff.getWidth()) / 2, 270, restartOff.getWidth(), restartOff.getHeight(), null);
		}
	}

	private void renderOptions() {
		if (mouseIn(width / 2 - (optionsOn.getWidth()) / 2, width / 2 - (optionsOn.getWidth()) / 2 + optionsOn.getWidth(), 340, 340 + optionsOn.getHeight())){
			g.drawImage(optionsOn, width / 2 - (optionsOn.getWidth()) / 2, 330, optionsOn.getWidth(), optionsOn.getHeight(), null);
			if(InputHandler.MousePressed == 1){
				clickCheck();
				new OptionsGui();
			}
		}else{
			g.drawImage(optionsOff, width / 2 - (optionsOff.getWidth()) / 2, 340, optionsOff.getWidth(), optionsOff.getHeight(), null);
		}
	}

	private void renderQuit() {
		if (mouseIn(width / 2 - (quitOn.getWidth()) / 2, width / 2 - (quitOn.getWidth()) / 2 + quitOn.getWidth(), 410, 410 + quitOn.getHeight())){
			g.drawImage(quitOn, width / 2 - (quitOn.getWidth()) / 2, 400, quitOn.getWidth(), quitOn.getHeight(), null);
			if(InputHandler.MousePressed == 1){
				clickCheck();
				frame.dispose();
				new PlayMenuGui();
				stop();
			}
		}else{
			g.drawImage(quitOff, width / 2 - (quitOff.getWidth()) / 2, 410, quitOff.getWidth(), quitOff.getHeight(), null);
		}
	}

	private void loadImages() {
		try {
			resumeOff = ImageIO.read(Display.class.getResource("/textures/resumeOff.png"));
			resumeOn = ImageIO.read(Display.class.getResource("/textures/resumeOn.png"));
			restartOff = ImageIO.read(Display.class.getResource("/textures/restartOff.png"));
			restartOn = ImageIO.read(Display.class.getResource("/textures/restartOn.png"));
			optionsOff = ImageIO.read(Display.class.getResource("/textures/optionsOff.png"));
			optionsOn = ImageIO.read(Display.class.getResource("/textures/optionsOn.png"));
			quitOff = ImageIO.read(Display.class.getResource("/textures/quitOff.png"));
			quitOn = ImageIO.read(Display.class.getResource("/textures/quitOn.png"));
			cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void fps() {
		currentTime = System.nanoTime();
		passedTime = currentTime - previousTime;
		previousTime = currentTime;
		unprossesedSeconds += passedTime / 1000000000.0;
		while (unprossesedSeconds > 1 / 60.0) {
			tick();
			unprossesedSeconds -= 1 / 60.0;

			ticked = true;
			if (!game.isPause())
				tickCount++;
			if (tickCount % 60 == 0) {
				fps = frames;
				previousTime += 1000;
				frames = 0;
			}
		}
	}

	private void timer() {
		adjustColor();
		if (game.isPlay()) {
			gameTime++;
			time = fullTime - tickCount / 60;
			g.drawString(Integer.toString(time / 60) + ":" + Integer.toString(time % 60), 20, 30);
		} else if (game.isPause()) {

		}
	}

	private void adjustColor() {
		if (time < 10)
			g.setColor(Color.red);
		else if (game.isFinish())
			g.setColor(Color.green);
		else
			g.setColor(Color.orange);
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
