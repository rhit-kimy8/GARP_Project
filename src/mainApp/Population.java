package mainApp;

import java.util.ArrayList;
import java.util.Random;

public class Population {
	ArrayList<String> population = new ArrayList<>();
	ArrayList<String> specialPopulation = new ArrayList<>();
	Random random = new Random();
	
	Population(){
	}
	
	public void generateRandomPopulation(int size) {
		for (int i=0; i<size; i++) {
			String chromosome = "";
			for (int j=0; j<10; j++) {
				if (random.nextDouble() > 0.5) {
					chromosome = chromosome + "1";
				}else {
					chromosome = chromosome + "0";
				}
			}
			population.add(chromosome);
		}
	}
	
	public void generateSpecialPopulation(int size) {
		for (int i=0;i<size/4;i++) {
			String chromosome = "0000000000";
			specialPopulation.add(chromosome);
		}
		for (int i=0;i<size/4;i++) {
			String chromosome = "";
			int index = random.nextInt(9);
			for (int j=0;j<10;j++) {
				if(j == index) chromosome = chromosome + "1";
				else chromosome = chromosome + "0";
			}
			specialPopulation.add(chromosome);
		}
		
		for (int i=0;i<size/4;i++) {
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
			for (int j=0;j<10;j++) {
				if(list.contains(j)) chromosome = chromosome + "1";
				else chromosome = chromosome + "0";
			}
			specialPopulation.add(chromosome);
		}
		
		for (int i=0;i<size/4;i++) {
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
			for (int j=0;j<10;j++) {
				if(list.contains(j)) chromosome = chromosome + "1";
				else chromosome = chromosome + "0";
			}
			specialPopulation.add(chromosome);
		}
	}
	
	public void testMaxOnes(int size) {
		generateSpecialPopulation(size);
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
	
	public void testGeneration(int size) {
		generateRandomPopulation(size);
		int n = 1;
		for(String i:population) {
			System.out.println(i);
			System.out.println(n + "th chromosome");
			n ++;
		}
	}
	
	
	
	
}
