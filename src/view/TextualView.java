package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.text.Style;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import model.Entity;
import model.Game;
import model.Map;
import model.Room;
import model.Status;

public class TextualView extends JPanel implements Observer {
	public TextualView(){
		super();
		this.setLayout(new BorderLayout());
		textPane = new JTextPane();
		textPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 28));

		this.add(textPane, BorderLayout.CENTER);
		textPane.setEditable(false);
		label = new JLabel();
		label.setLocation(this.getWidth() / 2 - 15, textPane.getHeight() + 10);
		this.add(label, BorderLayout.PAGE_END);
	}

	@Override
	public void update(Observable game, Object arg1) {
		Game g = (Game)game;
		Map m = g.getMap();
		

		switch(g.getStatus()){
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
			switch(m.getRoomAt(m.getHunter().getRow(), m.getHunter().getCol()).type){
				case Blood:
					label.setText("The blood feels sticky on your toes");
					break;
				case Slime:
					label.setText("Green goo is covering the floor");
					break;
				case Goop:
					label.setText("The smell of this room is making you dizzy");
					break;
				default:
					label.setText("Nothing but a cold stone floor beneath your feet");
			}
			break;
		}

		StyleContext context = new StyleContext();
		StyledDocument document = new DefaultStyledDocument(context);
		
		style = context.getStyle(StyleContext.DEFAULT_STYLE);
		StyleConstants.setBackground(style,  Color.decode("#002B36"));	

		// Draw Rooms
		for(int row = 0; row < m.getHeight(); ++row){
			String curRow;
			int col;
			for(col = 0; col < m.getWidth() * 2 - 1; ++col){
				if(col % 2 == 1){
					try {
						document.insertString(document.getLength(), " ", style);
					} catch (BadLocationException e1){
						e1.printStackTrace();
					}
				}else{
					Room r = m.getRoomAt(row, col / 2);
					String curRoom;
					String charColor = null;
					if(!r.visited){
						curRoom = "~";
						charColor = "#93A1A1";
					}else{
						curRoom = Character.toString(r.type.getChar());
						switch(r.type){
							case Blood:
								charColor = "#892D36";
								break;
							case Goop:
								charColor = "#B48801";
								break;
							case Pit:
								charColor = "#268AD0";
								break;
							case Slime:
								charColor = "#27A097";
								break;
							default:
								charColor = "#002B36";
								break;
						}
					}
					
					StyleConstants.setForeground(style, Color.decode(charColor));
					try {
						document.insertString(document.getLength(), curRoom, style);
					} catch (BadLocationException e1) {
						e1.printStackTrace();
					}
				}
			}

			if(row != m.getHeight() - 1){
				try {
					document.insertString(document.getLength(), "\n", style);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		}
		
		for(Entity e : m.getEntities()){
			// We don't want to draw entities that are in the rooms the Hunter hasn't visited yet
			if(!m.getRoomAt(e.getRow(), e.getCol()).visited)
				continue;
			// We don't want to draw the arrow
			if(e.getClass().getSimpleName().equals("Arrow"))
				continue;
			
			int pos = e.getRow() * (m.getWidth() * 2) + e.getCol() * 2;
			try {
				document.remove(pos, 1);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			String eStr = null;
			StyleConstants.setForeground(style, Color.decode("#DADAD7"));
			switch(e.getClass().getSimpleName()){
				case "Hunter":
					eStr = "@";
					break;
				case "Wumpus":
					eStr = "W";
					break;
				default:
					eStr = "?";
					break;
			}
			
			try {
				document.insertString(pos, eStr, style);
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
		textPane.setDocument(document);
	}
	
	public void setUserText(String text){
		label.setText(text);
	}
	
	
	private JTextPane textPane;
	private JLabel label;
	private Style style;
}

