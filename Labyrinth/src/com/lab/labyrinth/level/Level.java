package com.lab.labyrinth.level;

import java.io.Serializable;
import java.util.ArrayList;

public class Level implements Serializable{

	private static final long serialVersionUID = 1L;
	private String username;
	private String levelName;
	private int[][] flag;
	private int minTimeLimit;
	private int secTimeLimit;
	private int minBestTime;
	private int secBestTime;
	private int blocks;
	private int spawnX;
	private int spawnY;
	private int lvlWidth;
	private int lvlHeight;
    private ArrayList<String> rankings;
	
	
	public Level(String username, String levelName, int[][] flag, int minTimeLimit, int secTimeLimit, int minBestTime, int secBestTime, int blocks, int spawnX, int spawnY, int lvlWidth, int lvlHeight, ArrayList<String> rankings) {
		this.username = username;
		this.levelName = levelName;
		this.flag = flag;
		this.minTimeLimit = minTimeLimit;
		this.secTimeLimit = secTimeLimit;
		this.minBestTime = minBestTime;
		this.secBestTime = secBestTime;
		this.blocks = blocks;
		this.spawnX = spawnX;
		this.spawnY = spawnY;
		this.lvlWidth = lvlWidth;
		this.lvlHeight = lvlHeight;
		this.rankings = rankings;
	}

	
	public ArrayList<String> getRankings() {
		return rankings;
	}
	
	public String getUsername() {
		return username;
	}

	public String getName() {
		return levelName;
	}

	public int[][] getFlag() {
		return flag;
	}

	public int getMinTimeLimit() {
		return minTimeLimit;
	}

	public int getSecTimeLimit() {
		return secTimeLimit;
	}

	public int getMinBestTime() {
		return minBestTime;
	}

	public int getSecBestTime() {
		return secBestTime;
	}

	public int getBlocks() {
		return blocks;
	}

	public int getSpawnX() {
		return spawnX;
	}

	public int getSpawnY() {
		return spawnY;
	}

	public int getLvlWidth() {
		return lvlWidth;
	}

	public int getLvlHeight() {
		return lvlHeight;
	}


	public String getLevelName() {
		return levelName;
	}


	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public void setFlag(int[][] flag) {
		this.flag = flag;
	}


	public void setMinTimeLimit(int minTimeLimit) {
		this.minTimeLimit = minTimeLimit;
	}


	public void setSecTimeLimit(int secTimeLimit) {
		this.secTimeLimit = secTimeLimit;
	}


	public void setMinBestTime(int minBestTime) {
		this.minBestTime = minBestTime;
	}


	public void setSecBestTime(int secBestTime) {
		this.secBestTime = secBestTime;
	}


	public void setBlocks(int blocks) {
		this.blocks = blocks;
	}


	public void setSpawnX(int spawnX) {
		this.spawnX = spawnX;
	}


	public void setSpawnY(int spawnY) {
		this.spawnY = spawnY;
	}


	public void setLvlWidth(int lvlWidth) {
		this.lvlWidth = lvlWidth;
	}


	public void setLvlHeight(int lvlHeight) {
		this.lvlHeight = lvlHeight;
	}


	public void setRankings(ArrayList<String> rankings) {
		this.rankings = rankings;
	}
}