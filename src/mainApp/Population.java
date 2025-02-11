package mainApp;
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
 
public class Population {
	FitnessComparator FC = new FitnessComparator();
	ArrayList<Chromosome> population = new ArrayList<>();
	ArrayList<Chromosome> specialPopulation = new ArrayList<>();
	Random random = new Random(250);
	
	Population(){
	}
	
	public void generateRandomPopulation(int populationSize, int chromosomeSize) {
		population.clear();
		for (int i=0; i<populationSize; i++) {
			Chromosome chr = new Chromosome();
			population.add(chr);
		}
		Collections.sort(population,FC);
	}
	
	public void generateSpecialPopulation(int populationSize, int chromosomeSize) {
		for (int i=0;i<populationSize/4;i++) { // remember to -1 for test
			String chromosome = "0000000000";
			Chromosome chr = new Chromosome();
			chr.setBitString(chromosome);
			specialPopulation.add(chr);
		}
		
		// for testing randomness
//		Chromosome specialchr = new Chromosome();
//		ArrayList<Integer> speciallist = new ArrayList<>();
//		int specialnum = 0;
//		while (true) {
//			int index = random.nextInt(9);
//			if (speciallist.contains(index)) continue;
//			speciallist.add(index);
//			specialnum++;
//			if(specialnum == 7) break;
//		}
//		String specialchromosome = "";
//		for (int j=0;j<chromosomeSize;j++) {
//			if(speciallist.contains(j)) specialchromosome = specialchromosome + "1";
//			else specialchromosome = specialchromosome + "0";
//		}
//		specialchr.setBitString(specialchromosome);
//		specialPopulation.add(specialchr);
		
		for (int i=0;i<populationSize/4;i++) {
			String chromosome = "";
			int index = random.nextInt(9);
			for (int j=0;j<chromosomeSize;j++) {
				if(j == index) chromosome = chromosome + "1";
				else chromosome = chromosome + "0";
			}
			Chromosome chr = new Chromosome();
			chr.setBitString(chromosome);
			specialPopulation.add(chr);
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
			Chromosome chr = new Chromosome();
			chr.setBitString(chromosome);
			specialPopulation.add(chr);
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
			Chromosome chr = new Chromosome();
			chr.setBitString(chromosome);
			specialPopulation.add(chr);
		}
		Collections.sort(population,FC);
	}
	
	public int maxOnesFitness(ArrayList<Chromosome> chromosomes) {
		int totalMaxOnes = 0;
		for (Chromosome i: chromosomes) {
			totalMaxOnes += i.getFitness();
		}
		return totalMaxOnes;
	}
	
	public int matchTargetFitness(ArrayList<Chromosome> chromosomes, Chromosome chromosome) {
		int matchingChromosomes = 0;
		for (Chromosome i: chromosomes) {
			matchingChromosomes += i.matchFitness(chromosome);
		}
		return matchingChromosomes;
	}
	
	public int maxConsecutiveOnesFitness(ArrayList<Chromosome> chromosomes) {
		int maxConsecutiveCount = 0;
		for (Chromosome i: chromosomes) {
			int count = i.consecutiveFitness();
				if(count > maxConsecutiveCount) {
					maxConsecutiveCount = count;
				}
				
			}
		return maxConsecutiveCount;
	}
	
	public void truncationSelect(ArrayList<Chromosome> chromosomes) {
		for (int j = 0; j < 2; j++) {
//		for (Chromosome chr :chromosomes) {
//			System.out.println(chr.getBitString());
//		}
//		Collections.sort(chromosomes,FC);
//		System.out.println("Sorting");
//		for (Chromosome chr :chromosomes) {
//			System.out.println(chr.getBitString());
//		}
		int halfSize = chromosomes.size()/2;
		for (int i=0;i<halfSize;i++) chromosomes.removeLast();
		for (int i=0;i<halfSize;i++) {
			Chromosome repeat = new Chromosome();
			repeat.setBitString(chromosomes.get(i).getBitString());
			chromosomes.add(repeat);
		}
//		System.out.println(maxOnesFitness(chromosomes));
		}
		
	}
	
	public void crossOverSelect() {
		for (int j = 0; j < 2; j++) {
			Collections.sort(population,FC);
			System.out.println("Sorting");
			for (Chromosome chr :population) {
				System.out.println(chr.getBitString());
			}
			int halfSize = population.size()/2;
			for (int i=0;i<halfSize;i++) population.removeLast();
			for (int i =0; i < halfSize; i++) {
				int parentIndex1 = random.nextInt(0, halfSize);
				System.out.println("parent1");
				System.out.println(population.get(parentIndex1).getBitString());
				int parentIndex2 = random.nextInt(0, halfSize);
				System.out.println("parent2");
				System.out.println(population.get(parentIndex2).getBitString());
				Chromosome child = crossOverChromosome(population.get(parentIndex1),population.get(parentIndex2),5);
				System.out.println("child");
				System.out.println(child.getBitString());
				population.add(child);
			}
			System.out.println(maxOnesFitness(population));
		}
	}
	
	public void ElitismSelect(int numbers,ArrayList<Chromosome> chromosomes) {
		for (int i = 0; i < numbers; i ++) {
			Chromosome elit = new Chromosome();
			elit.setBitString(chromosomes.get(i).getBitString());
			chromosomes.add(elit);
		}
		
		
	}
	// this return crossover child chromosome from selected parents chromosome splited crossOverPoint
	public Chromosome crossOverChromosome(Chromosome parent1, Chromosome parent2, int crossOverPoint) {
		String childChromosome = "";
		childChromosome = childChromosome + parent1.getBitString().substring(0, crossOverPoint);
		childChromosome = childChromosome + parent2.getBitString().substring(crossOverPoint, parent2.getBitString().length());
		Chromosome chr = new Chromosome();
		chr.setBitString(childChromosome);
		return chr;
	}
	
	
	public void testSpecialPopulation(int populationSize, int chromosomeLength) {
		generateSpecialPopulation(populationSize, chromosomeLength);
		int totalMaxOnes = 0;
		int count = 1;
		for (Chromosome i: specialPopulation) {
			System.out.println(count +"th chromosome is " + i);
			count++;
			for (int k = 0; k < i.getBitString().length(); k++) {
				if (Character.getNumericValue(i.getBitString().charAt(k)) == 1) totalMaxOnes++;
			}
		}
		System.out.println("Total MaxOnes fitness is " + totalMaxOnes);
	}
	
	public void testPopulation(int populationSize, int chromosomeLength) {
		generateRandomPopulation(populationSize, chromosomeLength);
		int n = 1;
		for(Chromosome i:population) {
			System.out.println(i.getBitString());
			System.out.println(n + "th chromosome");
			n ++;
		}
	}
	public Chromosome getBest() {
		Collections.sort(population,FC);
		return population.get(0);
	}
	public Integer getAverage() {
		return maxOnesFitness(population)/population.size();
	}
	public Integer getWorst() {
		Collections.sort(population,FC);
		return population.get(population.size()-1).getFitness();
	}
	
 
	public ArrayList<Chromosome> getPopulation() {
		return population;
	}
 
	public ArrayList<Chromosome> getSpecialPopulation() {
		return specialPopulation;
	}	
	
}
 