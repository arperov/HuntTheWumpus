package view;

import javax.swing.*;

public class TextualView extends JTextPane implements Observer {
	public TextualView(int rows, int cols){
		super();
  	//this.setBackground(0x002B36);
  	//setFont(new Font("Courier", Font.BOLD, 18));
  	//setRows(rows);
  	//setColumns(cols);

  	setEditable(false);
}

