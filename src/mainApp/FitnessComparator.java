package mainApp;

import java.util.Comparator;

public class FitnessComparator implements Comparator<String>{
	
	// By comparing string o1 and string o2 with count fitness 1 and consecutive fitness, it will return 0 if o1 is fit well than o2, else will return -1.
	@Override
	public int compare(String o1, String o2) {
		int fitnesso1 = countFitness(o1);
		int fitnesso2 = countFitness(o2);
		int consecutiveo1 = maxConsecutiveOnesFitness(o1);
		int consecutiveo2 = maxConsecutiveOnesFitness(o2);
		if (fitnesso1 > fitnesso2) return 0;
		if (fitnesso1 > fitnesso2 && consecutiveo1 > consecutiveo2) return 0;
		if (fitnesso1 == fitnesso2 && consecutiveo1 > consecutiveo2) return 0;
		return -1;
	}
	
	public static int countFitness(String s) {
		int count = 0;
		for(int i = 0; i < s.length(); i++) {
			if (Character.getNumericValue(s.charAt(i)) == 1) count++;
		}
		return count;
	}
	
	public static int maxConsecutiveOnesFitness(String s) {
		int maxConsecutiveCount = 0;
		int count = 0;	
		for(int j=0;j<s.length();j++) {
				if(Character.getNumericValue(s.charAt(j)) == 1) count++;
				else count = 0;
				if(count > maxConsecutiveCount) {
					maxConsecutiveCount = count;
				}		
		}
		return maxConsecutiveCount;
	}
}
