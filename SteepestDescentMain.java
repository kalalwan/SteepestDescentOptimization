
import java.util.*;
import java.io.*;

public class Pro5_alalwank {

	// global scanner object
	public static Scanner scan = new Scanner(System.in);
	
	// method for printing the main menu
	public static void displayMenu () {
		
		System.out.println("   JAVA POLYNOMIAL MINIMIZER (STEEPEST DESCENT)");
		System.out.println("L - Load polynomials from file");
		System.out.println("F - View polynomial functions");
		System.out.println("C - Clear polynomial functions");
		System.out.println("S - Set steepest descent parameters");
		System.out.println("P - View steepest descent parameters");
		System.out.println("R - Run steepest descent algorithms");
		System.out.println("D - Display algorithm performance");
		System.out.println("X - Compare average algorithm performance");
		System.out.println("Q - Quit\n");
	}
	
	// method for getting the input from the user
	public static int getMenuInput () {
		
		int choice = 0;
		boolean flag = true;
		
		while (flag) {
		
			displayMenu();
			
			System.out.print("Enter choice: ");
			
			try {
				
				String temp = scan.nextLine();
				
				if (temp.equals("L") || temp.equals("l")) {
					
					choice = 1;
					flag = false;
					
				} else if (temp.equals("F") || temp.equals("f")) {
					
					choice = 2;
					flag = false;
					
				} else if (temp.equals("C") || temp.equals("c")) {
					
					choice = 3;
					flag = false;
					
				} else if (temp.equals("S") || temp.equals("s")) {
					
					choice = 4;
					flag = false;
					
				} else if (temp.equals("P") || temp.equals("p")) {
					
					choice = 5;
					flag = false;
					
				} else if (temp.equals("R") || temp.equals("r")) {
					
					choice = 6;
					flag = false;
					
				} else if (temp.equals("D") || temp.equals("d")) {
					
					choice = 7;
					flag = false;
					
				} else if (temp.equals("X") || temp.equals("x")) {
					
					choice = 8;
					flag = false;
					
				} else if (temp.equals("Q") || temp.equals("q")) {
					
					choice = 9;
					flag = false;
					
				} else {
					
					System.out.println("\nERROR: Invalid menu choice!");	
				}
			}
			catch (Exception e) {
				
				System.out.println("\nERROR: Invalid menu choice!");
			}
		
			System.out.println();
		}
		
		return choice;
	}
	
