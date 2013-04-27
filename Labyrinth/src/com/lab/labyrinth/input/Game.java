package com.lab.labyrinth.input;

import java.awt.event.KeyEvent;


public class Game {

	public int time;
	public Controller controls;

	public Game() {
		controls = new Controller();
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

		controls.tick(forward, back, left, right, jump, crouch, run, rLeft, rRight);
	}
}
