package com.lab.labyrinth.input;

import com.lab.labyrinth.graphics.Display;

public class Controller {
	
	private double z, x, y, rotation, xa, za, rotationa;
	private boolean walkBobbing, crouchBobbing, runBobbing;
	private boolean forward, back, left, right, jump, crouch, run, rLeft, rRight, pause;
	private double rotationSpeed, walkSpeed, jumpHeight, crouchHeight, xMove, zMove;

	public void tick() {
		rotationSpeed = Display.game.getRotationSpeed();
		walkSpeed = 0.55;
		jumpHeight = 0.5;
		crouchHeight = 0.5;
		xMove = 0;
		zMove = 0;

		if (Display.game.isPlay() && !Display.game.isCountdown()) {
			position();
			rotation();
			bobbing();
			other();
		}

		if (pause) {
			Display.game.setPlay(false);
			Display.game.setPause(true);
		}

		xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation)) * walkSpeed;
		za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation)) * walkSpeed;

		x += xa;
		z += za;
		y *= 0.9;
		xa *= 0.1;
		za *= 0.1;
		rotation += rotationa;
		rotationa *= 0.8;
	}
	
	private void position(){
		if (forward) {
			zMove++;
			walkBobbing = true;
		}
		if (back) {
			zMove--;
			walkBobbing = true;
		}
		if (left) {
			xMove--;
			walkBobbing = true;
		}
		if (right) {
			xMove++;
			walkBobbing = true;
		}
	}
	
	private void rotation(){
		if (rLeft)
			rotationa -= rotationSpeed;
		if (rRight)
			rotationa += rotationSpeed;
	}
	
	private void bobbing(){
		if (run && walking())
			runBobbing = true;
		if (crouch && walking())
			runBobbing = true;
		if (!walking())
			walkBobbing = false;
		if (!run)
			runBobbing = false;
		if (!crouch)
			crouchBobbing = false;
	}
	
	private boolean walking(){
		if(forward)
			return true;
		if(back)
			return true;
		if(left)
			return true;
		if(right)
			return true;
		return false;
	}
	
	private void other(){
		if (jump)
			y += jumpHeight;

		if (crouch) {
			y -= crouchHeight / 2;
			walkSpeed = 0.2;
		}
		if (run)
			walkSpeed = 0.8;
	}

	public void setForward(boolean forward) {
		this.forward = forward;
	}

	public void setBack(boolean back) {
		this.back = back;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}

	public void setCrouch(boolean crouch) {
		this.crouch = crouch;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	public void setrLeft(boolean rLeft) {
		this.rLeft = rLeft;
	}

	public void setrRight(boolean rRight) {
		this.rRight = rRight;
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	public boolean isWalkBobbing() {
		return walkBobbing;
	}

	public boolean isCrouchBobbing() {
		return crouchBobbing;
	}

	public boolean isRunBobbing() {
		return runBobbing;
	}

	public double getZ() {
		return z;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public double getRotationSpeed() {
		return rotationSpeed;
	}

	public void setRotationSpeed(double rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}
}
