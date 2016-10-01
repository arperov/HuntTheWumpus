package controller;

import javax.swing.*;

public class HuntTheWumpusGUI extends JFrame {
	public static void main(String[] args){
		HuntTheWumpusGUI hw = new HuntTheWumpusGUI();
		hw.setVisible(true);
	}

	public HuntTheWumpusGUI(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		this.setTitle("Hunt The Wumpus");

		JTabbedPane pane = new JTabbedPane();

	}

	private static final int WIDTH = 600;
	private static final int HEIGHT = 800;
}
