package mainApp;

/**
 * Class: MainApp
 * @author W25R101
 * <br>Purpose: Top level class for CSSE220 Project containing main method 
 * <br>Restrictions: None
 */
public class MainApp {
	ChromosomeEditor chromosomeEditor = new ChromosomeEditor();
	Population population = new Population();
	EvolutionViewer evolutionViewer = new EvolutionViewer();

	private void runApp() {
		evolutionViewer.runEvolutionViewer();
	} 

	/**
	 * ensures: runs the application    
	 * @param args unused
	 */
	public static void main(String[] args) {
		MainApp mainApp = new MainApp();
		mainApp.runApp();
		
	}
}