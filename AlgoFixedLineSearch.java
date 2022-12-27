import java.util.*;

public class SteepestDescent extends Statistics {

	private double eps; // tolerance
	private int maxIter; // maximum number of iterations
	private double x0; // starting point -> (x0, x0, ... )
	private ArrayList<double []> bestPoint; // best point found for all polynomials
	private double[] bestObjVal; // best objective function value found for all polynomials
	private double[] bestGradNorm; // best gradient norm found for all polynomials
	private long[] compTime; // computation time needed for all polynomials
	private int[] nIter; // number of iterations needed for all polynomials
	private boolean resultsExist = true; // whether or not results exist
	private double avgCompTime;
	private double avgGradNorm;
	private double avgIter;
	
	
	// constructors
	public SteepestDescent() {
		
		this.eps = 0.001;
		this.maxIter = 100;
		this.x0 = 1;
		this.bestPoint = new ArrayList <double[]>();
		this.bestObjVal = new double[0];
		this.bestGradNorm = new double[0];
		this.compTime = new long[0];
		this.nIter = new int[0];
		this.resultsExist = false;
		this.avgCompTime = 0;
		this.avgGradNorm = 0;
		this.avgIter = 0;
	}
	public SteepestDescent(double eps, int maxIter, double x0) {
		
		this.eps = eps;
		this.maxIter = maxIter;
		this.x0 = x0;
	}
	
	// getters
	public double getEps () {
		
		return this.eps;
	}
	public int getMaxIter () {
		
		return this.maxIter;
	}
	public double getX0 () {
		
		return this.x0;
	}
	public double[] getBestObjVal () {
		
		return this.bestObjVal;
	}
	public double[] getBestGradNorm () {
		
		return this.bestGradNorm;
	}
	public double[] getBestPoint (int i) {
		
		return this.bestPoint.get(i);
	}
	public int[] getNIter () {
		
		return this.nIter;
	}
	public long[] getCompTime () {
		
		return this.compTime;
	}
	public boolean hasResults () {
		
		return this.resultsExist;
	}
	public double getAvgComp () {
		
		return this.avgCompTime;
	}
	public double getAvgNorm () {
		
		return this.avgGradNorm;
	}
	public double getAvgIter () {
		
		return this.avgIter;
	}
	
	// setters
	public void setEps (double a) {
		
		this.eps = a;
	}
	public void setMaxIter (int a) {
		
		this.maxIter = a;
	}
	public void setX0 (double a) {
		
		this.x0 = a;
	}
	public void setBestObjVal (int i, double a) {
		
		this.bestObjVal[i] = a;
	}
	public void setBestGradNorm (int i, double a) {
		
		this.bestGradNorm[i] = a;
	}
	public void setBestPoint (int i, double[] a) {
		
		this.bestPoint.set(i, a);
	}
	public void setCompTime (int i, long a) {
		
		this.compTime[i] = a;
	}
	public void setNIter (int i, int a) {
		
		this.nIter[i] = a;
	}
	public void setHasResults (boolean a) {
		
		this.resultsExist = a;
	}

	// other methods
	
