/*
 * Artyom Perov
 *
 * Driver for the Hunt the Wumpus game
 *
 * __WARNING__ : Only tested on a linux machine
 */

package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import model.Direction;
import model.Game;
import model.GameAction;
import view.GraphicalView;
import view.TextualView;

public class HuntTheWumpusGUI extends JFrame implements KeyListener{
	public static void main(String[] args){
		HuntTheWumpusGUI hw = new HuntTheWumpusGUI();
		hw.setVisible(true);
		JOptionPane.showMessageDialog(null, "Movement: \'h\',\'j\',\'k\',\'l\' or arrow keys\n" +
																				"Shooting: \'s\' + direction (same as movement)");
	}

	public HuntTheWumpusGUI(){
		g = new Game();
		tv = new TextualView();
		gv = new GraphicalView();
		g.addObserver(tv);
		g.addObserver(gv);
		tv.update(g, null);
		gv.update(g, gv);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(WIDTH, HEIGHT);
		this.setTitle("Hunt The Wumpus");

		JTabbedPane pane = new JTabbedPane();
		pane.addTab("Textual View", tv);
		pane.addTab("Graphical View", gv);
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
			tv.setUserText("Shoot where?");
			gv.setUserText("Shoot where?");
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
	public void keyReleased(KeyEvent arg0) {}

	@Override
	public void keyTyped(KeyEvent arg0) {}
	
	private Game g;
	private TextualView tv;
	private GraphicalView gv;
	static boolean isShooting = false;
	private static final int WIDTH = 514;
	private static final int HEIGHT = 575;

}
