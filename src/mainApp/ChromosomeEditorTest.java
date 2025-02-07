package mainApp;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Random;

import javax.swing.JFileChooser;

import org.junit.Test;

public class ChromosomeEditorTest {
	ChromosomeEditor CE = new ChromosomeEditor();

	@Test
	public void testReadFile() {
		String output = null;
		String path = null;
		JFileChooser jf = new JFileChooser();
		jf.setCurrentDirectory(new File("./src/mainApp/ExampleFiles"));
		int filePath = jf.showSaveDialog(null);
		if (filePath == JFileChooser.APPROVE_OPTION) {
			path = jf.getSelectedFile().getAbsolutePath();	
		}
		try {
			output = CE.readFile(path);
		}catch (FileNotFoundException | InvalidBitStringInformationException | NoSuchElementException e1) {
			fail();
		}
		try {
			assertEquals(output, CE.readFile(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidBitStringInformationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testRandomProcess() {
		boolean check = CE.randomProcess(0.5);
		if (check == true) {
			return;
		}else {
			return;
		}
	}
}
