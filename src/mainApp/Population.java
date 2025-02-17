package mainApp;
 
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
 
public class Population {
	FitnessComparator FC = new FitnessComparator();
	ArrayList<Chromosome> population = new ArrayList<>();
	Random random = new Random(250);
	private int generationCount;
	
	public Population(){
	}
	
	public void setFitnessFunction(Chromosome chr, int functionChoose) {
		if (functionChoose == 0) {
			chr.MaxOnesFitnessCalculation();
		}else if (functionChoose == 1) {
			chr.matchFitness();
		}
		else if (functionChoose == 2) {
			chr.consecutiveFitness();
		}
	}
	
	public void generateRandomPopulation(int populationSize, int chromosomeSize, int functionChoose) {
		population.clear();
		for (int i=0; i<populationSize; i++) {
			Chromosome chr = new Chromosome(chromosomeSize);
			setFitnessFunction(chr, functionChoose);
			population.add(chr);
		}
		Collections.sort(population,FC);
	}
	
	public void generateSpecialPopulation(int populationSize, int chromosomeSize, int functionChoose) {
		for (int i=0;i<populationSize/4;i++) { // remember to -1 for test
			String chromosome = "";
			for (int j=0;j<chromosomeSize;j++) chromosome = chromosome +"0";
			Chromosome chr = new Chromosome(chromosome);
			setFitnessFunction(chr,functionChoose);
			population.add(chr);
		}
		for (int i=0;i<populationSize/4;i++) {
			String chromosome = "";
			int index = random.nextInt(chromosomeSize-1);
			for (int j=0;j<chromosomeSize;j++) {
				if(j == index) chromosome = chromosome + "1";
				else chromosome = chromosome + "0";
			}
			Chromosome chr = new Chromosome(chromosome);
			setFitnessFunction(chr,functionChoose);
			population.add(chr);
		}
		
		for (int i=0;i<populationSize/4;i++) {
			ArrayList<Integer> list = new ArrayList<>();
			int num = 0;
			while (true) {
				int index = random.nextInt(chromosomeSize-1);
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
			Chromosome chr = new Chromosome(chromosome);
			setFitnessFunction(chr,functionChoose);
			population.add(chr);
		}
		
		for (int i=0;i<populationSize/4;i++) {
			ArrayList<Integer> list = new ArrayList<>();
			int num = 0;
			while (true) {
				int index = random.nextInt(chromosomeSize-1);
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
			Chromosome chr = new Chromosome(chromosome);
			setFitnessFunction(chr,functionChoose);
			population.add(chr);
		}
		Collections.sort(population,FC);
	}
	
	public int maxOnesFitness(ArrayList<Chromosome> chromosomes) {
		int totalMaxOnes = 0;
		for (Chromosome i: chromosomes) {
			totalMaxOnes += i.MaxOnesFitnessCalculation();
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
	
	public void mutatePopulation(ArrayList<Chromosome> chromosomes, double mutationRate, int startNumber) {
		if (mutationRate != 0) {
			for (int i = startNumber; i < chromosomes.size();i++) {
				chromosomes.get(i).mutationProcess(mutationRate);
			}
		}
	}
	
	public void truncationSelect(ArrayList<Chromosome> chromosomes, boolean checkCrossOver,double mutationRate,int elitismNumber) {
		generationCount++;
		Collections.sort(chromosomes,FC);
		int halfSize = chromosomes.size()/2;
		if (elitismNumber != 0) {
		if (!checkCrossOver){
			// pure truncation
		for (int i=0;i<halfSize;i++) chromosomes.removeLast();
		for (int i=0;i<halfSize;i++) {
			Chromosome repeat = new Chromosome(chromosomes.get(i).getBitString());
			repeat.setFitnessFunctionStatus(chromosomes.get(i).getFitnessFunctionStatus());
			chromosomes.add(repeat);
		}
		Collections.sort(chromosomes,FC);
		mutatePopulation(chromosomes,mutationRate,elitismNumber);
		}
		else {
			// elitism + crossover
			for (int i=0;i<halfSize;i++) chromosomes.removeLast();
			for (int i=0;i<elitismNumber;i++) {
				Chromosome repeat = new Chromosome(chromosomes.get(i).getBitString());
				repeat.setFitnessFunctionStatus(chromosomes.get(i).getFitnessFunctionStatus());
				chromosomes.add(repeat);
			}
			crossOverSelect(chromosomes,elitismNumber,halfSize,false,null,false,null);
			mutatePopulation(chromosomes,mutationRate,elitismNumber);
		}
		}
		// no elitism here
		else {
			if (!checkCrossOver){
				// pure truncation
				for (int i=0;i<halfSize;i++) chromosomes.removeLast();
				for (int i=0;i<halfSize;i++) {
					Chromosome repeat = new Chromosome(chromosomes.get(i).getBitString());
					repeat.setFitnessFunctionStatus(chromosomes.get(i).getFitnessFunctionStatus());
					chromosomes.add(repeat);
				}
				Collections.sort(chromosomes,FC);
				mutatePopulation(chromosomes,mutationRate,0);
				}
				else {
					// only crossover
					for (int i=0;i<halfSize;i++) chromosomes.removeLast();
					crossOverSelect(chromosomes,0,halfSize,false,null,false,null);
					mutatePopulation(chromosomes,mutationRate,0);
				}
		}

	}

	public void RouleteSelection(ArrayList<Chromosome> chromosomes, boolean checkCrossOver,double mutationRate,int elitismNumber) {
		generationCount++;
		Collections.sort(chromosomes,FC);
		ArrayList<Chromosome> newChromosomes = new ArrayList<>();
		for (Chromosome chr : chromosomes) {
			newChromosomes.add(chr);
		}
		ArrayList<ArrayList<Chromosome>> classifiedChromosome = sortChromosome(newChromosomes);
		if (elitismNumber != 0) {
			if (!checkCrossOver) {
				//Roulete and elitism
				for (int i = 0;i<newChromosomes.size();i++) chromosomes.removeLast();
				for (int i = 0; i <elitismNumber; i++) {
					Chromosome repeat = new Chromosome(newChromosomes.get(i).getBitString());
					repeat.setFitnessFunctionStatus(newChromosomes.get(i).getFitnessFunctionStatus());
					chromosomes.add(repeat);
				}
				for (int i = elitismNumber; i < newChromosomes.size(); i++) {
					Chromosome selected = RouleteHelper(classifiedChromosome);
					Chromosome repeat = new Chromosome(selected.getBitString());
					repeat.setFitnessFunctionStatus(selected.getFitnessFunctionStatus());
					chromosomes.add(repeat);
				}
				Collections.sort(chromosomes,FC);
				mutatePopulation(chromosomes,mutationRate,elitismNumber);
				
			}
			else {
				// roulete, elitism, and crossOver
				for (int i = 0;i<newChromosomes.size();i++) chromosomes.removeLast();
				for (int i=0;i<elitismNumber;i++) {
					Chromosome repeat = new Chromosome(newChromosomes.get(i).getBitString());
					repeat.setFitnessFunctionStatus(newChromosomes.get(i).getFitnessFunctionStatus());
					chromosomes.add(repeat);
				}
				crossOverSelect(chromosomes,elitismNumber,newChromosomes.size(),true,newChromosomes,false,null);
				mutatePopulation(chromosomes,mutationRate,elitismNumber);
			}
		}else {
			//
			if (!checkCrossOver) {
				//pure Roulete
				for (int i = 0;i<newChromosomes.size();i++) chromosomes.removeLast();
				for (int i = 0; i < newChromosomes.size(); i++) {
					Chromosome selected = RouleteHelper(classifiedChromosome);
					Chromosome repeat = new Chromosome(selected.getBitString());
					repeat.setFitnessFunctionStatus(selected.getFitnessFunctionStatus());
					chromosomes.add(repeat);
				}
				Collections.sort(chromosomes,FC);
				mutatePopulation(chromosomes,mutationRate,0);
				
			}else {
				//roulete and crossover

				for (int i = 0;i<newChromosomes.size();i++) chromosomes.removeLast();
				crossOverSelect(chromosomes,0,newChromosomes.size(),true,newChromosomes,false,null);
				mutatePopulation(chromosomes,mutationRate,0);
				
			}
		}
	}
	
	public ArrayList<ArrayList<Chromosome>> sortChromosome(ArrayList<Chromosome> chromosomes){
		int chromosomeLength = chromosomes.get(0).getBitString().length();
		ArrayList<ArrayList<Chromosome>> sortedChromosome = new ArrayList<ArrayList<Chromosome>>();
		for (int i = 0; i <= chromosomeLength; i++) {
			sortedChromosome.add(new ArrayList<Chromosome>());
		}
		for (Chromosome chr : chromosomes) {
			sortedChromosome.get(chr.getFitness()).add(chr);
		}
		return sortedChromosome;
	}
	
	public Chromosome RouleteHelper(ArrayList<ArrayList<Chromosome>> chromosomes) {
        Random random = new Random();
        int fitnessSize = 0;
        ArrayList<Integer> fitnessSet = new ArrayList<>();
        for (ArrayList<Chromosome> chromosomeFitnessSet : chromosomes) {
        	if (chromosomeFitnessSet.size() == 0) {
        		fitnessSet.add(0);
        		continue;
        	}
            fitnessSet.add(chromosomeFitnessSet.size() * chromosomeFitnessSet.get(0).getFitness());
            fitnessSize += chromosomeFitnessSet.size() * chromosomeFitnessSet.get(0).getFitness();
        }
//        System.out.println("fitness Number is "+fitnessSize);
        int fitnessSelected = random.nextInt(1, fitnessSize + 1);
//        System.out.println("Number is "+fitnessSelected);
        int fitnessCompared = fitnessSet.get(0);
        int fitnessComparedIndex = 0;
        while (fitnessSelected > fitnessCompared) {
            fitnessComparedIndex += 1;
            fitnessCompared += fitnessSet.get(fitnessComparedIndex);
        }
//        System.out.println("fitness ComparedIndex is "+fitnessComparedIndex);
        int chromosomeIndexSelected = random.nextInt(0, chromosomes.get(fitnessComparedIndex).size());
//        System.out.println("fitnessIndex is " +chromosomeIndexSelected);
        return chromosomes.get(fitnessComparedIndex).get(chromosomeIndexSelected);

		
	}
	
	
	
	
	
	public void RankSelection(ArrayList<Chromosome> chromosomes, boolean checkCrossOver,double mutationRate,int elitismNumber) {
		generationCount++;
		Collections.sort(chromosomes,FC);
		ArrayList<Chromosome> newChromosomes = new ArrayList<>();
		for (Chromosome chr : chromosomes) {
			newChromosomes.add(chr);
		}
//		ArrayList<ArrayList<Chromosome>> classifiedChromosome = sortChromosome(newChromosomes);
		if (elitismNumber != 0) {
			if (!checkCrossOver) {
				// Rank and elitism
				for (int i = 0;i<newChromosomes.size();i++) chromosomes.removeLast();
				for (int i=0;i<elitismNumber;i++) {
					Chromosome repeat = new Chromosome(newChromosomes.get(i).getBitString());
					repeat.setFitnessFunctionStatus(newChromosomes.get(i).getFitnessFunctionStatus());
					chromosomes.add(repeat);
				}
				for (int i = elitismNumber; i < newChromosomes.size(); i++) {
					Chromosome selected = RankHelper(newChromosomes);
					Chromosome repeat = new Chromosome(selected.getBitString());
					repeat.setFitnessFunctionStatus(selected.getFitnessFunctionStatus());
					chromosomes.add(repeat);
				}
				Collections.sort(chromosomes,FC);
				mutatePopulation(chromosomes,mutationRate,elitismNumber);
				
			}
			else {
				// rank, elitism, and crossOver
				for (int i = 0;i<newChromosomes.size();i++) chromosomes.removeLast();
				for (int i=0;i<elitismNumber;i++) {
					Chromosome repeat = new Chromosome(newChromosomes.get(i).getBitString());
					repeat.setFitnessFunctionStatus(newChromosomes.get(i).getFitnessFunctionStatus());
					chromosomes.add(repeat);
				}
				crossOverSelect(chromosomes,elitismNumber,newChromosomes.size(),false,null,true,newChromosomes);
				mutatePopulation(chromosomes,mutationRate,elitismNumber);
			}
		}else {
			//
			if (!checkCrossOver) {
				//pure rank
				for (int i = 0;i<newChromosomes.size();i++) chromosomes.removeLast();
				for (int i = 0; i < newChromosomes.size(); i++) {
					Chromosome selected = RankHelper(newChromosomes);
					Chromosome repeat = new Chromosome(selected.getBitString());
					repeat.setFitnessFunctionStatus(selected.getFitnessFunctionStatus());
					chromosomes.add(repeat);
				}
				Collections.sort(chromosomes,FC);
				mutatePopulation(chromosomes,mutationRate,0);
				
			}else {
				//rank and crossover

				for (int i = 0;i<newChromosomes.size();i++) chromosomes.removeLast();
				crossOverSelect(chromosomes,0,newChromosomes.size(),false,null,true,newChromosomes);
				mutatePopulation(chromosomes,mutationRate,0);
				
			}
		}
	}
	
	public Chromosome RankHelper(ArrayList<Chromosome> chromosomes) {
        Random random = new Random();
        int fitnessSize = 0;
        ArrayList<Integer> fitnessSet = new ArrayList<>();
        for (int i = chromosomes.size()-1; i >= 0; i--) {
            fitnessSet.add(i+1);
            fitnessSize += i+1;
        }
//        System.out.println("fitness Number is "+fitnessSize);
        int fitnessSelected = random.nextInt(1, fitnessSize + 1);
//        System.out.println("Number is "+fitnessSelected);
        int fitnessCompared = fitnessSet.get(0);
        int fitnessComparedIndex = 0;
        while (fitnessSelected > fitnessCompared) {
            fitnessComparedIndex += 1;
            fitnessCompared += fitnessSet.get(fitnessComparedIndex);
        }
//        System.out.println("fitness ComparedIndex is "+fitnessComparedIndex);
//        int chromosomeIndexSelected = random.nextInt(0, chromosomes.get(fitnessComparedIndex).size());
//        System.out.println("fitnessIndex is " +chromosomeIndexSelected);
//        return chromosomes.get(fitnessComparedIndex).get(chromosomeIndexSelected);
        return chromosomes.get(fitnessComparedIndex);

		
	}
	
	public void crossOverSelect(ArrayList<Chromosome> population, int startIndex, int populationHalfSize,boolean roulete, ArrayList<Chromosome> rouleteChromosomes, boolean rank, ArrayList<Chromosome> rankChromosomes) {
	
			for (int i =startIndex; i < populationHalfSize; i++) {
				if (roulete) {
					ArrayList<ArrayList<Chromosome>> classifiedChromosome = sortChromosome(rouleteChromosomes);
					Chromosome parent1 = RouleteHelper(classifiedChromosome);
					Chromosome parent2 = RouleteHelper(classifiedChromosome);
					while (parent1 == parent2) {
						parent2 = RouleteHelper(classifiedChromosome);
					}
					Chromosome child = crossOverChromosome(parent1,parent2,5);
					population.add(child);
				}else if(rank){
					Chromosome parent1 = RankHelper(rankChromosomes);
					Chromosome parent2 = RankHelper(rankChromosomes);
					while (parent1 == parent2) {
						parent2 = RankHelper(rankChromosomes);
					}
					Chromosome child = crossOverChromosome(parent1,parent2,5);
					population.add(child);
				}
				else {
				int parentIndex1 = random.nextInt(0, populationHalfSize);
				int parentIndex2 = random.nextInt(0, populationHalfSize);
				while(parentIndex2 == parentIndex1) {
					parentIndex2 = random.nextInt(0, populationHalfSize);
				}
				Chromosome child = crossOverChromosome(population.get(parentIndex1),population.get(parentIndex2),5);
				population.add(child);
				}
			}
			Collections.sort(population,FC);
	}
	
	public void ElitismSelect(int numbers,ArrayList<Chromosome> chromosomes) {
		for (int i = 0; i < numbers; i ++) {
			Chromosome elit = new Chromosome(chromosomes.get(i).getBitString());
			elit.setFitnessFunctionStatus(chromosomes.get(i).getFitnessFunctionStatus());
			chromosomes.add(elit);
		}
		
		
	}
	// this return crossover child chromosome from selected parents chromosome splited crossOverPoint
	public Chromosome crossOverChromosome(Chromosome parent1, Chromosome parent2, int crossOverPoint) {
		String childChromosome = "";
		childChromosome = childChromosome + parent1.getBitString().substring(0, crossOverPoint);
		childChromosome = childChromosome + parent2.getBitString().substring(crossOverPoint, parent2.getBitString().length());
		Chromosome chr = new Chromosome(childChromosome);
		chr.setFitnessFunctionStatus(parent1.getFitnessFunctionStatus());
		return chr;
	}
	
	public void removePopulation() {
		population.clear();
	}
	
	public int calculateHammingDis() {
		int avg = 0;
		int count = 0;
		int index = 0;
		for (Chromosome chr: population) {
			count = 0;
			for (int j = index; j< population.size(); j++) {
				for (int i = 0; i < population.get(0).getBitString().length(); i++) {
					if (chr.getBitString().charAt(i) != population.get(j).getBitString().charAt(i)) count++;
				}
			}
			index ++;
			avg += count/population.size();
		}
		return avg/population.size();
	}

	
	
	public void testSpecialPopulation(int populationSize, int chromosomeLength) {
		generateSpecialPopulation(populationSize, chromosomeLength,0);
		int totalMaxOnes = 0;
		int count = 1;
		for (Chromosome i: population) {
			System.out.println(count +"th chromosome is " + i);
			count++;
			for (int k = 0; k < i.getBitString().length(); k++) {
				if (Character.getNumericValue(i.getBitString().charAt(k)) == 1) totalMaxOnes++;
			}
		}
		System.out.println("Total MaxOnes fitness is " + totalMaxOnes);
	}
	
	public void testPopulation(int populationSize, int chromosomeLength) {
		generateRandomPopulation(populationSize, chromosomeLength,0);
		int n = 1;
		for(Chromosome i:population) {
			System.out.println(i.getBitString());
			System.out.println(n + "th chromosome");
			n ++;
		}
	}
	public Chromosome getBest(ArrayList<Chromosome> chromosomes) {
		Collections.sort(chromosomes,FC);
		return chromosomes.get(0);
	}
	public Integer getAverage(ArrayList<Chromosome> chromosomes) {
		int average = 0;
		for (Chromosome i : chromosomes) {
			average += i.getFitness();
		}
		average = average / chromosomes.size();
		return average;
	}
	public Integer getWorst(ArrayList<Chromosome> chromosomes) {
		Collections.sort(chromosomes,FC);
		return chromosomes.get(chromosomes.size()-1).getFitness();
	}
	
 
	public ArrayList<Chromosome> getPopulation() {
		return population;
	}

	public int getGenerationCount() {
		return generationCount;
	}
	
	public void setGenerationCount() {
		generationCount = 0;
	}
	
}
 