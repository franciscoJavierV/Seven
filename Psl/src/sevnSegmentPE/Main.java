package sevnSegmentPE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class Main {

	public static void main(String[] arg) {
		processing(open("C:\\Users\\franj\\eclipse-workspace\\Psl\\src\\sample_numbers.txt"));
	}

	// this method will get the size for the matrix
	public static int getSize(String x) {
		int sub_final = 0, sub_inicial = 4;
		int size = 0;
		for (int j = 0; j < x.length() - 1; j++) {
			if (sub_final <= x.length() - 1) {
				sub_inicial = sub_final;
				sub_final = sub_final + 4;
				size++;
			}
		}
		return size;
	}

	// this method will open the file and put every number inside the dynamic list
	public static ArrayList<String> open(String path) {
		// variables needed to read the file
		File file = null;
		FileReader fr = null;
		BufferedReader br = null;

		// Dynamic list to save every character of the file
		ArrayList<String> line_list = new ArrayList<>();

		try {
			file = new File(path); // looking for the file
			fr = new FileReader(file); // fr will be the file reader
			br = new BufferedReader(fr); // br is the reader for fr
			String line; // line will keep the content on the file
			while ((line = br.readLine()) != null) { // read the file and meanwhile save the lines into line
				line_list.add(line); // add all the file information in line_list
			}
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		finally 
		{
			try
			{
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return line_list;
	}

	// this is the processing engine, here is where the numbers in the file are
	// processed and converted into the output
	public static void processing(ArrayList<String> line_list) {
		int sub_inicial = 0;
		int sub_final = 0;
		boolean three = false;
		boolean two = false;
		boolean five = false;
		boolean zero = false;
		// declaring the variables
		String[][] matriz;
		int contador;
		int colums = getSize(line_list.get(0).toString()); // columns will be the length for the matrix
		matriz = new String[line_list.size()][colums]; // the matrix will hold all the numbers

		// this for iterate the list and put every thing in the matrix without changing
		// the order

		for (int i = 0; i < line_list.size(); i++) {
			sub_inicial = 0;
			sub_final = 4;

			for (int j = 0; j < line_list.get(i).length() - 1; j++) {
				if (sub_final <= line_list.get(i).length()) { // here is were i control the length of the number inside
																// the matrix
					matriz[i][j] = (line_list.get(i).substring(sub_inicial, sub_final));
					sub_inicial = sub_final;
					sub_final = sub_final + 4;
				}
			}
		}
		// this for will count the _ - and | inside the matrix to see the difference
		// between each number
		for (int i = 0; i < colums - 1; i++) {
			int under = 0; // under will hold how many underscore characters is in the matrix
			contador = 0;
			for (int j = 0; j < matriz.length; j++) {
				contador = matriz[j][i].toString().trim().length() + contador; // contador will hold the size of each
																				// number
				if (matriz[j][i].contains("_")) {
					under++;
				}
				if (!matriz[2][i].contains("_|")) { // this is how i know 2 is not equal to 3,5
					two = true;
				}
				if (matriz[2][i].contains("_|") && matriz[1][i].contains("|_")) { // this is how i know 5 is not equal
																					// to 2,5
					five = true;
				}
				if (matriz[2][i].contains("_|") && matriz[1][i].contains("_|")) { // this is how i know 3 is not equal
																					// to 2,5
					three = true;
				}
			}
			if (matriz[1][i].contains("| |")) { // here i know it is a 0
				zero = true;
			}
			// in this switch the corresponding numbers will be set into the output
			switch (contador) { // contador is the key because that way 4 numbers are discover
			case 2: // when contador is 2 the number is 1
				System.out.print("1 ");
				break;
			case 3:
				System.out.print("7 ");
				break;
			case 4:
				System.out.print("4 ");
				break;
			case 5:
				if (under == 1) { // when the number only has 1 under_score character it is the 9
					System.out.print("9 ");
				} else if (two && three == false) { // when boolean two is true then it is a 2 in the output
					System.out.print("2 ");
				} else if (three == true && five == false) { // when boolean three is true then it is a 3 in the output
					System.out.print("3 ");
				} else if (five == true)
					System.out.print("5 "); // when boolean five is true then it is a 5 in the output

				break;
			case 6:
				if (under == 2) { // if the number has 2 underscore characters the it is a 6 in the output
					System.out.print("6 ");
				} else { // otherwise it is a 0
					System.out.println("0 ");
				}
				break;
			case 7:
				if (zero == false) { // when contador is 7 and zero is false the number in the output will be 8
					System.out.print("8 ");
				} else
					System.out.print("0 "); // when zero is true the number in the output will be 0
				break;

			default:

				break;
			}
		}
	}
}
