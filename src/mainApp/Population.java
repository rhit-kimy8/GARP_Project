package mainApp;

import java.util.ArrayList;
import java.util.Random;

public class Population {
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