	// methods for loading polynomials
	public static boolean loadPolynomialFile(ArrayList <Polynomial> P) {
		
		//create the temporary ArrayList for storing the values of the coefficients
		ArrayList <Double []> temp = new ArrayList<>();
		
		// get the filename from the user
		// get the filename from the user
		System.out.print("Enter file name (0 to cancel): ");
		String path = scan.nextLine();
		
		// if the user inputs zero then the process is cancelled
		if (path.contentEquals("0")) {
			
			System.out.println("\nFile loading process canceled.\n");
			return false;
		}
		
		boolean flag = true;
		String line = "";
		int polyNum = 1;
		
		int counter = 0;
		
		//create the buffered reader that reads stuff from the file
		try {
				BufferedReader br = new BufferedReader (new FileReader(path));
				
				while ((line = br.readLine()) != null) {
					
					
					//loop through the stuff before the asterisks
					while (flag) {
						
						String[] check = line.split(",");
						
						try {
							
							Double.parseDouble(check[0]);
							
							//turn check into an array of doubles
							
							Double [] checkDouble = new Double[check.length];
							
							for (int i = 0; i < check.length; i++) {
								
								checkDouble[i] = Double.parseDouble(check[i]);
								
							}
							
							// add checkDouble array into temporary ArrayList
							
							temp.add(checkDouble);
							
							line = br.readLine();
							
							if (line == null) {
								
								flag = false;
							}
							
						}
						
						catch (NumberFormatException e) {
							
							// flag must be false to stop the loop for an iteration
							flag = false;
							
							// find the number of variables in that polynomial
							int n = temp.size();
							
							// find the degree of the polynomials
							int degree = 0;
							int degreeTemp = 0;
							boolean degreeChange = false;
							
							for (int i = 0; i < temp.size(); i++) {
								
								if (degree == 0 && degreeTemp == 0) {
									
									degree = temp.get(i).length;
									degreeTemp = temp.get(i).length;
									
								} else {
									
									degree = temp.get(i).length;
								}
								
								// check to see if a change has been made
								
								if (degree != degreeTemp) {
									
									degreeChange = true;
								}
							}
							
							//if the polynomial is inconsistent, make degree negative 1
							if (degreeChange == true) {
								
								degree = -1;
								
								System.out.printf("\nERROR: Inconsistent dimensions in polynomial %d!\n", polyNum);
								
							} else {
								
								//make a temporary 2D array for the polynomial coefficients
								double[][] tempCoefs = new double[n][degree];
								
								// add the n and the degree to a polynomial
								Polynomial Poly = new Polynomial(n, degree, tempCoefs);
								
								Poly.setDegree(degree - 1);
								Poly.setN(n);
								
								// set the coefficients of the Poly to temp
								for (int i = 0; i < temp.size(); i++) {
									
									for (int j = (temp.get(i).length - 1), k = 0; j >= 0; j--, k++) {
										
										Poly.setCoef(i, j, temp.get(i)[k]);
									}
								}
								
								P.add(Poly); counter++;
							}
							
							temp = new ArrayList<>();
							
							polyNum++;
							
						}
						
					}
					
					if (line == null) {
						// find the number of variables in that polynomial
						int n = temp.size();
						
						// find the degree of the polynomials
						int degree = 0;
						int degreeTemp = 0;
						boolean degreeChange = false;
						
						for (int i = 0; i < temp.size(); i++) {
							
							if (degree == 0 && degreeTemp == 0) {
								
								degree = temp.get(i).length;
								degreeTemp = temp.get(i).length;
								
							} else {
								
								degree = temp.get(i).length;
							}
							
							// check to see if a change has been made
							
							if (degree != degreeTemp) {
								
								degreeChange = true;
							}
						}
						
						//if the polynomial is inconsistent, make degree negative 1
						if (degreeChange == true) {
							
							degree = -1;
							
							System.out.printf("\nERROR: Inconsistent dimensions in polynomial %d!\n", polyNum);
							
						} else {
							
							//make a temporary 2D array for the polynomial coefficients
							double[][] tempCoefs = new double[n][degree];
							
							// add the n and the degree to a polynomial
							Polynomial Poly = new Polynomial(n, degree, tempCoefs);
							
							Poly.setDegree(degree - 1);
							Poly.setN(n);
							
							// set the coefficients of the Poly to temp
							for (int i = 0; i < temp.size(); i++) {
								
								for (int j = (temp.get(i).length - 1), k = 0; j >= 0; j--, k++) {
									
									Poly.setCoef(i, j, temp.get(i)[k]);
								}
							}
							
							P.add(Poly); counter++;
						}
					}
					
					//System.out.println();
					
					flag = true;
				}
				
				//close the buffered reader
				br.close();
				
		} catch (IOException e) {
			// catches the file not found exception
			System.out.println("\nERROR: File not found!\n");
			
			return false;
		
		} 
		if (P.size() == 0) {
			
			return false;
		}
		
		System.out.printf("\n%d polynomials loaded!\n\n", counter);
		
		return true;
	}
	
	// method for getting integer
	public static int getInteger(String prompt, int LB, int UB) {
		
		int choice = 0;
		boolean flag = true;
		
		while (flag) {
			
			System.out.print(prompt);
			
			try {
				choice = Integer.parseInt(scan.nextLine());
				
				if (choice < LB || choice > UB) {
					
					if (UB == Integer.MAX_VALUE) {
						
						System.out.println("ERROR: Input must be an integer in ["+ LB + ", infinity]!\n");
					
					} else {
						
						System.out.println("ERROR: Input must be an integer in ["+ LB + ", "+ UB +"]!\n");
					}
				} else {
					
					flag = false;
				}
			} 
			catch (Exception e) {
				if (UB == Integer.MAX_VALUE) {
					
					System.out.println("ERROR: Input must be an integer in ["+ LB + ", infinity]!\n");
				
				} else {
					
					System.out.println("ERROR: Input must be an integer in ["+ LB + ", "+ UB +"]!\n");
				}
			}
		}
		return choice;
	}
	
