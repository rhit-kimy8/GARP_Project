package testForMileStone2;
import java.util.Collections;

import mainApp.FitnessComparator;
import mainApp.Population;

public class testForRandom {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (int i = 0; i < 3; i ++) {
		System.out.println("No."+i+" random generation for 16 chromosomes with length 10 to test seed function.");
		Population pop = new Population();
		FitnessComparator FC = new FitnessComparator();
		pop.generateSpecialPopulation(16, 10,0);
		Collections.sort(pop.getPopulation(),FC);
		for (int j = 0; j < pop.getPopulation().size(); j++) {
			System.out.println(pop.getPopulation().get(j).getBitString());
		}
	}
		
	}

}
