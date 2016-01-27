/* ----------------------------------------------------------------------------
 * Alicia Ning A10796746
 * Gabriel Gaddi A10851046
 * CSE 151 - Chaudhuri
 * 28 January 2016
 * --------------------------------------------------------------------------*/

import java.io.*;
import java.util.*;

class Pair<dist, label> {         
    public final double dist;
    public final int label;

    public Pair(double dist, int label) {         
        this.dist= dist;
        this.label= label;
     }
     public double getDist(){
     	return dist;
     }
     public int getLabel(){
     	return label;
     }
 }
public class hw2 {
	/* function to read files */
	public static LinkedList<Integer[]> read(File file) {
		LinkedList<Integer[]> res = new LinkedList<Integer[]>();
		String line = null;

		try {
			FileReader fReader = new FileReader(file);
			BufferedReader bReader = new BufferedReader(fReader);

			/* while there are still lines to read */
			while((line = bReader.readLine()) != null) {
				/* split each line by spaces */
				String[] vals = line.split(" ");
				/* array to store values */
				Integer[] intVals = new Integer[vals.length];

				/* store values as integers */
				for(int i = 0; i < intVals.length; i++) {
					intVals[i] = Integer.parseInt(vals[i]);
				}

				/* add to LL */
				res.add(intVals);
			}

			/* close file */
			bReader.close();
		} catch(FileNotFoundException ex) {
			System.out.println("Failed to open file");
		} catch(IOException ex) {
			System.out.println("Error reading file");
		}

		return res;
	}

	/* helper function to part 1 - classifier */
	public static int classify (int k, Integer[] data) {
		return 0;
	}

	/* Helper to calculate distance between points with labels*/
	public static Pair calcDistanceAndLabel(Integer[] first, Integer[] second) {
		Double distance = 0.0;
		int value = 0;
		for(int i = 0; i < first.length-1; i++){
			value = value + (int)Math.pow((second[i] - first[i]),2);
		}
		distance = Math.sqrt(value);
		return new Pair(distance, first[first.length-1]);
	}
	/* function for part 1 */
	public static void q1(int k, LinkedList<Integer[]> trainData) {
		/* array holding classification results */
		int[] res = new int[trainData.size()];
		/* iterator for the data */
		Iterator<Integer[]> it = trainData.iterator();

		for (int i = 0; i < trainData.size(); i++) {
			res[i] = classify(k, it.next());
		}
	}

	/* main function */
	public static void main(String[] args) {
		LinkedList<Integer[]> trainData, testData, valiData;

		/* read files */
		File trainFile = new File("hw2train.txt");
		trainData = read(trainFile);
		File testFile = new File("hw2test.txt");
		testData = read(testFile);
		File valiFile = new File("hw2validate.txt");
		valiData = read(valiFile);

		/* Q1 */
		System.out.println("problem 3.1:\n");
		int[] k = {1, 3, 5, 11, 16, 21};
		for (int i = 0; i < k.length; i++) {
			q1(k[i], trainData);
		}

		/* Q2 */
		System.out.println("problem 3.2:\n");
	}
}