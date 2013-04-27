package com.lab.labyrinth.graphics;

import java.util.ArrayList;

import com.lab.labyrinth.input.Game;
import com.lab.labyrinth.level.Level;

public class Screen extends Render {

	private Game game;
	private Render3D render3D;
	private Level level;
	private ArrayList<Detection> detectionList;
	private int renderDistance = 500;
	private int spawnX, spawnY, finishX, finishY;

	public Screen(Game game, Level level, int width, int height) {
		super(width, height);
		this.level = level;
		this.game = game;
		findSpawnFinish();
		constructDetection();
		finishBlock((finishX - spawnX - 1) * 3,(finishY - spawnY - 1) * 3);
		render3D = new Render3D(detectionList, this.game, width, height);
	}

	public void render() {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = 0;
		}
		render3D.floor();

		for (int i = 0; i < level.getLvlWidth(); i++)
			for (int j = 0; j < level.getLvlHeight(); j++)
				findBlocks(i, j);
		//fullBlock(3,3);

		render3D.renderDistancelimiter();
		draw(render3D, 0, 0);
	}

	private void fullBlock(int xb, int zb) {
		if (Math.abs(Render3D.forward - 8 * zb) < renderDistance && Math.abs(Render3D.sideways - 8 * xb) < renderDistance) {
			for (int y = 0; y < 3; y++) {
				render3D.walls(xb - 1, xb, zb, zb, y);
				render3D.walls(xb - 2, xb - 1, zb, zb, y);
				render3D.walls(xb - 3, xb - 2, zb, zb, y);
				render3D.walls(xb - 3, xb - 3, zb, zb + 1, y);
				render3D.walls(xb - 3, xb - 3, zb + 1, zb + 2, y);
				render3D.walls(xb - 3, xb - 3, zb + 2, zb + 3, y);
				render3D.walls(xb, xb - 1, zb + 3, zb + 3, y);
				render3D.walls(xb - 1, xb - 2, zb + 3, zb + 3, y);
				render3D.walls(xb - 2, xb - 3, zb + 3, zb + 3, y);
				render3D.walls(xb, xb, zb + 1, zb, y);
				render3D.walls(xb, xb, zb + 2, zb + 1, y);
				render3D.walls(xb, xb, zb + 3, zb + 2, y);
			}
		}
	}

	private void finishBlock(int i, int j) {
		detectionList.add(new Detection(game, i, j));
	}

	private void constructDetection() {
		detectionList = new ArrayList<Detection>();
		for (int i = 0; i < level.getLvlWidth(); i++)
			for (int j = 0; j < level.getLvlHeight(); j++)
				if (level.getFlag()[i][j] == 1 || level.getFlag()[i][j] == 2)
					detectionList.add(new Detection(game, ((i - spawnX) - 1) * 3, ((j - spawnY) - 1) * 3));
	}

	private void findSpawnFinish() {
		for (int i = 0; i < level.getLvlWidth(); i++) {
			for (int j = 0; j < level.getLvlHeight(); j++) {
				if (level.getFlag()[i][j] == 3) {
					spawnX = i;
					spawnY = j;
				}else if(level.getFlag()[i][j] == 4){
					finishX = i;
					finishY = j;
				}
			}
		}
	}

	private void findBlocks(int i, int j) {
		if (level.getFlag()[i][j] == 1 || level.getFlag()[i][j] == 2)
			fullBlock(((i - spawnX) - 1) * 3, ((j - spawnY) - 1) * 3);
		else if (level.getFlag()[i][j] == 4){
			
		}
			//finishBlock(((i - spawnX) - 1) * 3, ((j - spawnY) - 1) * 3);
	}
}
