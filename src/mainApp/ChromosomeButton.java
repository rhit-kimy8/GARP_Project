package mainApp;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
/**
 * Class: ChromosomeButton
 * @author Haocheng Tang
 * <br>Purpose: Used to deal with the flipcoin painting requirement
 *  * <br>Restrictions: no
 * <br>For example: 
 * <pre>
 *    ChromosomeButton chromosomeButton = new ChromosomeButton(1);
 * </pre>
 */
public class ChromosomeButton extends JButton{
	private int i;
	private Color squareColor;	
	/**
	 * ensures: initializes the ChromosomeButton
	 */
	public ChromosomeButton(String name) {
        super(name);
    }
	/**
	 * ensures: paint the squre color and the index of the button
	 * @param The color needs to be paint
	 * <br>requires: Color
	 * @param The index number of the button
	 * <br>requires: integer
	 */
    public void paintSquareColor(Color changecolor, int i) {
        squareColor = changecolor;
        repaint();
        this.i = i;
    }
	/**
	 * ensures: clear the square painting on the edit part
	 */
    public void clearColor() {
    	squareColor = null;
    	repaint();
    }
	/**
	 * ensures: rewrite the function of paintComponent, paint the button things first, then paint the rectangle, and then paint the index number over the rectangle.
	 * @param accountName used to initialize the account's name
	 * <br>requires: accountName &ne; ""
	 * @param initialBalance used to initialize the account's balance
	 * <br>requires: initialBalance &ge; 0.0
	 */
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
