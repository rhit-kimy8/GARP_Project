package mainApp;

public class testForRoulete {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Population pop = new Population();
		pop.generateSpecialPopulation(100, 10, 0);
		int firstGeneration = 0;
		int secondGeneration = 0;
		int thirdGeneration = 0;
		for (int j = 0; j < 1000; j++) {
			pop.population.clear();
			pop.generateSpecialPopulation(100, 10, 0);
		for (int i = 0; i < 3; i++) {
			
//			System.out.println(pop.maxOnesFitness(pop.population));
			pop.RouleteSelection(pop.population, false, 0, 0);
			if (i == 0) {
				firstGeneration += pop.maxOnesFitness(pop.population);
			}else if (i == 1){
				secondGeneration += pop.maxOnesFitness(pop.population);
			}else {
				thirdGeneration += pop.maxOnesFitness(pop.population);
			}
		}
		}
		System.out.println("Running 1000 times, average fitness for first generation is "+firstGeneration/1000);
		System.out.println("Running 1000 times, average fitness for second generation is "+secondGeneration/1000);
		System.out.println("Running 1000 times, average fitness for second generation is "+thirdGeneration/1000);
	}

}
