import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.lang.*;

public class HillClimbing {

//	initialize the initialState, currentState and charState in matrix
//	initial state: each row will contain int 1-4 without any duplicates
//	charState: transform the input file to a 2D matrix
	
	private static int[][] initialState = new int[4][4];
	private static int[][] currentState = new int[4][4];
	private static char[][] charState = new char[4][4];
//	initialize constant row and column number
	private static int row = 4;
	private static int column = 4;
//	initialize constant string
	private static String alphabet = "abcdefghijklmnopqrstuvwxyz";
	public HillClimbing() {
		// TODO Auto-generated constructor stub
	}
	
	private static void TestHarness() {
		Scanner keyboard = new Scanner(System.in);
    	System.out.println("Please enter your test input file: ");
    	String filename = keyboard.nextLine();
    	File testFile = new File("");
		BufferedReader reader=null;
		try {
			reader=new BufferedReader(new FileReader(filename));
//			read the first line of the file which is "4"
			String currentLine = reader.readLine();
//			Keep reading the rest of the file line by line
//			initialize the row number
			int a= 0;
//			index of the alphabet string
			int alphaIndex = 0;
			while((currentLine = reader.readLine())!= null) {
//				i is the column number
				for(int i = 0; i< currentLine.length(); i++) {
					if (currentLine.charAt(i) == '*') {
						charState[a][i] = alphabet.charAt(alphaIndex);
						alphaIndex++;
					}else {
						charState[a][i] = currentLine.charAt(i);
					}
				}
				a++;
			}
			
		} catch (Exception e) {
            System.err.println("Ooops!  I can't seem to read the file on the standard input!");
            System.exit(1);
	    }
	}
	


	public static void main(String[] args) {
		HCAlgorithm();
	}
	
	public static void HCAlgorithm() {
		initialize();
		System.out.println("This is the initial state: ");
		printmatrix(initialState);
		System.out.println("This is the total conflicts for this state: ");
		int totalcost = evaluation(initialState);
		System.out.println(totalcost);
		int iteration = 0;
		System.out.println("Iterations so far: ");
		System.out.println(iteration);
		iteration++;
		copyState();
		int[] returnval = successorfunction(totalcost, iteration);
		while(returnval[0]>0) {
			returnval = successorfunction(returnval[0], returnval[1]);
		}
		
	}
	
//	swap values who are in the same row
	public static int[] successorfunction(int cost, int iteration) {
		int[] returnval = new int[2];
//		compare initial state and char state. 
//		replace the vectors(letters) with the corresponding int from initialState
//		filled the rest of the matrix with 0
		int [][]vector = new int[4][4];
		for(int i = 0; i<row; i++) {
			 for (int j = 0; j<column; j++) {
				 if(alphabet.indexOf(charState[i][j])!=-1){
					 vector[i][j] = initialState[i][j];
				 }
			 }
		 }
//		set the cost to initial cost
//		set iteration begins with 1
		int ecost = cost;
		
		int iter = iteration;
//		iterate through each row
		for(int i = 0; i<row; i++) {
			
//			check if the current row only contains 0 or only contains one 0
//			if so, there is no operation needed for this row
			int occ = countOccurrences(vector[i],0);
			if(occ<3) {
				for(int j = 0; j<column; j++) {
					if(vector[i][j]!=0) {
						for (int k = j+1; k<column;k++) {
//							swap only if the cost is lower than the existing one. Otherwise swap back
							if(vector[i][k]!=0) {
//								printmatrix(currentState);
								currentState[i][j]=vector[i][k];
								currentState[i][k]=vector[i][j];
//								System.out.print(currentState[i][j]);
//								System.out.print(currentState[i][k]);
//								System.out.println();
//								System.out.println(evaluation(currentState));
//								System.out.println();
								if(evaluation(currentState)<ecost) {
									ecost = evaluation(currentState);
									returnval[0]=ecost;
									System.out.println("This is the next state: ");
									printmatrix(currentState);
									System.out.println("This is the total conflicts for this state: ");
									System.out.println(ecost);
									System.out.println("Iterations so far: ");
									System.out.println(iteration);
									iter++;
									returnval[1]=iter;
									return returnval;
								}else {
									currentState[i][j]=vector[i][j];
									currentState[i][k]=vector[i][k];
								}
							}
						}
					}
				}
			}
			
//			if(occ==1) {
//				for(int j = 0; j<column; j++) {
//					if(vector[i][j]!=0) {
//						for (int k = j+1; k<column;k++) {
//							if(vector[i][k]!=0) {
//								state[i][j]=vector[i][k];
//								state[i][k]=vector[i][j];
//								if(evaluation(state)<ecost) {
//									ecost = evaluation(state);
//									System.out.println("This is the next state: ");
//									printmatrix(state);
//									System.out.println("This is the total conflicts for this state: ");
//									System.out.println(ecost);
//									System.out.println("Iterations so far: ");
//									System.out.println(iteration);
//									iteration++;
//									return ecost;
//								}
//							}
//						}
//					}
//				}
//			}
//			if (occ!=4 && occ!=3) {
////				loop through columns 
//				int col = 0;
//				while(col < 4) {
//					if(vector[i][col]!=0) {
//						for(int k = col+1; k<col; k++) {
//							System.out.println("Hi");
//							int tmp = vector[i][k];
//							state[i][col] = tmp;
//							int tmp2 = vector[i][col];
//							state[i][k] = tmp2;
//							if(evaluation(state)<ecost) {
//								ecost = evaluation(state);
//								System.out.println("This is the next state: ");
//								printmatrix(state);
//								System.out.println("This is the total conflicts for this state: ");
//								System.out.println(ecost);
//								System.out.println("Iterations so far: ");
//								System.out.println(iteration);
//								iteration++;
//								return ecost;
//							}
//							
//						}
//					}
//					col++;
//				}
			
//			}
			
		}
		return returnval;
		
		
		
	}
	
