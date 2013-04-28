package com.lab.labyrinth.input;

import java.awt.event.KeyEvent;

import com.lab.labyrinth.launcher.OptionsConfiguration;

public class Game {

	public int time;
	public Controller controls;
	private int renderDistance;
	private boolean play, pause, finish, countdown;

	public Game() {
		controls = new Controller();
		loadOptions();
		play = true;
		pause = false;
		finish = false;
		countdown = true;
	}

	public void tick(boolean[] key) {
		time++;
		boolean forward = key[KeyEvent.VK_W];
		boolean back = key[KeyEvent.VK_S];
		boolean left = key[KeyEvent.VK_A];
		boolean right = key[KeyEvent.VK_D];
		boolean jump = key[KeyEvent.VK_SPACE];
		boolean crouch = key[KeyEvent.VK_CONTROL];
		boolean run = key[KeyEvent.VK_SHIFT];
		boolean rLeft = key[KeyEvent.VK_LEFT];
		boolean rRight = key[KeyEvent.VK_RIGHT];
		boolean pause =  key[KeyEvent.VK_ESCAPE];

		controls.tick(forward, back, left, right, jump, crouch, run, rLeft, rRight, pause);
	}
	
	public void loadOptions(){
		OptionsConfiguration options = new OptionsConfiguration();
		options.loadConfiguration("res/settings/config.xml");
		renderDistance = options.getBrightness()*130 + 1000;
	}

	public int getRenderDistance() {
		return renderDistance;
	}

	public void setRenderDistance(int renderDistance) {
		this.renderDistance = renderDistance;
	}

	public boolean isPlay() {
		return play;
	}

	public void setPlay(boolean play) {
		this.play = play;
	}

	public boolean isPause() {
		return pause;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public boolean isFinish() {
		return finish;
	}

	public void setFinish(boolean finish) {
		this.finish = finish;
	}

	public boolean isCountdown() {
		return countdown;
	}

	public void setCountdown(boolean countdown) {
		this.countdown = countdown;
	}
}
