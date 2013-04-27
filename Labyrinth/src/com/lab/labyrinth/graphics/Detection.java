package com.lab.labyrinth.graphics;

import com.lab.labyrinth.input.Controller;
import com.lab.labyrinth.input.Game;

public class Detection {
	private Game game;
	private int x, z;

	public Detection(Game game, int x, int z) {
		this.x = x;
		this.z = z;
		this.game = game;
	}

	/*public boolean detectFinish() {
		if (zL == z) {
			if (x > xL) {
				if (playerIn(zL * 8 - 6, zL * 8, x * 8 - 11, x * 8 + 3))
					return true;
			} else {
				if (playerIn(zL * 8, zL * 8 + 6, xL * 8 - 11, xL * 8 + 3))
					return true;
			}
		} else {
			if (zL > z) {
				if (playerIn(zL * 8 - 11, zL * 8 + 3, xL * 8 - 6, xL * 8))
					return true;
			} else {
				if (playerIn(z * 8 - 11, z * 8 + 3, xL * 8, xL * 8 + 6))
					return true;
			}
		}
		return false;
	}*/

	public void detectCollision() {
		if (inDistance()) {
			if (playerIn(z * 8 - 8, z * 8, (x - 2) * 8 - 11, x * 8 + 3)) {
				game.controls.z = game.controls.z - 0.01;
				Controller.Z = game.controls.z;
			}
			if (playerIn((z + 3) * 8, (z + 3) * 8 + 8, (x - 2) * 8 - 11, x * 8 + 3)) {
				game.controls.z = game.controls.z + 0.01;
				Controller.Z = game.controls.z;
			}
			if (playerIn((z + 1) * 8 - 11, (z + 3) * 8 + 3, (x - 3) * 8 - 8, (x - 3) * 8)) {
				game.controls.x = game.controls.x - 0.1;
				Controller.X = game.controls.x;
			}
			if (playerIn((z + 1) * 8 - 11, (z + 3) * 8 + 3, x * 8, x * 8 + 8)) {
				game.controls.x = game.controls.x + 0.1;
				Controller.X = game.controls.x;
			}
		}
	}

	private boolean playerIn(int a, int b, int c, int d) {
		if (game.controls.z <= a)
			return false;
		if (game.controls.z >= b)
			return false;
		if (game.controls.x <= c)
			return false;
		if (game.controls.x >= d)
			return false;
		return true;
	}

	private boolean inDistance() {
		if (Math.abs(game.controls.z - z * 8) < 40 && Math.abs(game.controls.x - x * 8) < 40)
			return true;
		return false;
	}
}