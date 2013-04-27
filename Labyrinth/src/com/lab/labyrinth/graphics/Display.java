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

import javax.swing.JFrame;

import com.lab.labyrinth.input.Controller;
import com.lab.labyrinth.input.Game;
import com.lab.labyrinth.input.InputHandler;
import com.lab.labyrinth.level.Level;

public class Display extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	private static int width = 900;
	private static int height = 650;
	public static int mouseSpeed;
	private Thread thread;
	private Screen screen;
	private Game game;
	private InputHandler input;
	private BufferedImage img;
	private Graphics g;
	private JFrame frame;
	private Level level;
	private boolean running = false;
	private int[] pixels;
	private int newX = 0, oldX = 0, fps;
	private int minutes, seconds, fullTime;

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
		screen = new Screen(game, this.level, width, height);
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

		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
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
		BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");
		frame.getContentPane().setCursor(blank);

		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		screen.render();
		for (int i = 0; i < width * height; i++)
			pixels[i] = screen.pixels[i];

		g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		g.setFont(new Font("Verdana", 3, 20));
		g.setColor(Color.orange);
		g.drawString(fps + "fps", 810, 70);
		g.drawString("Best Time " + level.getMinBestTime() +":"+level.getSecBestTime(), 700, 30);
		timer();
		
		g.dispose();
		bs.show();
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
			tickCount++;
			if (tickCount % 60 == 0) {
				fps = frames;
				previousTime += 1000;
				frames = 0;
			}
		}
	}
	
	private void timer(){
		time = fullTime -  tickCount/60;
		g.setFont(new Font("Verdana", 3, 20));
		g.setColor(Color.orange);
		g.drawString(Integer.toString(time/60)+":"+Integer.toString(time%60), 20, 30);
	}
}
