package com.lab.labyrinth.graphics;

import com.lab.labyrinth.input.Controller;

public class Detection {
	private int x, z;

	public Detection(int x, int z) {
		this.x = x;
		this.z = z;
	}

	public boolean detectFinish() {
		if (playerIn(z * 8, z * 8 + 31, x * 8 - 31, x * 8))
			return true;
		return false;
	}

	public void detectCollision() {
		if (inDistance()) {
			if (playerIn(z * 8 - 8, z * 8, (x - 2) * 8 - 11, x * 8 + 3)) {
				Display.game.controls.z = Display.game.controls.z - 0.01;
				Controller.Z = Display.game.controls.z;
			}
			if (playerIn((z + 3) * 8, (z + 3) * 8 + 8, (x - 2) * 8 - 11, x * 8 + 3)) {
				Display.game.controls.z = Display.game.controls.z + 0.01;
				Controller.Z = Display.game.controls.z;
			}
			if (playerIn((z + 1) * 8 - 11, (z + 3) * 8 + 3, (x - 3) * 8 - 8, (x - 3) * 8)) {
				Display.game.controls.x = Display.game.controls.x - 0.1;
				Controller.X = Display.game.controls.x;
			}
			if (playerIn((z + 1) * 8 - 11, (z + 3) * 8 + 3, x * 8, x * 8 + 8)) {
				Display.game.controls.x = Display.game.controls.x + 0.1;
				Controller.X = Display.game.controls.x;
			}
		}
	}

	private boolean playerIn(int a, int b, int c, int d) {
		if (Display.game.controls.z <= a)
			return false;
		if (Display.game.controls.z >= b)
			return false;
		if (Display.game.controls.x <= c)
			return false;
		if (Display.game.controls.x >= d)
			return false;
		return true;
	}

	private boolean inDistance() {
		if (Math.abs(Display.game.controls.z - z * 8) < 40 && Math.abs(Display.game.controls.x - x * 8) < 40)
			return true;
		return false;
	}

	public int getX() {
		return x;
	}

	public int getZ() {
		return z;
	}
}