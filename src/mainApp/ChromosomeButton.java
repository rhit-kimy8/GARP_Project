package mainApp;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;

public class ChromosomeButton extends JButton{
	private Color color;
	public ChromosomeButton(String name) {
        super(name);
    }

    public void paintSquareColor(Color changecolor) {
        color = changecolor;
        repaint();
    }
    public void clearColor() {
    	color = null;
    	repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
        if (color != null) { 
            int x = (getWidth() - 50) / 2;
            int y = (getHeight() - 50) / 2;
            Color transparentColor = new Color(color.getRed(),color.getGreen(), color.getBlue(),99);
            g.setColor(transparentColor);
            g.fillRect(x, y, 50, 50);
        }
        
         
    }
}