	private static void copyState() {
		for(int i = 0; i<row; i++) {
			for(int j = 0; j<column; j++) {
				currentState[i][j]=initialState[i][j];
			}
		}
	}
//	initialize the initial state from the charState
	private static void initialize() {
//		read the input file and create the char state (state vectors + given ints)
		TestHarness();	
		
//		loop through the 2D matrix charState
		for(int i = 0; i < row; i++) {
//			initialize a string contains 1234
			String nums = "1234";
//			loop through each column
			for (int j = 0; j< column; j++) {
				char c = charState[i][j];
//				if the current char is 1 or 2 or 3 or 4, remove the current char from the nums str
//				set the initializeState at the corresponding position to the current char (but in int form)
				if (nums.indexOf(c)!=-1) {
					initialState[i][j]=Character.getNumericValue(c);
					nums = removeCharAt(nums, nums.indexOf(c));
				}
			}
			
//			loop through each column again to fill the blank spots with 1-4 ints but making sure there is no duplicates in each row
			String fournumbers = "1234";
			int strindex = 0;
			for (int j = 0; j< column; j++) {
				char c = charState[i][j];
				if ((fournumbers.indexOf(c)==-1)) {
					initialState[i][j]=Character.getNumericValue(nums.charAt(strindex));
					strindex++;
				}
				
			}
			
		}
		
		
		
	}
	
//	no need to worry about row cost since when we initialize the state we made sure there are no duplicates in each row
	public static int evaluation(int[][] state) {
		int totalcost = 0;
		
		int columncost = column_cost(state);
		int boxcost = box_cost(state);
		
		totalcost = columncost + boxcost;
		
		return totalcost;
	}
	
	
//	calculate column cost
	public static int column_cost(int[][] state) {
		int cost = 0;
//		transform the row by column matrix to column by row
		int[][]column_e = new int[4][4];
		for(int i = 0; i<row; i++) {
			 for (int j = 0; j<column; j++) {
					column_e[j][i] = state[i][j];
			 }
		 }
		
		for(int i = 0; i<row; i++) {
			for (int j = 0; j<column; j++) {
				int count = countOccurrences(column_e[i], column_e[i][j])-1;
				cost+=count;
			}
		}
		return cost;
	}
	
	public static int box_cost(int[][] state) {
		int cost = 0;
		int[][] boxes = new int[4][4];
//		set 1st box(top left) = 1st array
		int col1 = 0;
		while(col1<4) {
			for(int i = 0; i < 2; i++) {
				for(int j = 0; j < 2; j++) {
					boxes[0][col1] = state[i][j];
					col1++;
				}
			}
		}
//		set 2nd box(top left) = 2nd array
		int col2 = 0;
		while(col2<4) {
			for(int i = 0; i < 2; i++) {
				for(int j = 2; j < 4; j++) {
					boxes[1][col2] = state[i][j];
					col2++;
				}
			}
		}
		
//		set 3rd box(top left) = 3rd array
		int col3 = 0;
		while(col3<4) {
			for(int i = 2; i < 4; i++) {
				for(int j = 0; j < 2; j++) {
					boxes[2][col3] = state[i][j];
					col3++;
				}
			}
		}
//		set 4th box(top left) = 4th array
		int col4 = 0;
		while(col4<4) {
			for(int i = 2; i < 4; i++) {
				for(int j = 2; j < 4; j++) {
					boxes[3][col4] = state[i][j];
					col4++;
				}
			}
		}
		
		for(int i = 0; i<row; i++) {
			for (int j = 0; j<column; j++) {
				int count = countOccurrences(boxes[i], boxes[i][j])-1;
				cost+=count;
			}
		}
		return cost;
	}
	
//	a function that allows me to subtract a char from the string given its index
	 public static String removeCharAt(String s, int pos) {
	      return s.substring(0, pos) + s.substring(pos + 1);
	 }
	 
//	 a function that counts element x's occurences in arr[]
	 public static int countOccurrences(int arr[], int x){
        int res = 0;
        for (int i=0; i<arr.length; i++) {
        	if (x == arr[i]) {
        		res++;
        	} 
        }
        return res;
            
    }
	 
	 
//	 print out matrix for debugging purposes
	 public static void printmatrix(int[][] state) {
		 for(int i = 0; i<row; i++) {
			 for (int j = 0; j<column; j++) {
				 System.out.print(state[i][j]);
			 }
			 System.out.println();
		 }
	 }

}
