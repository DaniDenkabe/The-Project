import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class Intake {
	public static void main(String[] args) throws Exception {
		EffectsModel model = new EffectsModel();
		int[] lastValue = new int[25];
		for (int i = 0; i < 25; i++) {
			lastValue[i] = 0;
		}
		int count = 0;
		System.out.println("Running...");
		while (true) {
			try {
				File myObj = new File("comms.txt");
				Scanner myReader = new Scanner(myObj);
				while (myReader.hasNextLine()) {
					int line = Integer.parseInt(myReader.nextLine());
					int data = line / 100;
					int origin = line % 100;
					// Dealing with rotary encoders
					if ((origin > 0 && origin < 5) || (origin > 6 && origin < 10) || (origin > 10 && origin < 12) || (origin > 12 && origin < 17) || (origin > 17 && origin < 19) || (origin > 19 && origin < 25)) {
						if (data - lastValue[origin] != 0) {
							System.out.println("Java Program change parameter for " + origin + ": " + (data - lastValue[origin]));
							lastValue[origin] = data;
							model.changeVariable(origin, data);
						}
					// Dealing with potentiometers
					} else if (origin >= 25 && origin < 31) {
						
					// Dealing with buttons
					} else if (origin >= 31 && origin < 63) {
						if (count != data) {
							System.out.println("Java Program reads button " + origin + " pressed");
							count = data;
							if (origin == 31) {
								model.setSelected(0);
							}
						}
					}
				}
				myReader.close();
			} catch (FileNotFoundException e) {
				System.out.println("An error occurred.");
				e.printStackTrace();
			}
		}
	}
}