	// initialize member arrays to correct size
	public void init (ArrayList <Polynomial> P) {
		
		this.bestPoint = new ArrayList<double[]>(P.size());
		
		for (Polynomial poly : P) {
			
			int size = poly.getN();
			double [] bPoint = new double [size];
			this.bestPoint.add(bPoint);
		}
		
		this.bestObjVal = new double[P.size()];
		this.bestGradNorm = new double[P.size()];
		this.compTime = new long[P.size()];
		this.nIter = new int[P.size()];
	}
	// run the steepest descent algorithm
	public void run (int i, Polynomial P) {
		
		int iter = 0; // number of iterations
		double norm;
		double [] curr = new double[P.getN()];
		boolean flag = true;
		
		for (int k = 0; k < curr.length; k++) {
			
			curr[k] = this.getX0();
		}
		
		long compTime = 0;
		double f_x = 0;
		
		do {
			
			// get the current time
			long temp = System.currentTimeMillis();

			// get the gradient norm
			norm = P.gradientNorm(curr);
			
			f_x = P.f(curr);
			
			// check to see if the armijo line search has any results
			
			if (this.lineSearch(P, curr) == -1) {
				
				flag = false;
				
			} else {
			
				// get the next step
				double [] next = new double [P.getN()];
				
				for (int j = 0; j < P.getN(); j++) {
					
					next[j] = curr[j] + (this.lineSearch(P, curr) * this.direction(P, curr)[j]);
				}
				
				// get the elapsed time
				long elapsed = System.currentTimeMillis()-temp;
				
				// add the elapsed time to the compTime variable
				compTime += elapsed;
				
				// if the current iteration is less that the maxIter, and the gradient norm is greater than epsilon
				if (iter < this.maxIter && norm >= this.eps) {
					
					// make the current point the next point
					curr = next;
					
					iter++;
					
				} else {
					
					flag = false;
				}
			}
			
		} while (flag);
	
		//best function value
		setBestObjVal(i, f_x);
		//best point
		setBestPoint(i, curr);
		//best norm gradient
		setBestGradNorm(i, norm);
		//number of iterations
		setNIter(i, iter);
		//Compile time
		setCompTime(i, compTime);
		
		System.out.printf("Polynomial %d done in %dms.\n", (i + 1), (int)compTime);
		
		setStats();
	}
	//find the next step size
	public double lineSearch (Polynomial P, double [] x) {
		
		return 0;
	}
	// get the next direction
	public double [] direction (Polynomial P, double [] x) {
		
		double [] direction = new double [P.getN()];
		
		for (int i = 0; i < P.getN(); i++) {
			
			direction[i] = -1 * P.gradient(x)[i];
		}
		
		return direction;
	}
	//get parameters from user for n-dim polynomial
	public boolean getParamsUser () {
		
		return true;
	}
	//print algorithm parameters
	public void print () {
		
		return;
	}
	// print the statistics for the algorithm runs
	public void printStats() {
		
		// print the header for the statistics
		System.out.println("---------------------------------------------------");
		System.out.println("          norm(grad)       # iter    Comp time (ms)");
		System.out.println("---------------------------------------------------");
		
		// get the average for the three value types
		
		double normAvg = avgDouble(this.getBestGradNorm());
		double iterAvg = avgInt(this.getNIter());
		double compTimeAvg = avgLong(this.getCompTime());
		
		// print out the averages
		
		System.out.printf("Average%13.3f%13.3f%18.3f\n", normAvg, iterAvg, compTimeAvg);
		
		// get the standard deviations for the three value types
		
		double normSTD = stDevDouble(this.getBestGradNorm());
		double iterSTD = stDevInt(this.getNIter());
		double compTimeSTD = stDevLong(this.getCompTime());
		
		// print out the standard deviations
		
		System.out.printf("St Dev%14.3f%13.3f%18.3f\n", normSTD, iterSTD, compTimeSTD);
		
		// get the minimums for all three value types
		
		double normMin = minDouble(this.getBestGradNorm());
		int iterMin = minInt(this.getNIter());
		int compTimeMin = minLong(this.getCompTime());
		
		// print out the minimums
		
		System.out.printf("Min%17.3f%13d%18d\n", normMin, iterMin, compTimeMin);
		
		// get the maximums for all three value types
		
		double normMax = maxDouble(this.getBestGradNorm());
		int iterMax = maxInt(this.getNIter());
		int compTimeMax = maxLong(this.getCompTime());
		
		// print out the maximums 
		
		System.out.printf("Max%17.3f%13d%18d\n", normMax, iterMax, compTimeMax);
		
		// extra new line
		System.out.println();
	}
	// set all of the statistics needed in the comparison
	public void setStats() {
		
		double normAvg = avgDouble(this.getBestGradNorm());
		double iterAvg = avgInt(this.getNIter());
		double compTimeAvg = avgLong(this.getCompTime());
		
		this.avgCompTime = compTimeAvg;
		this.avgGradNorm = normAvg;
		this.avgIter = iterAvg;
	}
	// print everything
	public void printAll() {
		
		// print header
		System.out.println("-------------------------------------------------------------------------");
		System.out.println("Poly no.         f(x)   norm(grad)   # iter   Comp time (ms)   Best point   ");
		System.out.println("-------------------------------------------------------------------------");
		
		// loop through all of the polynomials that you ran and print single results
		
		for (int i = 0; i < this.getBestGradNorm().length; i++) {
			
			printSingleResult(i, true);
		}
		
		System.out.println();
	}
	// print the results of one of the Polynomials
	public void printSingleResult(int i, boolean rowOnly) {
		
		// formatting for printing out the results
		System.out.printf("%8d", i + 1);
		System.out.printf("%13.6f", this.getBestObjVal()[i]);
		System.out.printf("%13.6f", this.getBestGradNorm()[i]);
		System.out.printf("%9d", this.getNIter()[i]);
		System.out.printf("%17d   ", (int)this.getCompTime()[i]);
		
		//print out best point
		for (int j = 0; j < this.bestPoint.get(i).length; j++) {
			
			if (j == this.bestPoint.get(i).length - 1) {
				
				if (this.bestPoint.get(i)[j] < Double.MAX_VALUE) {
					
					System.out.printf("%.4f", this.bestPoint.get(i)[j]);
					
				} else {
					
					System.out.print("Infinity");	
				}
				
			} else {
				
				if (this.bestPoint.get(i)[j] < Double.MAX_VALUE) {
					
					System.out.printf("%.4f, ", this.bestPoint.get(i)[j]);
					
				} else {
					
					System.out.print("Infinity, ");
					
				}
			}
		}
		
		//new line for the next polynomial
		System.out.println();
	}
}

