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
//	public static void q1(int k, LinkedList<Integer[]> trainData) {
		/* array holding classification results */
//		int[] res = new int[trainData.size()];
		/* iterator for the data */
//		Iterator<Integer[]> it = trainData.iterator();

//		for (int i = 0; i < trainData.size(); i++) {
//			res[i] = classify(k, it.next());
//		}
//	} 
/* Method to get most popular element in an integer array */
	public static int getPopularElement(int[] element)
	{
	  int count = 1 ;
	  int tempCount;
	  int popular = element[0];
	  int temp = 0;
	  for (int i = 0; i < element.length - 1; i++)
	  {
	    temp = element[i];
	    tempCount = 0;
	    for (int j = 1; j < element.length; j++)
	    {
	      if (temp == element[j])
	        tempCount++;
	    }
	    if (tempCount > count)
	    {
	      popular = temp;
	      count = tempCount;
	    }
	  }
	  return popular;
	}

	public static int q1(int k, LinkedList<Integer[]> trainData, LinkedList<Integer[]> testData) {
		Pair[] resNeigh = new Pair[k];
		Pair res;
		Pair resHolder = null;
		int[] train;
		int[] test;
		int place = 0;
		int holder = 0;
		int errors = 0;
		int[] labelArray = new int[k];
		int label = 0;
		/* Loops through all of the test points */
		for(int i =0; i < testData.size(); i++){
			/* Loop through all the training points */
			for(int j = 0; j < trainData.size(); j++) {
				/* Calculate the distance */
				res = calcDistanceAndLabel(trainData.get(j), testData.get(i));
				/* Adjust the array of Pairs to have the k least amount of distance */
				while(place != k){
					if(resHolder == null) {
						if(resNeigh[place] == null || res.getDist() < resNeigh[place].getDist() ){
							if(place == k-1 || resNeigh[place] == null) {
								resNeigh[place] = res;
							}
							else{
								resHolder = resNeigh[place];
								resNeigh[place] = res;
							}
						}
					}
					else if(resHolder != null) {
						if(place == resNeigh.length-1) {
							resHolder = null;
						}
						else{
							res = resHolder;
							resHolder = resNeigh[place];
							resNeigh[place] = res;
						}
					}
					place++;
				}
				place = 0;
			}
			/* Extracts the label for the nearest neighbors */
			for(int l = 0; l< k ; l++){
				labelArray[l] = resNeigh[l].getLabel();
				//System.out.println(resNeigh[l].getLabel());
			}
			/* get the popular label from the array */
			label = getPopularElement(labelArray);
			//System.out.println(testData.get(i)[testData.get(i).length-1]);
			/* Check if it is an error */
			if(label != testData.get(i)[testData.get(i).length-1]){
				errors++;
			}
			/* Resets the array of Pairs for next check */
			for(int reset = 0; reset < resNeigh.length; reset++) {
				resNeigh[reset] = null;
			}
		}
		return errors;
	}

	/* main function */
	public static void main(String[] args) {
		LinkedList<Integer[]> trainData, testData, valiData;
		int result = 0;

		/* read files */
		File trainFile = new File("hw2train.txt");
		trainData = read(trainFile);
		File testFile = new File("hw2test.txt");
		testData = read(testFile);
		File valiFile = new File("hw2validate.txt");
		valiData = read(valiFile);

	/*	Integer[] x = {0,0,0};
		Integer[] y = {0,0,0};

		System.out.println(calcDistanceAndLabel(x, y).getDist()); */

	/*	int count = 0;
		for(int i = 0; i < trainData.get(0).length; i++)
		{
			System.out.println(trainData.get(0)[i]);
			count++;
		}
		System.out.println(count); */
		/* Q1 */
		System.out.println("problem 3.1:\n");
		int[] k = {1, 3, 5, 11, 16, 21};
		for (int i = 0; i < k.length; i++) {
			System.out.println("k=" + k[i]+ "\n");
			result = q1(k[i], trainData, trainData);
			System.out.println("Training error: "+ (double)result/(double)trainData.size());
			result = q1(k[i], trainData, valiData);
			System.out.println("Validation error: "+ (double)result/(double)valiData.size());
			result = q1(k[i], trainData, testData);
			System.out.println("Test error: "+ (double)result/(double)testData.size() + "\n");
		}
//		System.out.println((double)result/(double)trainData.size());

		/* Q2 */
		System.out.println("problem 3.2:\n");
	}
}