	// method for getting double
	public static double getDouble(String prompt, double LB, double UB) {
		
		double choice = 0;
		boolean flag = true;
		
		while (flag) {
			
			System.out.print(prompt);
			
			try {
				choice = Double.parseDouble(scan.nextLine());
				
				if (choice < LB || choice > UB) {
					
					if (UB == Double.MAX_VALUE && LB == -1 * Double.MAX_VALUE) {
						
						System.out.println("ERROR: Input must be a real number in [-infinity, infinity]!\n");
					
					} else if (UB == Double.MAX_VALUE && LB == 0) {
						
						System.out.println("ERROR: Input must be a real number in [0.00, infinity]!\n");
					} else {
						
						System.out.printf("ERROR: Input must be a real number in [%.2f, %.2f]!\n\n", LB, UB);
					}
				} else {
					
					flag = false;
				}
			} 
			catch (Exception e) {
				
				if (UB == Double.MAX_VALUE && LB == -1 * Double.MAX_VALUE) {
					
					System.out.println("ERROR: Input must be a real number in [-infinity, infinity]!\n");
				
				} 
				if (UB == Double.MAX_VALUE && LB != -1 * Double.MAX_VALUE) {

					System.out.println("ERROR: Input must be a real number in [0.00, infinity]!\n");	
				}
				if (UB != Double.MAX_VALUE && LB != Double.MIN_VALUE){
					
					System.out.printf("ERROR: Input must be a real number in [%.2f, %.2f]!\n\n", LB, UB);
				} 
			}
		}
		
		return choice;
	}
	
	// method for printing out the polynomials
	public static void printPolynomials (ArrayList <Polynomial> P) {
		
		System.out.println("---------------------------------------------------------");
		System.out.println("Poly No.  Degree   # vars   Function");
		System.out.println("---------------------------------------------------------");
		
		//Loop through each polynomial
		
		for (int i = 0; i < P.size(); i++) {
			
			System.out.printf("%8d", i + 1);
			System.out.printf("%8d", P.get(i).getDegree());
			System.out.printf("%9d", P.get(i).getN());
			System.out.print("   ");
			P.get(i).print();
			
		}
		
		System.out.println();
		
		return;
	}
	
	// method for checking if polynomials are loaded
	public static boolean checkPolyLoaded (ArrayList <Polynomial> P) {
		
		if (P.size() > 0) {
			
			return true;
		}
		
		return false;
	}
	
	// method for getting all of the parameters for the different algorithms
	public static void getAllParams (SDFixed SDF, SDArmijo SDA, SDGSS SDG) {
		
		// get the algorithm parameters for the fixed line search
		System.out.println("Set parameters for SD with a fixed line search:");
		SDF.getParamsUser();
		
		// get the algorithm parameters for the Armijo line search
		System.out.println("Set parameters for SD with an Armijo line search:");
		SDA.getParamsUser();
		
		// get the algorithm parameters for the golden section line search
		System.out.println("Set parameters for SD with a golden section line search:");
		SDG.getParamsUser();
		
		return;
	}
	
	// method for printing out all of the algorithm parameters
	public static void printAllParams (SDFixed SDF, SDArmijo SDA, SDGSS SDG) {
		
		// fixed line search parameters
		SDF.print();
		
		// Armijo line search parameters
		SDA.print();
		
		// Golden line search parameters
		SDG.print();
		
		return;
	}
	
