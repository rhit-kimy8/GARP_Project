package mainApp;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

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
	public Color getColor() {
		return colors.get(statusIndex);
	}
	public Color setColorAndGet(int colorNumber) {
		statusIndex = colorNumber;
		return colors.get(colorNumber);
	}
	public Color clickChangeColor() {
		statusIndex = (statusIndex + 1) % 2;
		if (statusIndex == 2) {
			statusIndex = 0;
		}
		return getColor();
		
	}
	public Color mutateChangeColor(int numberColor) {
		statusIndex = numberColor;
		return getColor();
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
