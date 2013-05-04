package com.lab.labyrinth.input;

import java.awt.event.KeyEvent;

import com.lab.labyrinth.launcher.OptionsConfiguration;

public class Game {

	private Controller controls;
	private OptionsConfiguration options;
	private int renderDistance, time;
	private double rotationSpeed;
	private boolean play, pause, finish, countdown;
	private int forward, backwards, left, right, jump, run, rLeft, rRight;

	public Game() {
		controls = new Controller();
		loadOptions();
		play = true;
		pause = false;
		finish = false;
		countdown = true;
		time = 0;
	}

	public void tick(boolean[] key) {
		if (!pause)
			time++;
		controls.setForward(key[forward]);
		controls.setBack(key[backwards]);
		controls.setLeft(key[left]);
		controls.setRight(key[right]);
		controls.setJump(key[jump]);
		controls.setRun(key[run]);
		controls.setrRight(key[rRight]);
		controls.setrLeft(key[rLeft]);
		controls.setCrouch(key[KeyEvent.VK_CONTROL]);
		controls.setPause(key[KeyEvent.VK_ESCAPE]);
		controls.tick();
	}

	public void loadOptions() {
		options = new OptionsConfiguration();
		options.loadConfiguration("res/settings/config.xml");
		renderDistance = options.getBrightness() * 130 + 1000;
		rotationSpeed = options.getSensitivity() * 0.02/100;
		for(int i = 0; i < 100; i++)
			loadControls(i);
	}
	
	private void loadControls(int i){
		if(KeyEvent.getKeyText(i).equals(options.getForward()))
			forward = i;
		if(KeyEvent.getKeyText(i).equals(options.getBack()))
			backwards = i;
		if(KeyEvent.getKeyText(i).equals(options.getLeft()))
			left = i;
		if(KeyEvent.getKeyText(i).equals(options.getRight()))
			right = i;
		if(KeyEvent.getKeyText(i).equals(options.getRun()))
			run = i;
		if(KeyEvent.getKeyText(i).equals(options.getJump()))
			jump = i;
		if(KeyEvent.getKeyText(i).equals(options.getTurnLeft()))
			rLeft = i;
		if(KeyEvent.getKeyText(i).equals(options.getTurnRight()))
			rRight = i;
	}

	public int getRenderDistance() {
		return renderDistance;
	}

	public void setRenderDistance(int renderDistance) {
		this.renderDistance = renderDistance;
	}

	public double getRotationSpeed() {
		return rotationSpeed;
	}

	public void setRotationSpeed(int rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
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

	public int getTime() {
		return time;
	}

	public Controller getControls() {
		return controls;
	}
}