	// method for running all polynomials through all algorithms
	public static void runAll (SDFixed SDF, SDArmijo SDA,SDGSS SDG, ArrayList<Polynomial> P) {
		
		System.out.println("Running SD with a fixed line search:");
		for (int i = 0; i < P.size(); i++) {
			
			SDF.run(i, P.get(i));
		}
		System.out.println("\nRunning SD with an Armijo line search:");
		for (int i = 0; i < P.size(); i++) {
			
			SDA.run(i, P.get(i));
		}
		System.out.println("\nRunning SD with a golden section line search:");
		for (int i = 0; i < P.size(); i++) {
			
			SDG.run(i, P.get(i));
		}
	}
	
	// method for printing the results of running the algorithms
	public static void printAllResults (SDFixed SDF, SDArmijo SDA, SDGSS SDG, ArrayList<Polynomial> P) {
		
		// print out the detailed results for the fixed line search
		System.out.println("Detailed results for SD with a fixed line search:");
		SDF.printAll();
		System.out.println("Statistical summary for SD with a fixed line search:");
		SDF.printStats();
		System.out.println();
		
		// print out the detailed results for the Armijo line search
		System.out.println("Detailed results for SD with an Armijo line search:");
		SDA.printAll();
		System.out.println("Statistical summary for SD with an Armijo line search:");
		SDA.printStats();
		
		System.out.println();		
		// print out the detailed results for the golden section line search
		System.out.println("Detailed results for SD with a golden section line search:");
		SDG.printAll();
		System.out.println("Statistical summary for SD with a golden section line search:");
		SDG.printStats();
	}
	
	// method for comparing the results of the different algorithms
	public static void compare (SDFixed SDF, SDArmijo SDA, SDGSS SDG) {
		
		// print out the statistics without declaring a winner
		System.out.println("---------------------------------------------------");
		System.out.println("          norm(grad)       # iter    Comp time (ms)");
		System.out.println("---------------------------------------------------");
		System.out.printf("Fixed%15.3f%13.3f%18.3f\n", SDF.getAvgNorm(), SDF.getAvgIter(), SDF.getAvgComp());
		System.out.printf("Armijo%14.3f%13.3f%18.3f\n", SDA.getAvgNorm(), SDA.getAvgIter(), SDA.getAvgComp());
		System.out.printf("GSS%17.3f%13.3f%18.3f\n", SDG.getAvgNorm(), SDG.getAvgIter(), SDG.getAvgComp());
		System.out.println("---------------------------------------------------");
		
		// get the winner for the gradient norm
		
		String gradWinner = "";
		
		if ((SDF.getAvgNorm() <= SDA.getAvgNorm()) && (SDF.getAvgNorm() <= SDG.getAvgNorm())) {
			
			gradWinner = "Fixed";
		}
		
		if ((SDA.getAvgNorm() < SDF.getAvgNorm()) && (SDA.getAvgNorm() <= SDG.getAvgNorm())) {
			
			gradWinner = "Armijo";
		}
		
		if ((SDG.getAvgNorm() < SDF.getAvgNorm()) && (SDG.getAvgNorm() < SDA.getAvgNorm())) {
			
			gradWinner = "GSS";
		}
		
		// get the winner for the number of iterations
		
		String iterWinner = "";
		
		if ((SDF.getAvgIter() <= SDA.getAvgIter()) && (SDF.getAvgIter() <= SDG.getAvgIter())) {
			
			iterWinner = "Fixed";
		}
		
		if ((SDA.getAvgIter() < SDF.getAvgIter()) && (SDA.getAvgIter() <= SDG.getAvgIter())) {
			
			iterWinner = "Armijo";
		}
		
		if ((SDG.getAvgIter() < SDF.getAvgIter()) && (SDG.getAvgIter() < SDA.getAvgIter())) {
			
			iterWinner = "GSS";
		}
		
		// get the winner for the compile time 
		
		String compWinner = "";
		
		if ((SDF.getAvgComp() <= SDA.getAvgComp()) && (SDF.getAvgComp() <= SDG.getAvgComp())) {
			
			compWinner = "Fixed";
		}
		
		if ((SDA.getAvgComp() < SDF.getAvgComp()) && (SDA.getAvgComp() <= SDG.getAvgComp())) {
			
			compWinner = "Armijo";
		}
		
		if ((SDG.getAvgComp() < SDF.getAvgComp()) && (SDG.getAvgComp() < SDA.getAvgComp())) {
			
			compWinner = "GSS";
		}
		
		// print out the winners
		
		System.out.printf("Winner%14s%13s%18s\n", gradWinner, iterWinner, compWinner);
		System.out.println("---------------------------------------------------");
		
		// find the overall winner
		
		String overallWinner = "";
		
		if ((gradWinner == "Fixed") && (iterWinner == "Fixed") && (compWinner == "Fixed")) {
			
			overallWinner = "Fixed";
		}
		
		else if ((gradWinner == "Armijo") && (iterWinner == "Armijo") && (compWinner == "Armijo")) {
			
			overallWinner = "Armijo";
		}
		
		else if ((gradWinner == "GSS") && (iterWinner == "GSS") && (compWinner == "GSS")) {
			
			overallWinner = "GSS";
			
		} else {
			
			overallWinner = "Unclear";
		}
		
		System.out.println("Overall winner: " + overallWinner);
		System.out.println();
		
		return;
	}

