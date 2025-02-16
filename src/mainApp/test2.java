package mainApp;

public class test2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Population pop = new Population();
		pop.generateSpecialPopulation(100, 10, 0);
		for (int i = 0; i < 4; i++) {
			System.out.println(pop.maxOnesFitness(pop.population));
			pop.RankSelection(pop.population, false, 0, 0);
		}
	}

}
