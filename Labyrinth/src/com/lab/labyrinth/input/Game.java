package com.lab.labyrinth.input;

import java.awt.event.KeyEvent;

import com.lab.labyrinth.launcher.OptionsConfiguration;

public class Game {

	private Controller controls;
	private int renderDistance, time;
	private boolean play, pause, finish, countdown;

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
		controls.setForward(key[KeyEvent.VK_W]);
		controls.setBack(key[KeyEvent.VK_S]);
		controls.setLeft(key[KeyEvent.VK_A]);
		controls.setRight(key[KeyEvent.VK_D]);
		controls.setJump(key[KeyEvent.VK_SPACE]);
		controls.setCrouch(key[KeyEvent.VK_CONTROL]);
		controls.setRun(key[KeyEvent.VK_SHIFT]);
		controls.setrRight(key[KeyEvent.VK_RIGHT]);
		controls.setrLeft(key[KeyEvent.VK_LEFT]);
		controls.setPause(key[KeyEvent.VK_ESCAPE]);
		
		controls.tick();
		
	}

	public void loadOptions() {
		OptionsConfiguration options = new OptionsConfiguration();
		options.loadConfiguration("res/settings/config.xml");
		renderDistance = options.getBrightness() * 130 + 1000;
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

	public int getTime() {
		return time;
	}

	public Controller getControls() {
		return controls;
	}
}
