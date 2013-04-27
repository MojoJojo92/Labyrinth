package com.lab.labyrinth.graphics;

import com.lab.labyrinth.input.Controller;
import com.lab.labyrinth.input.Game;

public class Detection {
	private Game game;
	private int xL, xR, zR, zL, item;

	public Detection(Game game, int xL, int xR, int zR, int zL, int item) {
		this.xL = xL;
		this.xR = xR;
		this.zL = zL;
		this.zR = zR;
		this.item = item;
		this.game = game;
	}

	public boolean detectFinish() {
		if (zL == zR) {
			if (xR > xL) {
				if (playerIn(zL * 8 - 6, zL * 8, xR * 8 - 11, xR * 8 + 3))
					return true;
			} else {
				if (playerIn(zL * 8, zL * 8 + 6, xL * 8 - 11, xL * 8 + 3))
					return true;
			}
		} else {
			if (zL > zR) {
				if (playerIn(zL * 8 - 11, zL * 8 + 3, xL * 8 - 6, xL * 8))
					return true;
			} else {
				if (playerIn(zR * 8 - 11, zR * 8 + 3, xL * 8, xL * 8 + 6))
					return true;
			}
		}
		return false;
	}

	public void detectCollision() {
		if (inDistance()) {
			if (zL == zR) {
				if (xR > xL) {
					if (playerIn(zL * 8 - 6, zL * 8, xR * 8 - 11, xR * 8 + 3)) {
						game.controls.z = game.controls.z - 0.01;
						Controller.Z = game.controls.z;
					}
				} else {
					if (playerIn(zL * 8, zL * 8 + 6, xL * 8 - 11, xL * 8 + 3)) {
						game.controls.z = game.controls.z + 0.01;
						Controller.Z = game.controls.z;
					}
				}
			} else {
				if (zL > zR) {
					if (playerIn(zL * 8 - 11, zL * 8 + 3, xL * 8 - 6, xL * 8)) {
						game.controls.x = game.controls.x - 0.1;
						Controller.X = game.controls.x;
					}
				} else {
					if (playerIn(zR * 8 - 11, zR * 8 + 3, xL * 8, xL * 8 + 6)) {
						game.controls.x = game.controls.x + 0.1;
						Controller.X = game.controls.x;
					}
				}
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
		if (Math.abs(game.controls.z - zL * 8) < 10 && Math.abs(game.controls.x - xL * 8) < 10)
			return true;
		return false;
	}

	public int getItem() {
		return item;
	}

	public int getxL() {
		return xL;
	}

	public int getzL() {
		return zL;
	}
}