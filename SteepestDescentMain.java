import java.util.*;

public class Pro3_alalwank {
	
	//scanner object for taking in user input
	public static Scanner scan = new Scanner(System.in);
	
	//method for printing out the main menu
	public static void displayMenu () {
		
		System.out.println("   JAVA POLYNOMIAL MINIMIZER (STEEPEST DESCENT)");
		System.out.println("E - Enter polynomial function");
		System.out.println("F - View polynomial function");
		System.out.println("S - Set steepest descent parameters");
		System.out.println("P - View steepest descent parameters");
		System.out.println("R - Run steepest descent algorithm");
		System.out.println("D - Display algorithm performance");
		System.out.println("Q - Quit\n");
	}
	
	//method for taking letter input and returning a number value
	public static int getMenuInput () {
		
		int choice = 0;
		boolean flag = true;
		
		while (flag) {
		
			displayMenu();
			
			System.out.print("Enter choice: ");
			
			try {
				
				String temp = scan.nextLine();
				
				if (temp.equals("E") || temp.equals("e")) {
					
					choice = 1;
					flag = false;
					
				} else if (temp.equals("F") || temp.equals("f")) {
					
					choice = 2;
					flag = false;
					
				} else if (temp.equals("S") || temp.equals("s")) {
					
					choice = 3;
					flag = false;
					
				} else if (temp.equals("P") || temp.equals("p")) {
					
					choice = 4;
					flag = false;
					
				} else if (temp.equals("R") || temp.equals("r")) {
					
					choice = 5;
					flag = false;
					
				} else if (temp.equals("D") || temp.equals("d")) {
					
					choice = 6;
					flag = false;
					
				} else if (temp.equals("Q") || temp.equals("q")) {
					
					choice = 7;
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
	
	//method for getting integer
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
	
	//method for getting double
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
						
						System.out.println("ERROR: Input must be a real number in ["+ LB + ", "+ UB +"]!\n");
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
					
					System.out.println("ERROR: Input must be a real number in ["+ LB + ", "+ UB +"]!\n");
				}
			}
		}
		
		return choice;
	}

	//method for getting polynomial details
	public static boolean getPolynomialDetails (Polynomial P) {
		
		int numVars = getInteger("Enter number of variables (0 to cancel): ", 0, Integer.MAX_VALUE);
		
		if (numVars == 0) {
			
			System.out.println("\nProcess canceled. No changes made to polynomial function.\n");
			return false;
		}
		
		int polyDeg = getInteger("Enter polynomial degree (0 to cancel): ", 0, Integer.MAX_VALUE);
		
		if (polyDeg == 0) {
			
			System.out.println("\nProcess canceled. No changes made to polynomial function.\n");
			return false;
		}
		
		P.setN(numVars);
		
		P.setDegree(polyDeg);
		
		P.init();
		
		for (int i = 0; i < P.getN(); i++) {
			
			System.out.printf("Enter coefficients for variable x%d: \n", i+1);
			
			for (int j = P.getDegree(); j >= 0; j--) {
				
				//System.out.printf("   Coefficient %d:", polyDeg - j + 1);
				P.setCoef(i, j, getDouble("   Coefficient " + (polyDeg - j + 1) + ": ", -1 * Double.MAX_VALUE, Double.MAX_VALUE));
			}
		}
		
		System.out.println("\nPolynomial complete!\n");
		
		return true;
	}
	
	//method for getting steepest descent parameters
	public static boolean getAlgorithmParams(SteepestDescent SD, int n) {
		
		SD.getParamsUser(n);
		
		if (SD.getEps() == 0 || SD.getMaxIter() == 0 || SD.getStepSize() == 0) {
			
			SD.setEps(0.001);
			SD.setMaxIter(100);
			SD.setStepSize(0.05);
			
			SD.init(n);
			
			for (int i = 0; i < n; i++) {
				
				SD.setXO(i, 1);
			}
			
			return false;
		}
		
		return true;
	}
	
	//method for printing the default algorithm parameters if they're not set
	public static void printDefaultParams() {
		
		System.out.println("Tolerance (epsilon): 0.001");
		System.out.println("Maximum iterations: 100");
		System.out.println("Step size (alpha): 0.05");
		System.out.println("Starting point (x0): ( 1.00 )\n");
	}
	
