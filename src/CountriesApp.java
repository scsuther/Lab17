import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;




public class CountriesApp {

	// Note: NIO uses the Path class, not just a file path String.
	private static Path filePath = Paths.get("Countries.txt");

	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);

		while (true) {
			System.out.print("Enter a command (list, add, quit): ");
			String command = scnr.nextLine();
			if (command.equals("quit")) {
				break;
			} else if (command.equals("list")) {
				List<Country> things = readFile();
				int i = 1; 
				for (Country thing : things) {
					System.out.println(i++ + ". " + thing);
				}
			} else if (command.equals("add")) {
				Country Country = getCountryFromUser(scnr);
				System.out.println("Adding " + Country);
				appendLineToFile(Country);
			} else {
				System.out.println("Invalid command.");
			}
		}
		System.out.println("Goodbye.");
		scnr.close();
	}
	
	private static Country getCountryFromUser(Scanner scnr) {
		// TODO #1 adjust this for your class, not Country
		String name = Validator.getString(scnr, "Enter country name:");
		int population = Validator.getInt(scnr, "Enter population:");
		//boolean fit = Validator.getYesNo(scnr, "Does the shoe fit? (yes/no)? ");
		return new Country(name, population);
	}

	/**
	 * Read all the objects from a file and store them in a List.
	 */
	public static List<Country> readFile() {
		try {
			List<String> lines = Files.readAllLines(filePath);
			
			List<Country> Country = new ArrayList<>();
			for (String line : lines) {
				// #1 split the line based on the delimiter
				String[] parts = line.split("~~~");
				// #2 select each part based on index position and convert
				// to the correct type
				String name = parts[0];
				int population = Integer.parseInt(parts[1]);
				//boolean fit = Boolean.parseBoolean(parts[2]);
				// #3 use the data to create an object and add it to the list
				Country.add(new Country(name, population));
			}
			
			return Country;
		} catch (IOException e) {
			System.out.println("Unable to read file.");
			return new ArrayList<>();
		}
	}

	/**
	 * Add an object to the end of the file.
	 */
	public static void appendLineToFile(Country thing) {
		String line = thing.getName() + "~~~" + thing.getPopulation();		
		// TODO #3 add code here... convert your object to a string like
		// it should be as a line in the file. store it in the `line` variable.
		
		// Files.write requires a list of lines. Create a list of one line.
		List<String> lines = Collections.singletonList(line);
		try {
			Files.write(filePath, lines, StandardOpenOption.CREATE,
					StandardOpenOption.APPEND);
		} catch (IOException e) {
			System.out.println("Unable to write to file.");
		}
	}

}