package testForMileStone2;

import java.util.Collections;

import mainApp.FitnessComparator;
import mainApp.Population;
import mainApp.Chromosome;

public class testForThreeFitnessFunction {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Chromosome chr = new Chromosome(100);
		String str = "";
		for (int i = 0; i < 100; i++) {
			str += "1";
		}
		chr.setBitString(str);
		System.out.println("Test for MaxOnes.\n");
		System.out.println("Current bitString : "+chr.getBitString());
		System.out.println("MaxOnes fitness for the chromosome with all 1s : "+chr.getFitness());
		
		System.out.println("Change 1st bit.");
		
		str = "0";
		for (int i = 0; i < 99; i++) {
			str += "1";
		}
		chr.setBitString(str);
		System.out.println("Current bitString : "+chr.getBitString());
		System.out.println("MaxOnes fitness for current chromosome with all 1s : "+chr.getFitness());
		
		System.out.println("\nTest MaxConsecutive function.\n");
		str = "";
		for (int i = 0; i < 100; i++) {
			str += "1";
		}
		chr.setBitString(str);
		System.out.println("Current bitString : "+chr.getBitString());
		System.out.println("Consecutive fitness for the chromosome with all 1s : "+chr.consecutiveFitness());
		
		System.out.println("Change the middle bit.");
		str = "";
		for (int i = 0; i < 50; i++) {
			str += "1";
		}
		str +="0";
		for (int i = 0; i < 49; i++) {
			str += "1";
		}
		chr.setBitString(str);
		System.out.println("Current bitString : "+chr.getBitString());
		System.out.println("Consecutive fitness for the chromosome with all 1s : "+chr.consecutiveFitness());
		
		Chromosome comparedchr = new Chromosome(10);
		System.out.println("Test for match function.\n");
		for (int i = 0; i < 5; i ++) {
		Chromosome randomchr = new Chromosome(10);
		System.out.println("match bitstring : "+comparedchr.getBitString());
		System.out.println("gener bitstring : "+randomchr.getBitString());
		System.out.println("match fitness: "+ randomchr.matchFitness(comparedchr));
		System.out.println();
		}
		
		
		
		
	}

}
