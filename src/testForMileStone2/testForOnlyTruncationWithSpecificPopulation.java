package testForMileStone2;

import java.util.Collections;

import mainApp.FitnessComparator;
import mainApp.Population;

public class testForOnlyTruncationWithSpecificPopulation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Population pop = new Population();
		FitnessComparator FC = new FitnessComparator();
		pop.generateSpecialPopulation(100, 10,0);
		for (int i = 0; i < 3; i ++) {
		System.out.println("No."+i+" random generation for 100 chromosomes with length 10 to test truncation.");
		Collections.sort(pop.getPopulation(),FC);
		for (int j = 0; j < pop.getPopulation().size(); j++) {
			System.out.println(pop.getPopulation().get(j).getBitString() +" with fitness "+pop.getPopulation().get(j).getFitness());
		}
		System.out.println("Total fitness: "+pop.maxOnesFitness(pop.getPopulation()));
		pop.truncationSelect(pop.getPopulation(), false, 0, 0);
	}
	}

}
