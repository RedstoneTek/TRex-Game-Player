package com.tek.trgp;

import javax.swing.JOptionPane;

import com.tek.trgp.player.Player;

public class Launcher {

	public static void main(String[] args) {
		
		Player player = new Player();
		if(player.init()) {
			player.start();
		}else {
			JOptionPane.showMessageDialog(null, "Failed to find game instance");
		}
		
	}

}
