package com.lab.labyrinth.input;

import com.lab.labyrinth.graphics.Display;

public class Controller {

	public double xa, za, rotation, rotationa, y, walkMode;
	public static boolean turnLeft = false;
	public static boolean turnRight = false;
	public static boolean turnUp = false;
	public static boolean turnDown = false;
	public static boolean walkBobbing = false;
	public static boolean crouchBobbing = false;
	public static boolean runBobbing = false;
	public static boolean collision = false;
	public static boolean collisionB = false;
	public static boolean collisionL = false;
	public static boolean collisionR = false;
	public static double Z, X;
	public double z, x;
	public boolean go = false;

	public void tick(boolean forward, boolean back, boolean left, boolean right, boolean jump, boolean crouch, boolean run, boolean rLeft, boolean rRight, boolean pause) {
		double rotationSpeedA = 0.0003 * Display.mouseSpeed;
		double rotationSpeedB = 0.008;
		double walkSpeed = 0.55;
		double jumpHeight = 0.5;
		double crouchHeight = 0.5;
		double xMove = 0;
		double zMove = 0;

		if (Display.game.isPlay()) {
			if (forward) {
				if (!collision) {
					zMove++;
				}
				walkBobbing = true;
			}

			if (back) {
				if (!collision) {
					zMove--;
				}
				walkBobbing = true;
			}

			if (left) {
				if (!collision) {
					xMove--;
				}
				walkBobbing = true;
			}

			if (right) {
				if (!collision) {
					xMove++;
				}
				walkBobbing = true;
			}

			if (rLeft) {
				rotationa -= rotationSpeedB;
			}

			if (rRight) {
				rotationa += rotationSpeedB;
			}

			if (turnLeft) {
				rotationa -= rotationSpeedA;
			}

			if (turnRight) {
				rotationa += rotationSpeedA;
			}

			if (jump) {
				y += jumpHeight;
			}

			if (crouch) {
				y -= crouchHeight / 2;
				walkSpeed = 0.2;
			}

			if (run) {
				walkSpeed = 0.8;
			}

			if (run & (forward || back || left || right)) {
				runBobbing = true;
			}

			if (crouch & (forward || back || left || right)) {
				runBobbing = true;
			}

			if (!forward && !back && !left && !right && !jump) {
				walkBobbing = false;
			}

			if (!run) {
				runBobbing = false;
			}

			if (!crouch) {
				crouchBobbing = false;
			}
		}

		if (pause) {
			Display.game.setPlay(false);
			Display.game.setPause(true);
		}

		xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * walkSpeed;
		za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * walkSpeed;

		x += xa;
		z += za;
		Z = z;
		X = x;
		y *= 0.9;
		xa *= 0.1;
		za *= 0.1;
		rotation += rotationa;
		rotationa *= 0.8;
	}

}