	public static boolean checkHasResults(SDFixed SDF, SDArmijo SDA, SDGSS SDG) {
		
		if ((SDF.hasResults() == true) && (SDA.hasResults() == true) && (SDG.hasResults() == true)) {
			
			return true;
			
		} else {
			
			return false;
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		boolean flag = true;
		
		boolean dispResults = false;
		
		// create instances of all three of the algorithm types
		SDFixed SDF = new SDFixed();
		SDArmijo SDA = new SDArmijo();
		SDGSS SDG = new SDGSS();
		
		ArrayList <Polynomial> P = new ArrayList<>();
		
		while (flag) {
			
			int choice = getMenuInput();
			
			if (choice == 1) {
				//Load polynomials from file
				if (loadPolynomialFile(P) == false) {
					
					dispResults = true;
					
				} else {
					
					dispResults = false;
				}
			}
			
			if (choice == 2) {
				
				// view polynomial functions
				
				if (checkPolyLoaded(P) == true) {
					
					// print out the polynomials
					printPolynomials(P);
					
				} else {
					
					System.out.println("ERROR: No polynomial functions are loaded!\n");
				}
			}
			
			if (choice == 3) {

				P = new ArrayList<>();
				
				dispResults = false;
				
				System.out.println("All polynomials cleared.\n");
				
			}
			
			if (choice == 4) {
				
				// get steepest descent parameters from user
				getAllParams(SDF, SDA, SDG);
				
				if ((SDF.hasResults() == false) || (SDA.hasResults() == false) || (SDG.hasResults() == false)) {
					
					dispResults = false;
				}	
			}
			
			if (choice == 5) {
				
				// print out the steepest descent parameters
				printAllParams(SDF, SDA, SDG);
			}
			
			if (choice == 6) {
				
				// run steepest descent on each polynomial
				
				if (checkPolyLoaded(P) == true) {
					
					// run steepest descent on each polynomial
					
					// initialize the member arrays of SD's
					SDF.init(P);
					SDA.init(P);
					SDG.init(P);
					
					// loop through the polynomials and run them in order
					runAll(SDF, SDA, SDG, P);
					
					// print out the done message
					System.out.println("\nAll polynomials done.\n");
					
					dispResults = true;
					
				} else {
					
					System.out.println("ERROR: No polynomial functions are loaded!\n");
				}
			}
			
			if (choice == 7) {
				
				// display the algorithm performance summary
				
				if (dispResults == true) {
					
					// display the algorithm performance summary
					
					printAllResults(SDF, SDA, SDG, P);
					
				} else {
					
					// print out the error message
					System.out.println("ERROR: Results do not exist for all line searches!\n");
				}
			}
			
			if (choice == 8) {
				
				// compare average algorithm performance
				if (dispResults == true) {
					
					compare(SDF, SDA, SDG);
					
				} else {
					
					System.out.println("ERROR: Results do not exist for all line searches!\n");
				}
			}
			
			if (choice == 9) {
				
				flag = false;
				System.out.print("Arrivederci.\n");
			}		
		}
		
	}

}
