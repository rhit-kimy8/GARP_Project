package mainApp;
 
import java.util.Comparator;
/**
 * Class: FitnessComparator
 * @author Youngrak Kim, Haocheng Tom
 * <br> Purpose: This class is used to sort ArrayList of Chromosome with individual's fitness, eventually sort chromosome from highest fitness to lowest. 
 * <br> Restrictions: None.
 */
public class FitnessComparator implements Comparator<Chromosome>{
	
	// By comparing string o1 and string o2 with count fitness 1 and consecutive fitness, it will return 0 if o1 is fit well than o2, else will return -1.
	@Override
	public int compare(Chromosome o1, Chromosome o2) {
		int fitnesso1 = countFitness(o1);
		int fitnesso2 = countFitness(o2);
		
		return fitnesso2 - fitnesso1;
	}

	
	public static int countFitness(Chromosome c) {
		int count = c.getFitness();
		return count;
	}
}
 
