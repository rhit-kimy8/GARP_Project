package testForMileStone2;
import java.util.Collections;

import mainApp.FitnessComparator;
import mainApp.Population;
public class testForMutation {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Population pop = new Population();
		FitnessComparator FC = new FitnessComparator();
		pop.generateSpecialPopulation(100, 10);
		for (int i = 0; i < 10; i ++) {
		System.out.println("No."+i+" random generation for 100 chromosomes with length 10 to test mutation. Mutation rate is 10%.");
		Collections.sort(pop.getSpecialPopulation(),FC);
		System.out.println("Best fitness: "+pop.getSpecialPopulation().get(0).getFitness());
		System.out.println("Total fitness: "+pop.maxOnesFitness(pop.getSpecialPopulation()));
		pop.truncationSelect(pop.getSpecialPopulation(), false, 0.1, 0);
	}
		System.out.println("\n test for 0 mutation rate.\n");
		pop = new Population();
		FC = new FitnessComparator();
		pop.generateSpecialPopulation(100, 10);
		for (int i = 0; i < 10; i ++) {
			System.out.println("No."+i+" random generation for 100 chromosomes with length 10 to test mutation. Mutation rate is 10%.");
			Collections.sort(pop.getSpecialPopulation(),FC);
			System.out.println("Best fitness: "+pop.getSpecialPopulation().get(0).getFitness());
			System.out.println("Total fitness: "+pop.maxOnesFitness(pop.getSpecialPopulation()));
			pop.truncationSelect(pop.getSpecialPopulation(), false, 0, 0);
		}
	}
}
