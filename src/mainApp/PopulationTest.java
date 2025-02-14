package mainApp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Test;

public class PopulationTest {

	Population pop = new Population();
	Random random = new Random();

	@Test
	public void testGenerateRandomPopulation() {
		int populationSize = random.nextInt(0, 100);
		int chromosomeLength = random.nextInt(0,100);
		pop.generateRandomPopulation(populationSize, chromosomeLength,0);
		int populationSizeCount = 0;
		int chromosomeLengthCount = 0;
		for (Chromosome c:pop.population) {
			populationSizeCount++;
			chromosomeLengthCount = c.getBitString().length();
		}
		if (populationSize == populationSizeCount && chromosomeLength == chromosomeLengthCount) assertTrue(true);
		else fail();
	}
	
	@Test
	public void testGenerateSpecialPopulation() {
		pop.generateSpecialPopulation(100,10);
		int populationSizeCount = 0;
		int chromosomeLengthCount = 0;
		int zeroOnesCount = 0;
		int oneOnesCount = 0;
		int threeOnesCount = 0;
		int sixOnesCount = 0;
		for (Chromosome c:pop.specialPopulation) {
			populationSizeCount++;
			chromosomeLengthCount = c.getBitString().length();
			if (c.getFitness() == 0) zeroOnesCount ++;
			if (c.getFitness() == 1) oneOnesCount ++;
			if (c.getFitness() == 3) threeOnesCount ++;
			if (c.getFitness() == 6) sixOnesCount ++;
		}
		if (100 == populationSizeCount && 10 == chromosomeLengthCount &&  
			zeroOnesCount == 25 && oneOnesCount == 25 && threeOnesCount == 25 && sixOnesCount == 25) assertTrue(true);
		else fail();

	}
	
	@Test
	public void testTruncationSelection() {
		pop.generateSpecialPopulation(100,10);
		// this for 450 in fitness
		pop.truncationSelect(pop.specialPopulation, false, 0, 0);
		// this for 600 in fitness
		pop.truncationSelect(pop.specialPopulation, false, 0, 0);
		int totalFitness = pop.maxOnesFitness(pop.specialPopulation);
		if (totalFitness == 600) assertTrue(true);
	}
	
	@Test
	public void testCrossover() {
		int populationSize = random.nextInt(0, 100);
		int chromosomeLength = random.nextInt(0,100);
		pop.generateRandomPopulation(populationSize, chromosomeLength,0);
		int parent1Index = random.nextInt(0,populationSize);
		int parent2Index = random.nextInt(0,populationSize);
		Chromosome chr = pop.crossOverChromosome(pop.population.get(parent1Index), pop.population.get(parent2Index), pop.population.get(0).getBitString().length()/2);
		boolean isSame = false;
		for(int i=0; i<pop.population.get(0).getBitString().length()/2; i++) {
			if (chr.getBitString().charAt(i) == pop.population.get(parent1Index).getBitString().charAt(i) &&
				chr.getBitString().charAt(i) == pop.population.get(parent2Index).getBitString().charAt(i +pop.population.get(0).getBitString().length()/2)) isSame = true;
			else isSame = false;
		}
		if (isSame) assertTrue(true);
	}
	
	@Test
	public void testMaxOnesFitness() {
		Chromosome chr = new Chromosome();
		String str = "";
		for (int i = 0; i<100; i++) str = str + "1";
		chr.setBitString(str);
		ArrayList<Chromosome> list = new ArrayList<>();
		list.add(chr);
		if (pop.maxOnesFitness(list) == 100) assertTrue(true);
		else fail();
	}
	
	@Test
	public void testMaxConsecutiveOnesFitness() {
		int populationSize = random.nextInt(0, 100);
		int chromosomeLength = random.nextInt(0,100);
		pop.generateRandomPopulation(populationSize, chromosomeLength,0);
		int maxConsecutiveNum = 0;
		for (Chromosome chr: pop.population) {
			int count = 0;
			for (int i = 0; i < chr.getBitString().length(); i++) {
				if (Character.getNumericValue(chr.getBitString().charAt(i)) == 1) {
					count ++;
				}else count = 0;
				if (count > maxConsecutiveNum) maxConsecutiveNum = count;
			}
		}
		if (maxConsecutiveNum == pop.maxConsecutiveOnesFitness(pop.population)) assertTrue(true);
		else fail();
	}
	
	@Test
	public void testMatchTargetFitness() {
		pop.generateSpecialPopulation(100, 10);
		// Because in special population, there are 25% of none ones chromosomes
		int noneOnesChromosomeNum = 25;
		Chromosome chr = new Chromosome();
		chr.setBitString("0000000000");
		if (noneOnesChromosomeNum == pop.matchTargetFitness(pop.getSpecialPopulation(), chr)) assertTrue(true);
	}
	
	@Test
	public void testMutate() {
		int populationSize = 1;
		int chromosomeLength = 10;
		pop.generateRandomPopulation(populationSize, chromosomeLength,0);
		ArrayList<Chromosome> list = pop.population;
		pop.mutatePopulation(pop.population, 1);
		for (int i = 0; i< pop.population.get(0).getBitString().length(); i++) {
			if (pop.population.get(0).getBitString().charAt(i) != list.get(0).getBitString().charAt(i)) assertTrue(true);
		}
	}
	
	
	
}
