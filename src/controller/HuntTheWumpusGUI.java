package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import model.Direction;
import model.Game;
import model.GameAction;
import view.TextualView;

public class HuntTheWumpusGUI extends JFrame implements KeyListener{
	public static void main(String[] args){
		HuntTheWumpusGUI hw = new HuntTheWumpusGUI();
		hw.setVisible(true);
	}

	public HuntTheWumpusGUI(){
		g = new Game();
		tv = new TextualView();
		g.addObserver(tv);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		this.setTitle("Hunt The Wumpus");

		JTabbedPane pane = new JTabbedPane();
		pane.addTab("Textual View", tv);
		this.add(pane);
		pane.addKeyListener(this);
		pane.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("LEFT"), "none");
		pane.getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke("RIGHT"), "none");
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		Direction d = Direction.North;
		
		if(arg0.getKeyCode() == KeyEvent.VK_S){
			isShooting = true;
			return;
		}else{
			switch(arg0.getKeyCode()){
				case KeyEvent.VK_K:
				case KeyEvent.VK_UP:
					d = Direction.North;
					break;
				case KeyEvent.VK_J:
				case KeyEvent.VK_DOWN:
					d = Direction.South;
					break;
				case KeyEvent.VK_H:
				case KeyEvent.VK_LEFT:
					d = Direction.West;
					break;
				case KeyEvent.VK_L:
				case KeyEvent.VK_RIGHT:
					d = Direction.East;
					break;
				default:
					return;
			}
		}
		
		if(isShooting)
			g.perform(GameAction.Shoot, d);
		else
			g.perform(GameAction.Move, d);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}
	
	private Game g;
	private TextualView tv;
	static boolean isShooting = false;
	private static final int WIDTH = 600;
	private static final int HEIGHT = 800;

}
