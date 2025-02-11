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
	private int chromosomeSize;
	private int statusIndex;
	private String bitString;
	private ArrayList<Rectangle> rectangles;
	private int fitness;
	
	
	//Could add bitString in constructor if using files to import chromosomes
	Chromosome() {
		this.bitString ="";
		randomGeneration();
		fitnessMaxOnesCalculation();
		this.rectangles = new ArrayList<>();
		colors.add(Color.black);
		colors.add(Color.green);
		//Creates Rectangles to visualize bitString
		for (int yCord = 0; yCord < 80; yCord += 40) {
			for (int xCord = 0; xCord < 200; xCord += 40) {
				rectangles.add(new Rectangle(xCord, yCord, 40, 40));
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
	public void drawOnFitness(Graphics2D g2) {
		for (int x = 0; x < rectangles.size(); x++) {
			Rectangle r = rectangles.get(x);
			String s = "";
			g2.setColor(getColor(Character.getNumericValue(bitString.charAt(x))));
			g2.fill(r);
			if (getColor(Character.getNumericValue(bitString.charAt(x))) == Color.black) {
				g2.setColor(Color.green);
			}
			else {
				g2.setColor(Color.black);
			}
			s += x;
			g2.drawString(s, (int) r.getX(), (int) r.getY() + 10);
		}
	}
	
	public void randomGeneration() {
		for (int x = 0; x < 10; x++) {
			if (randomProcess(0.5)) {
				bitString += Integer.toString(1);
			}
			else {
				bitString += Integer.toString(0);
			}
		}
	}
	
	public void fitnessMaxOnesCalculation() {
		int count = 0;
		for (int i=0; i < this.bitString.length();i++) {
			if (Character.getNumericValue(bitString.charAt(i)) == 1) {
				count += 1;
			}
		}
		this.fitness = count;
	}
	public int matchFitness(Chromosome chromosome) {
			if (getBitString().contains(chromosome.getBitString())) return 1;
			return 0;
	}
	public int consecutiveFitness() {
		int maxConsecutiveCount = 0;
			int count = 0;
			for(int j=0;j<getBitString().length();j++) {
				if(Character.getNumericValue(getBitString().charAt(j)) == 1) count++;
				else count = 0;
				if(count > maxConsecutiveCount) {
					maxConsecutiveCount = count;
				}
				
			}
			return maxConsecutiveCount;
	}
	
	public boolean randomProcess(double possibility) {

		Random r = new Random();// eg: 3% all the doubles > 0.03 will make true, so we shouldn't do any changes
		return (r.nextDouble() > 1-possibility);
	}
	public void mutationProcess(double possibility) {
		for (int i = 0; i < this.bitString.length();i++) {
		if (randomProcess(possibility)) {
			if (bitString.charAt(i) =='1') {
				bitString = bitString.substring(0,i)+"0"+bitString.substring(i+1,bitString.length());
			}
			else {
				bitString = bitString.substring(0,i)+"1"+bitString.substring(i+1,bitString.length());
			}
		}
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
	public String getBitString() {
		return this.bitString;
	}
	
	public int getFitness() {
		fitnessMaxOnesCalculation();
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
	public void setBitString(String bitString) {
		fitnessMaxOnesCalculation();
		this.bitString = bitString;
	}

}