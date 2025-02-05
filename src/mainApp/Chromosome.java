package mainApp;

import java.awt.Color;
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
	private int name;
	private ArrayList<Color> colors = new ArrayList<>();
	private int statusIndex;
	
	Chromosome() {
		Random randomness = new Random();
		statusIndex = randomness.nextInt(0, 3);
		colors.add(Color.red);
		colors.add(Color.green);
		colors.add(Color.gray);
	}
	/**
	 * ensures: return the current color
	 */
	public Color getColor() {
		return colors.get(statusIndex);
	}
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
		return getColor();
		
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
	public int getName() {
		return name;
	}
	public void setName(int name) {
		this.name = name;
	}
	public int getStatusIndex() {
		return statusIndex;
	}
	public void setStatusIndex(int statusIndex) {
		this.statusIndex = statusIndex;
	}

}
