package view;

import java.awt.Color;
import java.awt.Font;
import java.util.Observable;
import java.util.Observer;

import javax.swing.text.Style;
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

public class TextualView extends JPanel implements Observer {
	public TextualView(){
		super();
  	//this.setBackground(0x002B36);
  	//setFont(new Font("Courier", Font.BOLD, 18));
  	//setRows(rows);
  	//setColumns(cols);
		/*
		StyleContext context = new StyleContext();
		StyledDocument document = new DefaultStyledDocument(context);
		
		style = context.getStyle(StyleContext.DEFAULT_STYLE);
		StyleConstants.setBackground(style,  Color.decode("#002B36"));	
		StyleConstants.setFontFamily(style, "Monospace");
		StyleConstants.setFontSize(style, 42);
		*/
		/*
    try {
      document.insertString(document.getLength(), "", style);
    } catch (BadLocationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
*/
		textPane = new JTextPane();
		textPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 28));

		this.add(textPane);
		textPane.setEditable(false);
		
	}

	@Override
	public void update(Observable game, Object arg1) {
		Game g = (Game)game;
		Map m = g.getMap();
		//g.getMap().getRoomAt(row, col);
		/*
		try {
			textPane.getDocument().remove(0, textPane.getDocument().getLength());
		} catch (BadLocationException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		*/
		

		StyleContext context = new StyleContext();
		StyledDocument document = new DefaultStyledDocument(context);
		
		style = context.getStyle(StyleContext.DEFAULT_STYLE);
		StyleConstants.setBackground(style,  Color.decode("#002B36"));	
		//StyleConstants.setFontFamily(style, "Monospace");
		//StyleConstants.setFontSize(style, 32);
		
		// Draw Rooms
		for(int row = 0; row < m.getHeight(); ++row){
			String curRow;
			int col;
			for(col = 0; col < m.getWidth(); ++col){
				Room r = m.getRoomAt(row, col);
				String curRoom;
				String charColor = null;
				if(!r.visited){
					curRoom = ".";
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
					//document.insertString(row * m.getWidth() + col + 1, curRoom, style);
					document.insertString(document.getLength(), curRoom, style);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			try {
				//document.insertString(row * m.getWidth() + col, "\n", style);
				document.insertString(document.getLength(), "\n", style);

			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		// Draw Entities
		for(Entity e : m.getEntities()){
			//int pos = e.getCol() * (m.getWidth() + 1) + e.getRow();
			int pos = e.getRow() * (m.getWidth() + 1) + e.getCol();
			try {
				document.remove(pos, 1);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
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
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		System.out.println("Hey");
		textPane.setDocument(document);
		//this.repaint();
	}
	
	private JTextPane textPane;
	private Style style;
}

