package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;
/**
 * Class: Chromosome
 * @author Haocheng Tang
 * <br>Purpose: Used to generate chromosomes
 * <br>Restrictions: no
 * <br>For example: 
 * <pre>
 *    Chromosome chromosome = new Chromosome();
 * </pre>
 */
public class Chromosome {
	private ArrayList<Color> colors = new ArrayList<>();
	private int statusIndex;
	private ArrayList<Integer> bitString;
	private ArrayList<Rectangle> rectangles;
	private int fitness;
	
	
	//Could add bitString in constructor if using files to import chromosomes
	Chromosome() {
		this.bitString = new ArrayList<>();
		this.rectangles = new ArrayList<>();
		for (int x = 0; x < 100; x++) {
			if (Math.random() > .5) {
				bitString.add(1);
			}
			else {
				bitString.add(0);
			}
		}
		//Creates Rectangles to visualize bitString
		for (int xCord = 0; xCord < 100; xCord += 10) {
			for (int yCord = 0; yCord < 100; yCord += 10) {
				rectangles.add(new Rectangle(xCord, yCord, 10, 10));
			}
		}
		
	}
	/**
	 * ensures: return the current color
	 */
	//public Color getColor() {
		//return colors.get(statusIndex);
	//}
	/**
	 * ensures: return the color according to the bit(1 return green)
	 * @param the index of the color
	 * <br>requires: [0,2]
	 */
	public Color getIndexColor(int index) {
		return colors.get(index);
	}
	/**
	 * ensures: change the current bit, and then return the related color to change the button's background color.
	 * @param the index of the color
	 * <br>requires: [0,2]
	 */
	public Color setColorAndGet(int colorNumber) {
		statusIndex = colorNumber;
		return colors.get(colorNumber);
	}
	/**
	 * ensures: change the bit in order(0-1-0-1-0-1) then return the related color to change the background color.
	 * 
	 */
	public Color clickChangeColor() {
		statusIndex = (statusIndex + 1) % 2;
		if (statusIndex == 2) {
			statusIndex = 0;
		}
		return Color.black;
		//return getColor();
		
	}
	/**
	 * ensures: change the bit according to whether the event succeeds
	 * @param true or false
	 * <br>requires: boolean
	 * 	 */
	public void mutate(boolean check) {
		if (check) {
			if(statusIndex == 0) {
				statusIndex = 1;
			}
			else if(statusIndex == 1){
				statusIndex = 0;
			}
		}
	}
	
	//Draw each Chromosome
	public void drawOn(Graphics2D g2) {
		for (int x = 0; x < rectangles.size(); x++) {
			Rectangle r = rectangles.get(x);
			String s = "";
			g2.setColor(getColor(bitString.get(x)));
			g2.fill(r);
			if (getColor(bitString.get(x)) == Color.black) {
				g2.setColor(Color.green);
			}
			else {
				g2.setColor(Color.black);
			}
			s += x;
			g2.drawString(s, (int) r.getX() + 5, (int) r.getY() + 5);
		}
	}
	
	//Getters
	public Color getColor(int bit) {
		if (bit == 1) {
			return Color.green;
		}
		else {
			return Color.black;
		}
	}
	public ArrayList<Integer> getBitString() {
		return this.bitString;
	}
	
	public int getFitness() {
		return this.fitness;
	}
	
	public int getStatusIndex() {
		return statusIndex;
	}
	
	//Setters
	public void setFitness(int fit) {
		this.fitness = fit;
	}
	
	public void setStatusIndex(int statusIndex) {
		this.statusIndex = statusIndex;
	}

}