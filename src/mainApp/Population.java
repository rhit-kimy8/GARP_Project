package mainApp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Population {
	FitnessComparator FC = new FitnessComparator();
	ArrayList<String> population = new ArrayList<>();
	ArrayList<String> specialPopulation = new ArrayList<>();
	Random random = new Random();
	
	Population(){
	}
	
	public void generateRandomPopulation(int populationSize, int chromosomeSize) {
		for (int i=0; i<populationSize; i++) {
			String chromosome = "";
			for (int j=0; j<chromosomeSize; j++) {
				if (random.nextDouble() > 0.5) {
					chromosome = chromosome + "1";
				}else {
					chromosome = chromosome + "0";
				}
			}
			population.add(chromosome);
		}
	}
	
	public void generateSpecialPopulation(int populationSize, int chromosomeSize) {
		for (int i=0;i<populationSize/4;i++) {
			String chromosome = "0000000000";
			specialPopulation.add(chromosome);
		}
		for (int i=0;i<populationSize/4;i++) {
			String chromosome = "";
			int index = random.nextInt(9);
			for (int j=0;j<chromosomeSize;j++) {
				if(j == index) chromosome = chromosome + "1";
				else chromosome = chromosome + "0";
			}
			specialPopulation.add(chromosome);
		}
		
		for (int i=0;i<populationSize/4;i++) {
			ArrayList<Integer> list = new ArrayList<>();
			int num = 0;
			while (true) {
				int index = random.nextInt(9);
				if (list.contains(index)) continue;
				list.add(index);
				num++;
				if(num == 3) break;
			}
			String chromosome = "";
			for (int j=0;j<chromosomeSize;j++) {
				if(list.contains(j)) chromosome = chromosome + "1";
				else chromosome = chromosome + "0";
			}
			specialPopulation.add(chromosome);
		}
		
		for (int i=0;i<populationSize/4;i++) {
			ArrayList<Integer> list = new ArrayList<>();
			int num = 0;
			while (true) {
				int index = random.nextInt(9);
				if (list.contains(index)) continue;
				list.add(index);
				num++;
				if(num == 6) break;
			}
			String chromosome = "";
			for (int j=0;j<chromosomeSize;j++) {
				if(list.contains(j)) chromosome = chromosome + "1";
				else chromosome = chromosome + "0";
			}
			specialPopulation.add(chromosome);
		}
	}
	
	public int maxOnesFitness(ArrayList<String> chromosomes) {
		int totalMaxOnes = 0;
		for (String i: chromosomes) {
			for (int k = 0; k < i.length(); k++) {
				if (Character.getNumericValue(i.charAt(k)) == 1) totalMaxOnes++;
			}
		}
		return totalMaxOnes;
	}
	
	public int matchTargetFitness(ArrayList<String> chromosomes, String chromosome) {
		int matchingChromosomes = 0;
		for (String i: chromosomes) {
			if (i.contains(chromosome)) matchingChromosomes++;
		}
		return matchingChromosomes;
	}
	
	public int maxConsecutiveOnesFitness(ArrayList<String> chromosomes) {
		int maxConsecutiveCount = 0;
		for (String i: chromosomes) {
			int count = 0;
			for(int j=0;j<i.length();j++) {
				if(Character.getNumericValue(i.charAt(j)) == 1) count++;
				else count = 0;
				if(count > maxConsecutiveCount) {
					maxConsecutiveCount = count;
				}
				
			}
		}
		return maxConsecutiveCount;
	}
	
	public void truncationSelect(ArrayList<String> chromosomes) {
		for (String j :chromosomes) {
			System.out.println(j);
		}
		Collections.sort(chromosomes,FC);
		Collections.reverse(chromosomes);
		
		// this println and below print method are for checking sort is working or not.
		System.out.println("Remove Half");
		int selectionSize = chromosomes.size()/2;
		for (int i=0;i<selectionSize;i++) {
			chromosomes.removeLast();
		}
		for (String j :chromosomes) {
			System.out.println(j);
		}
		
		// this is the test to check get each random parents and try to return child chromosome through crossover
		String parent1 = chromosomes.get(random.nextInt(chromosomes.size()/2));
		System.out.println("Parent1");
		System.out.println(parent1);
		String parent2 = chromosomes.get(random.nextInt(chromosomes.size()/2,chromosomes.size()-1));
		System.out.println("Parent2");
		System.out.println(parent2);
		String child = crossOverChromosome(parent1,parent2,parent1.length()/2);
		System.out.println("Child");
		System.out.println(child);
	}
	
	// this return crossover child chromosome from selected parents chromosome splited crossOverPoint
	public String crossOverChromosome(String parent1, String parent2, int crossOverPoint) {
		String childChromosome = "";
		childChromosome = childChromosome + parent1.substring(0, crossOverPoint);
		childChromosome = childChromosome + parent2.substring(crossOverPoint, parent2.length());
		return childChromosome;
	}
	
	
	public void testSpecialPopulation(int populationSize, int chromosomeLength) {
		generateSpecialPopulation(populationSize, chromosomeLength);
		int totalMaxOnes = 0;
		int count = 1;
		for (String i: specialPopulation) {
			System.out.println(count +"th chromosome is " + i);
			count++;
			for (int k = 0; k < i.length(); k++) {
				if (Character.getNumericValue(i.charAt(k)) == 1) totalMaxOnes++;
			}
		}
		System.out.println("Total MaxOnes fitness is " + totalMaxOnes);
	}
	
	public void testPopulation(int populationSize, int chromosomeLength) {
		generateRandomPopulation(populationSize, chromosomeLength);
		int n = 1;
		for(String i:population) {
			System.out.println(i);
			System.out.println(n + "th chromosome");
			n ++;
		}
	}

	public ArrayList<String> getPopulation() {
		return population;
	}

	public ArrayList<String> getSpecialPopulation() {
		return specialPopulation;
	}	
	
}
