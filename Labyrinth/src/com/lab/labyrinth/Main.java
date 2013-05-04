package com.lab.labyrinth;

import com.lab.labyrinth.account.AccountGui;
import com.lab.labyrinth.input.Game;


public class Main {
	
	public static Game game;
	
	public static void main(String[] args) {
		new AccountGui();
		game = new Game();
	}
}
