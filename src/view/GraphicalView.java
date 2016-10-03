/*
 * Artyom Perov
 *
 * Graphical view
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;

import model.Entity;
import model.Game;
import model.Map;

public class GraphicalView extends JPanel implements Observer {
	
	public GraphicalView(){
		super();
		this.setLayout(new BorderLayout());

		label = new JLabel();
		this.add(label, BorderLayout.PAGE_END);
		this.setBackground(Color.BLACK);
		label.setForeground(Color.CYAN);
		
		try{
			hunterImg = ImageIO.read(new File("img/TheHunter.png"));
			wumpusImg = ImageIO.read(new File("img/Wumpus.png"));
			bloodImg = ImageIO.read(new File("img/Blood.png"));
			groundImg = ImageIO.read(new File("img/Ground.png"));
			goopImg = ImageIO.read(new File("img/Goop.png"));
			slimeImg = ImageIO.read(new File("img/Slime.png"));
			pitImg = ImageIO.read(new File("img/SlimePit.png"));
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public void paintComponent(Graphics g){
		final int tSize = 33;
		super.paintComponent(g);
		Map m = game.getMap();
		BufferedImage curImg = null;

		for(int row = 0; row < m.getHeight(); ++row){
			for(int col = 0; col < m.getWidth(); ++col){
				if(!m.getRoomAt(row, col).visited)
					continue;
				
				// Draw the ground first
				g.drawImage(groundImg, col * tSize, row * tSize, tSize, tSize, this);
				
				switch(m.getRoomAt(row, col).type){
					case Blood:
						curImg = bloodImg;
						break;
					case Empty:
						curImg = groundImg;
						break;
					case Goop:
						curImg = goopImg;
						break;
					case Slime:
						curImg = slimeImg;
						break;
					case Pit:
						curImg = pitImg;
						break;
				}
				g.drawImage(curImg, col * tSize, row * tSize, tSize, tSize, this);
			}
		}
		
		for(Entity e : m.getEntities()){
			// We don't want to draw entities that are in the rooms the Hunter hasn't visited yet
			if(!m.getRoomAt(e.getRow(), e.getCol()).visited)
				continue;
			// We don't want to draw the arrow
			if(e.getClass().getSimpleName().equals("Arrow"))
				continue;
			
			switch(e.getClass().getSimpleName()){
				case "Hunter":
					curImg = hunterImg;
					break;
				case "Wumpus":
					curImg = wumpusImg;
					break;
				default:
					curImg = groundImg;
					break;
			}
			g.drawImage(curImg, e.getCol() * tSize, e.getRow() * tSize, tSize, tSize, this);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		game = (Game)arg0;
		Map m = game.getMap();
		switch(game.getStatus()){
			case DeathByWumpus:
				label.setText("The Wumpus got you");
				m.setAllRooms(true);
				break;
			case DeathByPit:
				label.setText("You walked into a bottomless pit");
				m.setAllRooms(true);
				break;
			case DeathByArrow:
				label.setText("Your shot yourself to death");
				m.setAllRooms(true);
				break;
			case Success:
				label.setText("You Won!");
				m.setAllRooms(true);
				break;
			default:
				label.setText("");
				break;
		}
		repaint();
	}
	
	public void setUserText(String text){
		label.setText(text);
	}
	
	private Game game;
	private JLabel label;
	private BufferedImage hunterImg;
	private BufferedImage wumpusImg;
	private BufferedImage bloodImg;
	private BufferedImage groundImg;
	private BufferedImage goopImg;
	private BufferedImage slimeImg;
	private BufferedImage pitImg;
}
