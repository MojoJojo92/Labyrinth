package com.lab.labyrinth.graphics;

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
			if (playerIn(z * 8 - 8, z * 8, (x - 2) * 8 - 11, x * 8 + 3))
				Display.game.getControls().setZ(Display.game.getControls().getZ() - 0.01);
			if (playerIn((z + 3) * 8, (z + 3) * 8 + 8, (x - 2) * 8 - 11, x * 8 + 3))
				Display.game.getControls().setZ(Display.game.getControls().getZ() + 0.01);
			if (playerIn((z + 1) * 8 - 11, (z + 3) * 8 + 3, (x - 3) * 8 - 8, (x - 3) * 8))
				Display.game.getControls().setX(Display.game.getControls().getX() - 0.1);
			if (playerIn((z + 1) * 8 - 11, (z + 3) * 8 + 3, x * 8, x * 8 + 8))
				Display.game.getControls().setX(Display.game.getControls().getX() + 0.1);
		}
	}

	private boolean playerIn(int a, int b, int c, int d) {
		if (Display.game.getControls().getZ() <= a)
			return false;
		if (Display.game.getControls().getZ() >= b)
			return false;
		if (Display.game.getControls().getX() <= c)
			return false;
		if (Display.game.getControls().getX()>= d)
			return false;
		return true;
	}

	private boolean inDistance() {
		if (Math.abs(Display.game.getControls().getZ() - z * 8) < 40 && Math.abs(Display.game.getControls().getX() - x * 8) < 40)
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