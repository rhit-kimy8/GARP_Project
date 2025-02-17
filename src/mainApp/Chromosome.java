package mainApp;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;
/**
 * Class: Chromosome
 * @author Haocheng Tang, Daniel
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
	//private ArrayList<Integer> bitString;
	private String bitString;
	private String comparedString;
	private ArrayList<Rectangle2D.Float> rectangles;
	private ArrayList<Rectangle2D.Float> rectangleIndividual;
	private int fitness;
	private int fitnessFunctionStatus;
	
	
	//Could add bitString in constructor if using files to import chromosomes
	public Chromosome(int chromosomeSize) {
		//this.bitString = new ArrayList<>();
		this.bitString = "";
		randomGeneration(chromosomeSize);
		MaxOnesFitnessCalculation();
		
		String str = "";
		for (int i = 0; i < this.bitString.length()/2; i++) {
			str += "1";
		}
		for (int i = this.bitString.length()/2; i < this.bitString.length(); i++) {
			str += "0";
		}
		this.comparedString = str;
		
		this.rectangles = new ArrayList<>();
		this.rectangleIndividual = new ArrayList<>();
		colors.add(Color.black);
		colors.add(Color.green);
		colors.add(Color.gray);
	}
	Chromosome(String chromosomeBitString){
		this.bitString = "";
		setBitString(chromosomeBitString);
		MaxOnesFitnessCalculation();
		
		String str = "";
		for (int i = 0; i < this.bitString.length()/2; i++) {
			str += "1";
		}
		for (int i = this.bitString.length()/2; i < this.bitString.length(); i++) {
			str += "0";
		}
		this.comparedString = str;
		
		this.rectangles = new ArrayList<>();
		this.rectangleIndividual = new ArrayList<>();
		colors.add(Color.black);
		colors.add(Color.green);
		colors.add(Color.gray);
	}
	
	Chromosome(){
		this.bitString = "";
		MaxOnesFitnessCalculation();
		this.rectangles = new ArrayList<>();
		this.rectangleIndividual = new ArrayList<>();
		colors.add(Color.black);
		colors.add(Color.green);
		colors.add(Color.gray);
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
		return colors.get(statusIndex);
		
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
		/*
		//for (int x = 0; x < rectangles.size(); x++) {
		int bitLen = bitString.length();
		int xAmount = (int) (Math.pow(bitLen, .5) + .99);
		int yAmount = (int) (Math.pow(bitLen, .5));
		float xSize = 200 / xAmount;
		float ySize = 200 / yAmount;
		System.out.println("xAmount: " + xAmount + " yAmount: " + yAmount + " xSize: " + xSize + " ySize: " + ySize);
		for (int x = 0; x < this.bitString.length(); x++) {
			if (rectangles.size() == 0) {
				for (int yCord = 0; yCord < 200; yCord += ySize) {
					for (int xCord = 0; xCord < 200; xCord += xSize) {
						rectangles.add(new Rectangle2D.Float(xCord, yCord, xSize, ySize));
					}
				}
			}
			Rectangle2D r = rectangles.get(x);
			String s = "";
			g2.setColor(getColor(bitString.substring(x,x+1)));
			g2.fill(r);
			if (getColor(bitString.substring(x,x+1)) == Color.black) {
				g2.setColor(Color.green);
			}
			else {
				g2.setColor(Color.black);
			}
			s += x;
			g2.drawString(s, (int) r.getX(), (int) r.getY() + 10);
		}
		*/
		
		//Testing
		if (rectangleIndividual.size() == 0) {
			int chromeLen = this.getBitString().length();
			int xAmount = (int) ((Math.pow(chromeLen, .5) + .99));
			int yAmount = (int) (Math.pow(chromeLen, .5));
			System.out.println("xAmount: " + xAmount + " yAmount: " + yAmount);
			float xSize = 200 / xAmount;
			float ySize = 200 / yAmount;
			System.out.println("xSize: " + xSize + "\nySize: " + ySize);
			///*
			for (float yCord = 0; yCord < 200; yCord += ySize) {
				for (float xCord = 0; xCord < 200; xCord += xSize) {
					rectangleIndividual.add(new Rectangle2D.Float(xCord, yCord, xSize, ySize));	
				}
			}
		}
		System.out.println("Rectangles size: " + rectangles.size());
		for (int x = 0; x < this.bitString.length(); x++) {
			//for (int x = 0; x < rectangles.size(); x++) {
				Rectangle2D r = rectangleIndividual.get(x);
				g2.setColor(getColor(bitString.substring(x,x+1)));
				g2.fill(r);
			}
	}
	
	public void drawOnPopulation(Graphics2D g2, float xLen, float yLen, int popLen) {
		if (rectangles.size() == 0) {
			int chromeLen = this.getBitString().length();
			int xAmount = (int) ((Math.pow(chromeLen, .5) + .99));
			int yAmount = (int) (Math.pow(chromeLen, .5));
			//System.out.println("xAmount: " + xAmount + " yAmount: " + yAmount);
			float xSize = xLen / xAmount;
			float ySize = yLen / yAmount;
			//System.out.println("xSize: " + xSize + "\nySize: " + ySize);
			///*
			for (float yCord = 0; yCord < yLen; yCord += ySize) {
				for (float xCord = 0; xCord < xLen; xCord += xSize) {
					rectangles.add(new Rectangle2D.Float(xCord, yCord, xSize, ySize));
					//rectangles.add(new Rectangle2D.Double(xCord, yCord, xSize, ySize));
					
				}
			}
		}
		for (int x = 0; x < this.bitString.length(); x++) {
		//for (int x = 0; x < rectangles.size(); x++) {
			Rectangle2D r = rectangles.get(x);
			g2.setColor(getColor(bitString.substring(x,x+1)));
			g2.fill(r);
		}
		g2.setColor(Color.black);
		g2.draw(new Rectangle2D.Float(0, 0, xLen, yLen));
	}
	
	public void randomGeneration(int length) {
		for (int x = 0; x < length; x++) {
			if (randomProcess(0.5)) {
				bitString += Integer.toString(1);
			}
			else {
				bitString += Integer.toString(0);
			}
		}
	}
	
	public int MaxOnesFitnessCalculation() {
		int count = 0;
		for (int i=0; i < this.bitString.length();i++) {
			if (Character.getNumericValue(bitString.charAt(i)) == 1) {
				count += 1;
			}
		}
		this.fitnessFunctionStatus = 0;
		this.fitness = count;
		return count;
	}
	
	public int matchFitness() {
		int count = 0;
			for (int i = 0; i < this.getBitString().length(); i++) {
				if (this.getBitString().charAt(i) == comparedString.charAt(i)) {
					count += 1;
				}
			}
			this.fitness = count;
			this.fitnessFunctionStatus = 1;
		return count;
	}
	
	public int matchFitness(Chromosome chromosome) {
		int count = 0;
			for (int i = 0; i < this.getBitString().length(); i++) {
				if (this.getBitString().charAt(i) == chromosome.getBitString().charAt(i)) {
					count += 1;
				}
			}
			this.comparedString = chromosome.getBitString();
			this.fitness = count;
			this.fitnessFunctionStatus = 1;
		return count;
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
			this.fitness = maxConsecutiveCount;
			this.fitnessFunctionStatus = 2;
			return maxConsecutiveCount;
	}
	
	public int binaryFitness() {
		int num = 0;
		String bits = this.bitString;
		for (int x = bits.length() - 1; x >= 0; x--) {
			num += Integer.parseInt(bits.substring(x, x + 1)) * Math.pow(2, bits.length() - x - 1);
		}
		return num;
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
	public Color getColor(String bit) {
		if (bit.equals("1")) {
			return Color.green;
		}
		else if (bit.equals("0")){
			return Color.black;
		}else {
			return Color.gray;
		}
	}
	public String getBitString() {
		return this.bitString;
	}
	
	public int getFitness() {
		if (this.fitnessFunctionStatus == 0) {
		MaxOnesFitnessCalculation();
		}
		else if (this.fitnessFunctionStatus == 1) {
			matchFitness();
		}
		else {
			consecutiveFitness();
		}
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
		this.bitString = bitString;
		getFitness();
	}
	public int getFitnessFunctionStatus() {
		return fitnessFunctionStatus;
	}
	public void setFitnessFunctionStatus(int fitnessFunctionStatus) {
		this.fitnessFunctionStatus = fitnessFunctionStatus;
		getFitness();
	}

}