	//method for setting the default algorithm parameters if they're not set
	public static void setDefaultParams(SteepestDescent SD, int n) {
		
		if (SD.getEps() == 0 || SD.getMaxIter() == 0 || SD.getStepSize() == 0) {
			
			SD.setEps(0.001);
			SD.setMaxIter(100);
			SD.setStepSize(0.05);
			
			SD.init(n);
			
			for (int i = 0; i < n; i++) {
				
				SD.setXO(i, 1);
			}
		}
	}
	
	//method for printing the algorithm parameters
	public static void printAlgoParams(SteepestDescent SD) {
		
		SD.print();
	}
	
	//method for checking if the parameter dimensions are the same as the polynomial
	public static void fixDimension(SteepestDescent SD, Polynomial P) {
		
		int descentDim = SD.getX0().length;
		int polynomialDim = P.getN();
		
		if (descentDim != polynomialDim) {
			
			int n = P.getN();
			
			SD.init(n);
			
			for (int i = 0; i < n; i++) SD.getX0()[i] = 1;
			
		}
	}
	
	public static void main(String[] args) {
		
		boolean flag = true;
		boolean polyComplete = false;
		boolean paramsComplete = false;
		boolean algoRun = false;
		//create an instance of polynomial
		Polynomial P = new Polynomial();
		//create an instance of SteepestDescent
		SteepestDescent SD = new SteepestDescent();
		
		while (flag) {
			
			int choice = getMenuInput();
			
			if (choice == 1) {
				//Enter polynomial function
				//Number of parameters
				//Degree of the polynomial
				if (getPolynomialDetails(P)) {
					polyComplete = true;
				} else {
					polyComplete = false;
				}
				// Done
				
			} else if (choice == 2) {
				//View polynomial function
				//Print out f(x) = (f(x1) + f(x2) + ... + f(xn))
				if (polyComplete) {
					
					P.print();
				} else {
					
					System.out.println("ERROR: Polynomial function has not been entered!\n");
				}
				// Done
				
			} else if (choice == 3) {
				//Set steepest descent parameters
				//Get epsilon value
				//Get maximum number of iterations
				//Get step size alpha
				//Enter values for the starting point
				if (getAlgorithmParams(SD, P.getN())) {
					paramsComplete = true;
				} else {
					paramsComplete = false;
				}
				// Done
				
			} else if (choice == 4) {
				//View steepest descent parameters
				//Print out all of the parameters that you got above
				if (paramsComplete == false && polyComplete == false) {
					
					printDefaultParams();
				}
				else if (paramsComplete == false && polyComplete == true) {
					
					printDefaultParams();
					setDefaultParams(SD, P.getN());
				} else {
					
					printAlgoParams(SD);
				}
				// Done
				
			} else if (choice == 5) {
				//Run steepest descent algorithm
				
				if (polyComplete && paramsComplete) {
					
					if (SD.getX0().length != P.getN()) {
						
						System.out.println("WARNING: Dimensions of polynomial " 
								+ "and x0 do not match! Using x0 = 1-vector of appropriate dimension.\n");
						
						fixDimension(SD, P);
						
						SD.run(P);
						algoRun = true;
					} else {
						
						SD.run(P);
						algoRun = true;
						
					}
				} 
				
				else if (polyComplete && !paramsComplete) {
					
					if (P.getN() != 1) {
						System.out.println("WARNING: Dimensions of polynomial " 
							+ "and x0 do not match! Using x0 = 1-vector of appropriate dimension.\n");
					}
					setDefaultParams(SD, P.getN());
					SD.run(P);
					algoRun = true;
					
				} else {
					
					System.out.println("ERROR: Polynomial function has not been entered!\n");
				}
				//check if the polynomial exists and if the algorithm parameters exist
			} else if (choice == 6) {
				//Display algorithm performance
				if (polyComplete && algoRun) {
					
					SD.printResults(true);
				} else {
					
					System.out.println("ERROR: No results exist!\n");
				}
				//a couple of error messages if things are filled out.
			} else if (choice == 7) {
				//Quit the program
				System.out.println("The end.");
				flag = false;
				//done
			}
		}
	}

}
