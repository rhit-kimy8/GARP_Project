package mainApp;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

public class ChromosomeButton extends JButton{
	private int i;
	private Color squareColor;
	public ChromosomeButton(String name) {
        super(name);
    }

    public void paintSquareColor(Color changecolor, int i) {
        squareColor = changecolor;
        repaint();
        this.i = i;
    }
    public void clearColor() {
    	squareColor = null;
    	repaint();
    }

    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
        if (squareColor != null) { 
            int x = getWidth() / 4;
            int y = getHeight() / 4 ;
            g.setColor(squareColor);
            g.fillRect(x, y, getWidth()/2, getHeight()/2);
            g.setColor(Color.BLUE);
            g.drawString(Integer.toString(this.i),getWidth()/2-5, getHeight()/2+5);
        }
        
         
    }

	public Color getSquareColor() {
		return squareColor;
	}

	public void setSquareColor(Color squareColor) {
		this.squareColor = squareColor;
	}
}
